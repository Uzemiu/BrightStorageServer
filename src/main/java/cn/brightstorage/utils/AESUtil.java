package cn.brightstorage.utils;

import cn.brightstorage.config.SecurityConfig;
import cn.brightstorage.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

@Slf4j
@Component
public class AESUtil implements InitializingBean {

    private Cipher encryptor;
    private Cipher decryptor;

    @Override
    public void afterPropertiesSet() throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        try {
            Key key = new SecretKeySpec(SecurityConfig.AES_KEY.getBytes(StandardCharsets.UTF_8), "AES");
            encryptor = Cipher.getInstance("AES/ECB/PKCS7Padding");
            encryptor.init(Cipher.ENCRYPT_MODE, key);
            decryptor = Cipher.getInstance("AES/ECB/PKCS7Padding");
            decryptor.init(Cipher.DECRYPT_MODE, key);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    public String encrypt(String content){
        try {
            return new String(Base64Utils.encode(encryptor.doFinal(content.getBytes(StandardCharsets.UTF_8))));
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param content Base64 encrypted string
     * @return decrypted content
     */
    public String decrypt(String content){
        try {
            return new String(decryptor.doFinal(Base64Utils.decode(content.getBytes(StandardCharsets.UTF_8))));
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            log.error("Error in parsing AES encrypted password: " + content);
            throw new BadRequestException("解析密码异常");
        }
    }
}
