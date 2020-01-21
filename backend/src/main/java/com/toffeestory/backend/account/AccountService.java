package com.toffeestory.backend.account;

import com.toffeestory.backend.exception.AccountNotValidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.accountRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("email: " + email + " not found"));
    }

    public String saveAccount(Account account){
        account.setAccountPwd(passwordEncoder.encode(account.getAccountPwd()));
        account.setAuthority("ROLE_USER");
        // TODO : DB에 저장할 때, 정상적으로 저장되었는지 로직 처리하기. (try-catch 문 같은거....)
        @Valid Account save = accountRepository.save(account);

        return save.getAccountId();
    }

    // 계정 정보 업데이트
    public Account updateAccount(Account account) {
        Account accountFromDb = accountRepository.findByAccountNo(account.getAccountNo()).orElseThrow(() -> new AccountNotValidException(account.getAccountNo()+"를 찾을 수 없습니다"));;

        accountFromDb.setAccountName(account.getAccountName());
        accountFromDb.setInstagram(account.getInstagram());
        accountFromDb.setTwitter(account.getTwitter());
        accountFromDb.setBio(account.getBio());

        return accountRepository.save(accountFromDb);
    }

    // 현재 비밀번호 확인
    public Boolean checkCurrentPassword(Account account) {
        Account accountFromDb = accountRepository.findByAccountNo(account.getAccountNo()).orElseThrow(() -> new AccountNotValidException(account.getAccountNo()+"를 찾을 수 없습니다"));;

        if (passwordEncoder.encode(account.getAccountPwd()) == accountFromDb.getAccountPwd()) {
            return true;
        } else {
            return false;
        }
    }

    // 비밀번호 업데이트
    public Account updatePassword(Account account) {
        Account accountFromDb = accountRepository.findByAccountNo(account.getAccountNo()).orElseThrow(() -> new AccountNotValidException(account.getAccountNo()+"를 찾을 수 없습니다"));;

        accountFromDb.setAccountPwd(passwordEncoder.encode(account.getAccountNewPwd()));

        return accountRepository.save(accountFromDb);
    }
}
