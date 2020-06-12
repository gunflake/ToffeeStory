package com.toffeestory.backend.post;

import com.toffeestory.backend.account.Account;
import com.toffeestory.backend.account.AccountRepository;
import com.toffeestory.backend.alarm.AlarmService;
import com.toffeestory.backend.exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.springframework.http.ResponseEntity.*;

@Slf4j
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    final PostRepository postRepository;
    final AccountRepository accountRepository;
    final PostDtlRepository postDtlRepository;
    final InterestPostRepository interestPostRepository;
    final PostService postService;
    final AlarmService alarmService;

    @Value("${url}")
    String defaultUrl;

    /*-------------------------------
       -select Post List
     -------------------------------*/
    @GetMapping(path = "")
    public List<Post> initPage(@RequestParam(required = false, name = "keyword") String keyword) {
        if(keyword != null) {
            return postRepository.findAllSearchKeywordPost(keyword);
        } else {
            return postRepository.findAllByUseStateCode(Post.UseType.USE);
        }
    }

    /*-------------------------------
      -select Related Post List
    -------------------------------*/
    @GetMapping("/{postNo}/relatedPost")
    public ResponseEntity relatedPostList(@PathVariable("postNo") Integer postNo) {
        List<Post> posts = new ArrayList<>();

        Post post = postRepository.findById(postNo).orElseThrow(() -> new NotFoundPostException(postNo));
        //해당 게시글이 가지고 있는 토핑, 상품 조회
        List<PostDtl> postDtls = postDtlRepository.findByPost(post);

        for (int i = 0; i < postDtls.size(); i++) {
            // 토핑, 상품을 가지고 있는 게시글 넘버 가져옴
            List<PostDtl> setPost = postDtlRepository.findByTagName(postDtls.get(i).getTagName());

            for (int j = 0; j < setPost.size(); j++) {
                if(!posts.contains(setPost.get(j).getPost()) && post != setPost.get(j).getPost()) {
                    posts.add(setPost.get(j).getPost());
                }
            }
        }

        return ok(posts);
    }

    /*-------------------------------
      -select account Post List
    -------------------------------*/
    @GetMapping("/account/{accountId}")
    public ResponseEntity accountPostList(@PathVariable("accountId") String accountId) {
        Account  account = accountRepository.findByAccountId(accountId).orElseThrow(() -> new NotFoundAccountException(accountId));
        List<Post> posts = postRepository.findAllByAccountAndUseStateCode(account, Post.UseType.USE);

        return ok(posts);
    }

	@PostMapping("")
    public ResponseEntity enrollPost(@RequestParam("file") MultipartFile multipartFile,
                                     @RequestParam("content") String content,
                                     @RequestParam("score") float score,
                                     @RequestParam("price") short price,
                                     @AuthenticationPrincipal Account account) throws Exception {

        // TODO : image 파일이 아닌거는 예외처리 던질 수 있도록 처리하기, 글 내용 유효성 검사 추가하기
        Post post = new Post();

        try{
            if(multipartFile.getSize() > 5120000) throw new MaxUploadSizeExceededException();



            int lastIndex = Objects.requireNonNull(multipartFile.getOriginalFilename()).lastIndexOf('.');
            String suffix = multipartFile.getOriginalFilename().substring(lastIndex);

            UUID uuid = UUID.randomUUID();
            String fileName = uuid.toString() + suffix;
            String compressFileName = uuid.toString() + "-compress" + suffix;

            String rootPath = Paths.get("").toAbsolutePath().toString();
            rootPath = rootPath.split("ToffeeStory")[0] + "ToffeeStory";
            Path fileNameAndPath = Paths.get(rootPath +"/images/", fileName);
            Files.write(fileNameAndPath, multipartFile.getBytes());
            post.setSrc(defaultUrl+fileName);

            double percent = 0.25;
            postService.resize(fileNameAndPath.toString(), rootPath +"/images/" + compressFileName, percent);

        }catch (IOException e){
            throw new InvalidImageException("이미지 업로드에 실패했습니다. 작성한 글 내용을 확인해주세요.");
        }

        account.setPosts(postRepository.findAllByAccountAndUseStateCode(account, Post.UseType.USE));


        post.setScore(score);
        post.setContent(content);
        post.setPrice(price);
        post.setAccount(account);

        Post save = postRepository.save(post);

        return created(
                ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/api/posts/{postNo}")
                .buildAndExpand(save.getPostNo())
                .toUri()).build();
    }

    @PutMapping("/{postNo}")
    public ResponseEntity updatePost(@PathVariable("postNo") int postNo,
                                     @RequestParam(value = "file", required = false) MultipartFile multipartFile,
                                     @RequestParam("content") String content,
                                     @RequestParam("score") float score,
                                     @RequestParam("price") short price,
                                     @AuthenticationPrincipal Account account) throws Exception{

        Post post = postRepository.findById(postNo).orElseThrow(() -> new NotFoundPostException(postNo));

        if(!post.getAccount().getAccountId().equals(account.getAccountId())){
            return badRequest().body(new RestApiError(HttpStatus.BAD_REQUEST, "본인이 작성한 글만 수정할 수 있습니다."));
        }

        if (multipartFile != null) {
            UUID uuid = UUID.randomUUID();
            String fileName = uuid.toString();

            Path fileNameAndPath = Paths.get("./images/", fileName);
            try {
                Files.write(fileNameAndPath, multipartFile.getBytes());
            } catch (IOException e) {
                throw new InvalidImageException("이미지 업로드에 실패했습니다.");
            }
            post.setSrc(defaultUrl+fileName);
        }

        post.setContent(content);
        post.setScore(score);
        post.setPrice(price);
        postRepository.save(post);

        return noContent().build();
	}

	@DeleteMapping(path = "/{postNo}")
    public ResponseEntity deletePost(@PathVariable("postNo") Integer postNo,
                                     @AuthenticationPrincipal Account account){
        Post post = postRepository.findById(postNo).orElseThrow(() -> new NotFoundPostException(postNo));
        log.info(post.getAccount().getAccountId());
        log.info(account.getAccountId());

        if(!post.getAccount().getAccountId().equals(account.getAccountId())){
            return badRequest().body(new RestApiError(HttpStatus.BAD_REQUEST, "본인이 작성한 글만 수정할 수 있습니다."));
        }

        post.setUseStateCode(Post.UseType.UNUSED);

        postRepository.save(post);

        return noContent().build();
    }

    /*-------------------------------
       - select Post
     -------------------------------*/
    @GetMapping(path = "/{postNo}")
    public ResponseEntity selectPost(@PathVariable("postNo") Integer postNo,
                                     @AuthenticationPrincipal Account account) {
        Post post = postRepository.findById(postNo).orElseThrow(() -> new NotFoundPostException(postNo));
        List<String>  tagNames = new ArrayList<>();
        List<PostDtl> postDtls = postDtlRepository.findByPost(post);

        for (int i = 0; i < postDtls.size(); i++) {
            tagNames.add(postDtls.get(i).getTagName());
        }

        post.setTags(tagNames);

        int likeState     = 0;
        int bookmarkState = 0;

        if(account != null && interestPostRepository.findAllByPostAndAccount(post, account).isPresent()) {
            InterestPost interestPost = interestPostRepository.findAllByPostAndAccount(post, account).orElseThrow(() -> new NotFoundPostException());

            likeState     = interestPost.getLikeState().ordinal();
            bookmarkState = interestPost.getBookmarkState().ordinal();
        }

        ResponsePost responsePost = new ResponsePost(post, post.getAccount().getAccountId(), post.getAccount().getSrc(), likeState, bookmarkState);

        return ok(responsePost);
    }

    /*-------------------------------
       - select Tag
     -------------------------------*/
    @GetMapping("/tag/{tagName}")
    public List<Post> tagPostList(@PathVariable("tagName") String tagName) {
        List<Post> posts = new ArrayList<>();
        List<PostDtl> post = postDtlRepository.findByTagName(tagName);

        for (int i = 0; i < post.size(); i++) {
            int postNo = post.get(i).getPost().getPostNo();
            posts.add(postRepository.findById(postNo).orElseThrow(() -> new NotFoundPostException(postNo)));
        }

        return posts;
    }

    /*-------------------------------
       - update Interest
     -------------------------------*/
    @PutMapping("/{postNo}/interest")
    public ResponseEntity updateInterest(@PathVariable("postNo") Integer postNo,
                                         @RequestParam("valueCode") Byte valueCode,
                                         @RequestParam("useFlag") Boolean useState,
                                         @AuthenticationPrincipal Account account) {

        Post post = postRepository.findById(postNo).orElseThrow(() -> new NotFoundPostException(postNo));

        // set InterestPost
        postService.updateInterest(post, account, valueCode, useState);


        if(valueCode == 0) {
            if(!useState) {
                post.setLikeCnt(post.getLikeCnt() + 1);

                // set Alarm & 내 게시글이 아닐 경우에만 알람 등록
                if(!post.getAccount().getAccountNo().equals(account.getAccountNo())){
                    alarmService.enrollLikeAlarm(post.getAccount(), account.getAccountNo(), post).orElseThrow(FailEnrollAlarmException::new);
                }

            } else {
                post.setLikeCnt(post.getLikeCnt() - 1);
            }

            postRepository.save(post);
        }

        return ok(post);
    }
}
