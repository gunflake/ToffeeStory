package com.toffeestory.backend.post;

import com.toffeestory.backend.account.Account;
import com.toffeestory.backend.account.AccountRepository;
import com.toffeestory.backend.exception.InvalidImageException;
import com.toffeestory.backend.exception.NotFoundPostException;
import com.toffeestory.backend.exception.RestApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@Slf4j
@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    PostRepository postRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PostDtlRepository postDtlRepository;

    @Autowired
    InterestPostRepository interestPostRepository;

    @Autowired
    PostService postService;

    /*-------------------------------
       -select Post List (new / best / hot)
     -------------------------------*/
    @GetMapping(path = "")
    public List<Post> initPage(@RequestParam("flag") Integer flag) {
        if(flag == 0){                      // New
            return postRepository.findAll();
        } else if(flag == 1) {              // Best
            return postRepository.findAllByOrderByScoreDesc();
        } else {                            // Hot
            return postRepository.findAllByOrderByLikeCntDesc();
        }
    }

	@PostMapping("")
    public ResponseEntity enrollPost(@RequestParam("file") MultipartFile multipartFile,
                                     @RequestParam("content") String content,
                                     @RequestParam("score") float score,
                                     @RequestParam("price") short price,
                                     @AuthenticationPrincipal Account account) throws Exception {

        log.info(multipartFile.getOriginalFilename());

        // TODO : image 파일이 아닌거는 예외처리 던질 수 있도록 처리하기, 글 내용 유효성 검사 추가하기

        Path fileNameAndPath = Paths.get("./images/",multipartFile.getOriginalFilename());
        try{
            Files.write(fileNameAndPath, multipartFile.getBytes());
        }catch (IOException e){
            throw new InvalidImageException("이미지 업로드에 실패했습니다.");
        }

        account.setPosts(postRepository.findAllByAccount(account));

        Post post = new Post();
        post.setScore(score);
        post.setContent(content);
        post.setPostPic(multipartFile.getOriginalFilename());
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

        log.info(post.getAccount().getAccountId());
        log.info(account.getAccountId());
        if(!post.getAccount().getAccountId().equals(account.getAccountId())){
            return badRequest().body(new RestApiError(HttpStatus.BAD_REQUEST, "본인이 작성한 글만 수정할 수 있습니다."));
        }

        if (multipartFile != null) {
            String fileName = multipartFile.getOriginalFilename();

            Path fileNameAndPath = Paths.get("./images/", fileName);
            try {
                Files.write(fileNameAndPath, multipartFile.getBytes());
            } catch (IOException e) {
                throw new InvalidImageException("이미지 업로드에 실패했습니다.");
            }
            post.setPostPic(fileName);
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

        postRepository.delete(post);

        return noContent().build();
    }

    /*-------------------------------
       - select Post
     -------------------------------*/
    @GetMapping(path = "/{postNo}")
    public ResponseEntity selectPost(@PathVariable("postNo") Integer postNo,
                                     @AuthenticationPrincipal Account account) {
        Post post = postRepository.findByPostNo(postNo).orElseThrow(() -> new RuntimeException());
        List<String>  tagNames = new ArrayList<>();
        List<PostDtl> postDtls = postDtlRepository.findByPostNo(postNo);

        for (int i = 0; i < postDtls.size(); i++) {
            tagNames.add(postDtls.get(i).getTagName());
        }

        post.setTags(tagNames);
        post.setSrc(post.getPostPic());

        Byte likeFlag     = 0;
        Byte bookmarkFlag = 0;

        if(account != null) {
            likeFlag     = interestPostRepository.selectUseFlag(postNo, account.getAccountNo(), (byte)0);
            bookmarkFlag = interestPostRepository.selectUseFlag(postNo, account.getAccountNo(), (byte)1);
        }

        ResponsePost responsePost = new ResponsePost(post, post.getAccount().getAccountId(), post.getAccount().getProfilePic(), likeFlag, bookmarkFlag);

        return ok(responsePost);
    }

    /*-------------------------------
      -select Related Post List
    -------------------------------*/
    @GetMapping("/{postNo}/relatedPost")
    public ResponseEntity relatedPostList(@PathVariable("postNo") Integer postNo) {
        List<Post> posts = new ArrayList<>();

        //해당 게시글이 가지고 있는 토핑, 상품 조회
        List<PostDtl> postDtls = postDtlRepository.findByPostNo(postNo);

        for (int i = 0; i < postDtls.size(); i++) {
            // 토핑, 상품을 가지고 있는 게시글 넘버 가져옴
            List<PostDtl> setPostNo = postDtlRepository.findByTagName(postDtls.get(i).getTagName());

            for (int j = 0; j < setPostNo.size(); j++) {
                int getPostNo = setPostNo.get(i).getPostNo();

                if(!posts.contains(getPostNo)) {
                    posts.add(postRepository.findByPostNo(getPostNo).orElseThrow(() -> new RuntimeException()));
                }
            }
        }

        return ok(posts);
    }

    /*-------------------------------
       - select Tag
     -------------------------------*/
    @GetMapping("/tag/{tagName}")
    public List<Post> tagPostList(@PathVariable("tagName") String tagName) {
        List<Post> posts = new ArrayList<>();
        List<PostDtl> postNo = postDtlRepository.findByTagName(tagName);

        for (int i = 0; i < postNo.size(); i++) {
            posts.add(postRepository.findByPostNo(postNo.get(i).getPostNo()).orElseThrow(() -> new RuntimeException()));
        }

        return posts;
    }

    /*-------------------------------
       - update linkCnt
     -------------------------------*/
    @PutMapping("/{postNo}/interest")
    public ResponseEntity updateInterest(@PathVariable("postNo") Integer postNo,
                                         @RequestParam("valueCode") Byte valueCode,
                                         @RequestParam("useFlag") Byte useFlag,
                                         @AuthenticationPrincipal Account account) {

        postService.updateInterest(postNo, account.getAccountNo(), valueCode, useFlag);

        Post post = postRepository.findByPostNo(postNo).orElseThrow(() -> new RuntimeException());
        if(valueCode == 0) {

            if(useFlag == 0) {
                post.setLikeCnt(post.getLikeCnt() + 1);
            } else {
                post.setLikeCnt(post.getLikeCnt() - 1);
            }

            postRepository.save(post);
        }

        return ok(post);
    }
}
