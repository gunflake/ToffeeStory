package com.toffeestory.backend.account;

import com.toffeestory.backend.exception.AccountNotValidException;
import com.toffeestory.backend.security.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountController {


    private static final Logger LOG = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping(path = "/join")
    public Integer createMember(@RequestBody @Valid Account account, BindingResult bindingResult) throws Exception {

        if (bindingResult.hasErrors()) {
            List<ObjectError> errorLists = bindingResult.getAllErrors();

            for (ObjectError error : errorLists) {
                LOG.error(error.toString());
            }
            throw new AccountNotValidException("회원정보가 올바르지 않습니다.");
        } else {

            account.setAccountPwd(passwordEncoder.encode(account.getAccountPwd()));

            // TODO : DB에 저장할 때, 정상적으로 저장되었는지 로직 처리하기. (try-catch 문 같은거....)
            @Valid Account save = accountRepository.save(account);

            return save.getAccountNo();
        }
    }

    @PostMapping(path = "/login")
    public AccountToken loginMember(@RequestBody Account account, BindingResult bindingResult) throws Exception {
        try {
            String username = account.getAccountId();
            String password = account.getAccountPwd();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            String token = jwtTokenProvider.createToken(username, this.accountRepository.findByAccountId(username).orElseThrow(() -> new UsernameNotFoundException("AccountId: " + username + "not found")).getRoles());

            return new AccountToken(username, token);
        } catch (AuthenticationException e) {
            throw new AccountNotValidException("ID / PW를 다시 확인해주세요.");
        }
    }
}
