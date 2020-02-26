package com.toffeestory.backend.post;

import com.toffeestory.backend.account.Account;
import com.toffeestory.backend.exception.InvalidImageException;
import com.toffeestory.backend.exception.NotFoundPostException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.http.ResponseEntity.*;

@Slf4j
@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @PostMapping("")
    public ResponseEntity enrollPost(@RequestParam("file") MultipartFile multipartFile,
                                     @RequestParam("content") String content,
                                     @RequestParam("score") float score,
                                     @AuthenticationPrincipal Account account) throws Exception {

        log.info(multipartFile.getOriginalFilename());

        // TODO : image 파일이 아닌거는 예외처리 던질 수 있도록 처리하기, 글 내용 유효성 검사 추가하기

        Path fileNameAndPath = Paths.get("./images/",multipartFile.getOriginalFilename());
        try{
            Files.write(fileNameAndPath, multipartFile.getBytes());
        }catch (IOException e){
            throw new InvalidImageException("이미지 업로드에 실패했습니다.");
        }

        account.setPost(postRepository.findAllByAccount(account));

        Post post = new Post();
        post.setScore(score);
        post.setContent(content);
        post.setPostPic(multipartFile.getOriginalFilename());
        post.setPrice((short) 10000);
        post.setAccount(account);

        Post save = postRepository.save(post);

        return created(
                ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/api/posts/{postNo}")
                .buildAndExpand(save.getPostNo())
                .toUri()).build();
    }

    @GetMapping(path = "/{postNo}")
    public ResponseEntity<Post> getPost(@PathVariable("postNo") int postNo,
                                  @AuthenticationPrincipal Account account) throws Exception{


        Post post = postRepository.findByAccount(account).orElseThrow(() -> new NotFoundPostException(0));
        log.info(post.getPostPic());

        return ok(post);

    }

    @PutMapping("/{postNo}")
    public ResponseEntity updatePost(@PathVariable("postNo") int postNo,
                                     @RequestParam(value = "file", required = false) MultipartFile multipartFile,
                                     @RequestParam("content") String content,
                                     @RequestParam("score") float score,
                                     @AuthenticationPrincipal Account account) throws Exception{

        Post post = postRepository.findById(postNo).orElseThrow(() -> new NotFoundPostException(postNo));

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
        postRepository.save(post);

        return noContent().build();
    }
}
