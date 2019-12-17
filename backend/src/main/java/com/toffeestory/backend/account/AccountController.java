package com.toffeestory.backend.account;

import com.toffeestory.backend.exception.AccountNotValidException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountController {


    private static final Logger LOG = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping(path = "/create")
    public Account createMember(@RequestBody @Valid Account account, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<ObjectError> errorLists = bindingResult.getAllErrors();

            for (ObjectError error : errorLists) {
                LOG.error(error.toString());
            }
            throw new AccountNotValidException("회원정보가 올바르지 않습니다.");
        } else {

            account.setAccountPwd(bCryptPasswordEncoder.encode(account.getAccountPwd()));
            return accountRepository.save(account);
        }
    }

    @PostMapping(path = "/login")
    public AuthenticationToken login(
            @RequestBody Account account,
            HttpSession session
    ) {
        String username = account.getAccountId();
        String password = account.getAccountPwd();


    }
}
