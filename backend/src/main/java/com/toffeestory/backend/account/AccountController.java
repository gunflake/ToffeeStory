package com.toffeestory.backend.account;

import com.toffeestory.backend.exception.AccountNotValidException;
import com.toffeestory.backend.security.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    private static final Logger LOG = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping(path = "/join")
    public String createMember(@RequestBody @Valid Account account, BindingResult bindingResult) throws Exception {

        if (bindingResult.hasErrors()) {
            List<ObjectError> errorLists = bindingResult.getAllErrors();

            for (ObjectError error : errorLists) {
                LOG.error(error.toString());
            }
            throw new AccountNotValidException("회원정보가 올바르지 않습니다.");
        } else {

            // TODO : DB에 저장할 때, 정상적으로 저장되었는지 로직 처리하기. (try-catch 문 같은거....)
            return accountService.saveAccount(account);
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
            throw new AccountNotValidException("ID / PW를 다시 확인해주세요.");
        }
    }

    @GetMapping(path = "/auth")
    public AccountInfo getAccountInfo(@AuthenticationPrincipal UserDetails userDetails){
        Account getAccount = accountRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("UserEmail: " + userDetails.getUsername() + "not found"));
        return new AccountInfo(getAccount.getAccountId(), getAccount.getEmail());
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
    public Integer checkCurrentPassword(@RequestBody Account account) {
        // TODO : 토큰에서 정보 어떻게 가져올까~~~~~ --> @AuthenticationPrincipal, @RequestBody
        // 리턴시 코드, 메세지 묶어서 보내기? -> Account 객체에 필드 추가
        try {
            String userEmail = account.getEmail();
            String password = account.getAccountPwd();
            System.out.println("현재 비밀번호 " + userEmail + password);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userEmail, password));

            Account getAccount = accountRepository.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("UserEmail: " + userEmail + "not found"));

            return getAccount.getAccountNo();
        } catch (AuthenticationException e) {
            return 0;
        }
    }

    // 비밀번호 업데이트
    @PostMapping(path = "/secured/changePassword")
    public Integer updatePassword(@RequestBody Account account) {
        account = accountService.updatePassword(account);

        return account.getAccountNo();
    }
}
