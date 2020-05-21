package com.toffeestory.backend.common;

import com.toffeestory.backend.exception.InvalidEncryptTextException;
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
            throw new InvalidEncryptTextException();
        }
        return decryptText;
    }

}
