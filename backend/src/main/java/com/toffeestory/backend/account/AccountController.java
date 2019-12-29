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
    public AccountToken loginMember(@RequestBody Account account, BindingResult bindingResult) throws Exception {
        try {
            String userEmail = account.getEmail();
            String password = account.getAccountPwd();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userEmail, password));

            Account getAccount = accountRepository.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("UserEmail: " + userEmail + "not found"));
            String token = jwtTokenProvider.createToken(getAccount.getEmail(), getAccount.getRoles());

            return new AccountToken(getAccount.getAccountId(), getAccount.getEmail(), token);
        } catch (AuthenticationException e) {
            throw new AccountNotValidException("ID / PW를 다시 확인해주세요.");
        }
    }

    @GetMapping(path = "/auth")
    public String checkAccount(@AuthenticationPrincipal UserDetails userDetails){
        return userDetails.getUsername();
    }
}
