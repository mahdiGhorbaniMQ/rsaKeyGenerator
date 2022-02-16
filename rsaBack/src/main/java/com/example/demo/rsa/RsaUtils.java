package com.example.demo.rsa;

import com.example.demo.EmailServiceImpl;
import com.example.demo.models.RsaKey;
import com.example.demo.models.User;
import com.example.demo.repositories.RsaKeyRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class RsaUtils {

    @Autowired
    RsaKeyRepository rsaKeyRepository;
    @Autowired
    EmailServiceImpl emailService;
    @Autowired
    UserRepository userRepository;

    public Boolean generateNewUniqueRsaKeyForUser(String username) throws NoSuchAlgorithmException {

        if(rsaKeyRepository.existsByUsername(username)){
            return false;
        }
        String email;
        User user = userRepository.findByUsername(username).orElseThrow();
        email = user.getEmail();

        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        KeyPair pair = generator.generateKeyPair();
        PrivateKey privateKey = pair.getPrivate();
        PublicKey publicKey = pair.getPublic();
        if(rsaKeyRepository.existsByPrivateKey(privateKey)){
            return generateNewUniqueRsaKeyForUser(username);
        }
        else {
            RsaKey rsaKey = new RsaKey(username,publicKey,privateKey);
            rsaKeyRepository.save(rsaKey);
            String privateKeyString = Base64.getEncoder().encodeToString(privateKey.getEncoded());
            emailService.sendSimpleMessage(email,"your privateKey",privateKeyString);
            return true;
        }
    }

    public Boolean changeRsaKeyForUser(String username) throws NoSuchAlgorithmException {

        if(!rsaKeyRepository.existsByUsername(username)){
            return false;
        }
        String email;
        User user = userRepository.findByUsername(username).orElseThrow();
        email = user.getEmail();

        RsaKey userRsaKey = rsaKeyRepository.getByUsername(username);
        rsaKeyRepository.delete(userRsaKey);
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        KeyPair pair = generator.generateKeyPair();
        PrivateKey privateKey = pair.getPrivate();
        PublicKey publicKey = pair.getPublic();
        if(rsaKeyRepository.existsByPrivateKey(privateKey)){
            return generateNewUniqueRsaKeyForUser(username);
        }
        else {
            RsaKey rsaKey = new RsaKey(username,publicKey,privateKey);
            rsaKeyRepository.save(rsaKey);
            String privateKeyString = Base64.getEncoder().encodeToString(privateKey.getEncoded());
            emailService.sendSimpleMessage(email,"your privateKey",privateKeyString);
            return true;
        }
    }
    public Boolean getUserKeyStatus(String username) throws NoSuchAlgorithmException {

        if(rsaKeyRepository.existsByUsername(username)){
            return true;
        }
        return false;
    }

    public String cryptMessageForUser(String username,String message) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException, InvalidKeySpecException {

        RsaKey rsaKey = rsaKeyRepository.getByUsername(username);

        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, rsaKey.getPublicKey());
        byte[] secretMessageBytes = message.getBytes(StandardCharsets.UTF_8);
        byte[] encryptedMessageBytes = encryptCipher.doFinal(secretMessageBytes);
        String encodedMessage = Base64.getEncoder().encodeToString(encryptedMessageBytes);
        return encodedMessage;
    }


    public String decryptMessageForUser(String username, String message) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, IOException, InvalidKeySpecException {


        byte[] secretMessageBytes = Base64.getDecoder().decode(message);

        RsaKey rsaKey = rsaKeyRepository.getByUsername(username);
        Cipher decryptCipher = Cipher.getInstance("RSA");
        decryptCipher.init(Cipher.DECRYPT_MODE, rsaKey.getPrivateKey());

        byte[] decryptedMessageBytes = decryptCipher.doFinal(secretMessageBytes);
        String decryptedMessage = new String(decryptedMessageBytes, StandardCharsets.UTF_8);

        return decryptedMessage;
    }

}
