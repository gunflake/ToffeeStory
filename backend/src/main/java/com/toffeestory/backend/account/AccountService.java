package com.toffeestory.backend.account;

import com.toffeestory.backend.exception.EmailSendException;
import com.toffeestory.backend.exception.NotFoundAccountException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Properties;
import java.util.UUID;

@Slf4j
@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountKeyRepository accountKeyRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private  PasswordEncoder passwordEncoder;
    
    @Autowired
    private MessageSource messageSource;

    @Value("${baseUrl}")
    private String baseUrl;

    private static final String mailSubject = "[ToffeeStudy] Password Reset";

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.accountRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("email: " + email + " not found"));
    }

    public Account saveAccount(Account account){
        account.setAccountPwd(passwordEncoder.encode(account.getAccountPwd()));
        // TODO : DB에 저장할 때, 정상적으로 저장되었는지 로직 처리하기. (try-catch 문 같은거....)

        return accountRepository.save(account);
    }

    // 아이디 검사
    public boolean checkAccountId(String accountId, String requestAccountId) {
        if (accountId.equals(requestAccountId) || accountId == requestAccountId || accountRepository.findByAccountId(requestAccountId).orElse(null) == null) {
            return true;
        } else {
            return false;
        }
    }

    // 현재 비밀번호 일치 여부 확인
    public Boolean checkCurrentPassword(String currentPassword, String requestPwd) {
        if (passwordEncoder.matches(requestPwd, currentPassword)) {
            return true;
        } else {
            return false;
        }
    }

    // 비밀번호 업데이트
    public Account updatePassword(Account account) {
        Account accountFromDb = accountRepository.findByAccountNo(account.getAccountNo()).orElseThrow(() -> new NotFoundAccountException(account.getAccountNo()+"를 찾을 수 없습니다"));;

        accountFromDb.setAccountPwd(passwordEncoder.encode(account.getAccountNewPwd()));

        return accountRepository.save(accountFromDb);
    }

    // 이메일 전송
    public void sendEmail(Account account, Locale locale){
        try{
            SimpleMailMessage msg = new SimpleMailMessage();

            StringBuilder stringBuilder = new StringBuilder();

            msg.setTo(account.getEmail());
            msg.setSubject(mailSubject);

            String token = createResetPasswordToken(account);

            String resetURL = baseUrl + "/reset/password/" + token;

            String content = messageSource.getMessage("emailContent", null, locale);

            stringBuilder.append(content)
                    .append(resetURL);
            msg.setText(stringBuilder.toString());

            javaMailSender.send(msg);
        }catch (Exception ex){
            log.error(ex.toString());
            throw new EmailSendException();
        }
    }

    private String createResetPasswordToken(Account account){

        UUID uuid = UUID.randomUUID();
        String randomToken = uuid.toString();

        AccountKey accountKey = new AccountKey();
        accountKey.setAccountNo(account.getAccountNo());
        accountKey.setToken(randomToken);
        accountKeyRepository.save(accountKey);
        return randomToken;
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        // TODO : mail ID & 비밀번호 외부주입으로 하도록 설정해야하나...

        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("toffeestudy3");
        mailSender.setPassword("xhvltmxjel123");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.connectiontimeout", 5000);
        props.put("mail.smtp.timeout", 5000);
        props.put("mail.smtp.writetimeout", 5000);

        return mailSender;
    }
}
