package com.toffeestory.backend.common;

import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.util.text.StrongTextEncryptor;
import org.springframework.stereotype.Component;

@Component
public class JasyptConfig {

    StrongTextEncryptor textEncryptor;
    private final String key = "secretKey";

    public JasyptConfig() {
        textEncryptor = new StrongTextEncryptor();
        textEncryptor.setPassword(key);
    }

    public String decrypt(String encryptText) {
        String decryptText = null;
        try {
            decryptText = textEncryptor.decrypt(encryptText);
        }
        catch (EncryptionOperationNotPossibleException e){
            System.out.println("복호화에 실패했습니다. 암호화 text를 확인해주세요");
        }
        return decryptText;
    }

}
