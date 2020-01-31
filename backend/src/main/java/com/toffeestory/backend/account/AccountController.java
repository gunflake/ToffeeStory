package com.toffeestory.backend.account;

import com.toffeestory.backend.exception.InvalidAccountException;
import com.toffeestory.backend.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
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
        return ok(new AccountInfo(account.getAccountId(), account.getEmail()));
    }

    // Edit Profile 내 정보 세팅용
    @GetMapping(path = "/secured/getAccount")
    public Account getAccount(@AuthenticationPrincipal UserDetails userDetails){
        return accountRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("UserEmail: " + userDetails.getUsername() + "not found"));
    }

    // TODO : 수정하는건 PUT으로
    // 계정 정보 업데이트
    @PostMapping(path = "/secured/updateAccount")
    public Integer updateAccount(@RequestBody @Valid Account account) {
        // TODO : username(accountId) 중복 검사 로직 추가
        account = accountService.updateAccount(account);

        return account.getAccountNo();
    }

    // 현재 비밀번호 검사
    @PostMapping(path = "/secured/checkCurrentPassword")
    public Account checkCurrentPassword(@AuthenticationPrincipal Account account, @RequestParam("accountPwd") String requestPwd) {
        if (accountService.checkCurrentPassword(account.getAccountPwd(), requestPwd)) {
            account.setResponseCode(0); // 성공
        } else {
            account.setResponseCode(1); // 실패
            account.setResponseMsg("Current Password is invalid");
        }

        return account;
    }

    // 비밀번호 업데이트
    @PutMapping(path = "/secured/changePassword")
    public Account updatePassword(@AuthenticationPrincipal Account account, @RequestParam("accountNewPwd") String acocuntNewPwd) {
        account.setAccountNewPwd(acocuntNewPwd);
        account = accountService.updatePassword(account);
        account.setResponseCode(0);

        return account;
    }

    // 계정 삭제
    @PostMapping(path = "/secured/deleteAccount")
    public Account deleteAccount(@AuthenticationPrincipal Account account) {
        accountRepository.delete(account);
        account.setResponseCode(0);

        return account;
    }
}
