package com.toffeestory.backend.common;

import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.util.text.StrongTextEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JasyptConfigTest {

    StrongTextEncryptor textEncryptor;
    private final String key = "secretKey";

    void setEncryptor() {
        textEncryptor = new StrongTextEncryptor();
        textEncryptor.setPassword(key);
    }

    @Test
    void decrypt() {
        setEncryptor();

        String decryptText = "UBjZlk3EUZzj+iWKpdojuQ==";
        String decrpytext1 = "gEb3XK5MJMb3vMiAaSSRaQ==";


        try {
            String decrypt = textEncryptor.decrypt(decryptText);

            System.out.println(decrypt);
            System.out.println(textEncryptor.decrypt(decrpytext1));
        }
        catch (EncryptionOperationNotPossibleException e){
            System.out.println("복호화에 실패했습니다. 암호화 text를 확인해주세요");
        }
    }

}
