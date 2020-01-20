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
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.ResponseEntity.created;

@Slf4j
@RestController
@RequestMapping("/api/account")
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
            throw new InvalidAccountException("회원정보가 올바르지 않습니다.");
        } else {
            // TODO : DB에 저장할 때, 정상적으로 저장되었는지 로직 처리하기. (try-catch 문 같은거....)
            Account saveAccount = accountService.saveAccount(account);
            return created(
                    ServletUriComponentsBuilder
                            .fromCurrentRequest()
                            .path("/api/account/{id}")
                            .buildAndExpand(saveAccount.getAccountNo())
                            .toUri())
                    .build();
        }
    }

    @PostMapping(path = "/login")
    public String loginMember(@RequestBody Account account, BindingResult bindingResult) throws Exception {
        try {
            String userEmail = account.getEmail();
            String password = account.getAccountPwd();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userEmail, password));

            Account getAccount = accountRepository.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("UserEmail: " + userEmail + "not found"));
            String token = jwtTokenProvider.createToken(getAccount.getEmail(), getAccount.getAuthorities().toString());

            return token;
        } catch (AuthenticationException e) {
            throw new InvalidAccountException("ID / PW를 다시 확인해주세요.");
        }
    }

    @GetMapping(path = "/auth")
    public AccountInfo getAccountInfo(@AuthenticationPrincipal UserDetails userDetails){
        Account getAccount = accountRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("UserEmail: " + userDetails.getUsername() + "not found"));
        return new AccountInfo(getAccount.getAccountId(), getAccount.getEmail());
    }
}
