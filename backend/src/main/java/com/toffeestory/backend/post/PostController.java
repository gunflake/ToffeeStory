package com.toffeestory.backend.post;

import com.toffeestory.backend.account.Account;
import com.toffeestory.backend.exception.InvalidImageException;
import com.toffeestory.backend.exception.NotFoundPostException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

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
        post.setPostPic(fileNameAndPath.toString());
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
    public ResponseEntity getPost(@PathVariable("postNo") int postNo,
                                  @AuthenticationPrincipal Account account) throws Exception{


        Post post = postRepository.findByAccount(account).orElseThrow(() -> new NotFoundPostException(0));
        log.info(post.getPostPic());
        File file = new File("./images/"+post.getPostPic());
        post.setFile(file);
        Resource resource = new FileUrlResource(post.getPostPic());

        return ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header("Content-Disposition","attachment; filename=\"" + file.getName() + "\"" )
                .body(resource);

    }

    @PutMapping("/{postNo}")
    public ResponseEntity updatePost(@PathVariable("postNo") int postNo,
                                     @RequestParam("file") MultipartFile multipartFile,
                                     @RequestParam("content") String content,
                                     @RequestParam("score") float score,
                                     @AuthenticationPrincipal Account account) throws Exception{

        return null;
    }

}
