package com.toffeestory.backend.account;

import com.toffeestory.backend.exception.InvalidAccountException;
import com.toffeestory.backend.exception.InvalidImageException;
import com.toffeestory.backend.exception.NotFoundAccountException;
import com.toffeestory.backend.post.PostRepository;
import com.toffeestory.backend.post.Post;
import com.toffeestory.backend.post.InterestPostRepository;
import com.toffeestory.backend.post.InterestPost;
import com.toffeestory.backend.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.ArrayList;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private InterestPostRepository interestPostRepository;

    @Value("${url}")
    String defaultUrl;

    /**
     * 회원 생성
     * @param account
     * @param bindingResult
     * @return
     */
    @PostMapping(path = "")
    public ResponseEntity<Object> createMember(@RequestBody @Valid Account account, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorLists = bindingResult.getAllErrors();

            for (ObjectError error : errorLists) {
                log.error(error.toString());
            }
            throw new InvalidAccountException("회원정보가 올바르지 않습니다. 확인 후 다시 입력해주세요.");
        } else {
            // TODO : DB에 저장할 때, 정상적으로 저장되었는지 로직 처리하기. (try-catch 문 같은거....)
            Account saveAccount = accountService.saveAccount(account);
            return created(
                    ServletUriComponentsBuilder
                            .fromCurrentRequest()
                            .path("/api/accounts/{id}")
                            .buildAndExpand(saveAccount.getAccountNo())
                            .toUri())
                    .build();
        }
    }

    @PostMapping(path = "/login")
    public ResponseEntity<String> loginMember(@RequestBody Account account, BindingResult bindingResult) throws Exception {
        try {
            String userEmail = account.getEmail();
            String password = account.getAccountPwd();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userEmail, password));

            Account getAccount = accountRepository.findByEmail(userEmail).orElseThrow(() -> new InvalidAccountException("ID / PW 입력 정보를 다시 확인해주세요."));
            String token = jwtTokenProvider.createToken(getAccount.getEmail(), getAccount.getAuthorities().toString());

            return ok(token);
        } catch (AuthenticationException e) {
            throw new InvalidAccountException("ID / PW 입력 정보를 다시 확인해주세요.");
        }
    }

    @GetMapping(path = "/me")
    public ResponseEntity<AccountInfo> getAccountInfo(@AuthenticationPrincipal Account account){
        return ok(new AccountInfo(account.getAccountId(), account.getEmail(), account.getSrc()));
    }

    // Profile 페이지 세팅
    @GetMapping(path = "/{username}")
    public ResponseEntity<Account> getProfile(@PathVariable("username") String accountId) {
        return ok(accountRepository.findByAccountId(accountId).orElseThrow(() -> new NotFoundAccountException("존재하지 않는 아이디")));
    }

    // Edit Profile 내 정보 세팅
    @GetMapping(path = "/secured/getAccount")
    public ResponseEntity<Account> getAccount(@AuthenticationPrincipal UserDetails userDetails) {
        return ok(accountRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new NotFoundAccountException("존재하지 않는 아이디")));
    }

    // 아이디 검사
    @PostMapping(path = "/secured/checkAccountId")
    public ResponseEntity checkAccountId(@AuthenticationPrincipal Account account, @RequestParam("accountId") String requestAccountId) {
        if (accountService.checkAccountId(account.getAccountId(), requestAccountId)) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity("You can not use this User Name", HttpStatus.CREATED);
        }
    }

    // 계정 정보 업데이트
    @PutMapping(path = "/secured/updateAccount")
    public ResponseEntity<String> updateAccount(@AuthenticationPrincipal Account account, @RequestBody Account requestAccount) {
        Account accountFromDb = accountRepository.findByAccountNo(account.getAccountNo()).orElseThrow(() -> new NotFoundAccountException(account.getAccountNo()+"를 찾을 수 없습니다"));;

        accountFromDb.setAccountName(requestAccount.getAccountName());
        accountFromDb.setAccountId(requestAccount.getAccountId());
        accountFromDb.setInstagram(requestAccount.getInstagram());
        accountFromDb.setTwitter(requestAccount.getTwitter());
        accountFromDb.setBio(requestAccount.getBio());

        accountRepository.save(accountFromDb);

        return ok("Your account has been changed successfully!");
    }

    // 프로필 사진 업데이트
    @PostMapping(path = "/secured/updateProfilePic")
    public Account updateAccount(@AuthenticationPrincipal Account account, @RequestPart("file") MultipartFile profilePic) throws IOException {
        if (profilePic != null) {
            Path fileNameAndPath = Paths.get("./images/", profilePic.getOriginalFilename());

            try {
                // 파일 저장
                Files.write(fileNameAndPath, profilePic.getBytes());

                // 계정 업데이트
                Account accountFromDb = accountRepository.findByAccountNo(account.getAccountNo()).orElseThrow(() -> new NotFoundAccountException(account.getAccountNo()+"를 찾을 수 없습니다"));;
                accountFromDb.setSrc(defaultUrl+profilePic.getOriginalFilename());
                accountRepository.save(accountFromDb);
            } catch (IOException e) {
                throw new InvalidImageException("이미지 업로드에 실패했습니다.");
            }
        }

        return account;
    }

    // 현재 비밀번호 검사
    @PostMapping(path = "/secured/checkCurrentPassword")
    public ResponseEntity checkCurrentPassword(@AuthenticationPrincipal Account account, @RequestParam("accountPwd") String requestPwd) {
        if (accountService.checkCurrentPassword(account.getAccountPwd(), requestPwd)) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity("Current Password is invalid", HttpStatus.CREATED);
        }
    }

    // 비밀번호 업데이트
    @PatchMapping(path = "/secured/changePassword")
    public ResponseEntity<String> updatePassword(@AuthenticationPrincipal Account account, @RequestParam("accountNewPwd") String acocuntNewPwd) {
        account.setAccountNewPwd(acocuntNewPwd);

        accountService.updatePassword(account);

        return ok("Your password has been changed successfully!");
    }

    // 계정 삭제
    @PostMapping(path = "/secured/deleteAccount")
    public ResponseEntity<String> deleteAccount(@AuthenticationPrincipal Account account) {
        account = accountRepository.findByAccountId(account.getAccountId()).orElseThrow(() -> new NotFoundAccountException("존재하지 않는 아이디"));

        accountRepository.delete(account);

        return ok("Your account has been closed. We\'re here for you always :)");
    }

    /*-------------------------------
        - select My Menu List
    -------------------------------*/
    @GetMapping("/me/myMenu/{valueCode}")
    public ResponseEntity interestList(@PathVariable("valueCode") Byte valueCode,
                                       @AuthenticationPrincipal Account account) {
        List<Post> posts = new ArrayList<>();

        if(valueCode == 3) {    //my menu
            posts = postRepository.findAllByAccount(account);
        } else {
            List<InterestPost> interestPosts;

            if(valueCode == 0) {
                interestPosts = interestPostRepository.findByAccountNoAndLikeState(account.getAccountNo(), InterestPost.UseType.INTEREST);
            } else {
                interestPosts = interestPostRepository.findByAccountNoAndBookmarkState(account.getAccountNo(), InterestPost.UseType.INTEREST);
            }

            for (int i = 0; i < interestPosts.size(); i++) {
                posts.add(postRepository.findByPostNo(interestPosts.get(i).getPostNo()).orElseThrow(() -> new RuntimeException()));
            }
        }

        return ok(posts);
    }
}
