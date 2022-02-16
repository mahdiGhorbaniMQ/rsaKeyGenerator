package com.example.demo.controllers;

import com.example.demo.models.RsaKey;
import com.example.demo.payload.request.SecretMessageRequest;
import com.example.demo.payload.response.ResponseMessage;
import com.example.demo.repositories.RsaKeyRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.rsa.RsaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequestMapping("/api/key")
public class KeyController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RsaKeyRepository rsaKeyRepository;
    @Autowired
    RsaUtils rsaUtils;

    @GetMapping("/generate")
    private ResponseEntity<ResponseMessage> generateRsaKey(Principal principal) throws NoSuchAlgorithmException {
        String username = principal.getName();

        if(rsaUtils.generateNewUniqueRsaKeyForUser(username)){
            return ResponseEntity.ok(new ResponseMessage("Rsa key successfully generated!"));
        }
        else return ResponseEntity.badRequest().body(new ResponseMessage("Error: can't generate RsaKey!"));
    }
    @GetMapping("/change")
    private ResponseEntity<ResponseMessage> changeRsaKey(Principal principal) throws NoSuchAlgorithmException {
        String username = principal.getName();

        if(rsaUtils.changeRsaKeyForUser(username)){
            return ResponseEntity.ok(new ResponseMessage("Rsa key successfully changed!"));
        }
        else return ResponseEntity.badRequest().body(new ResponseMessage("Error: can't change RsaKey!"));
    }
    @GetMapping("/status")
    private ResponseEntity<ResponseMessage> rsaKeyStatus(Principal principal) throws NoSuchAlgorithmException {
        String username = principal.getName();

        if(rsaUtils.getUserKeyStatus(username)){
            return ResponseEntity.ok(new ResponseMessage("hasKey"));
        }
        else return ResponseEntity.ok(new ResponseMessage("noKey"));
    }

    @PostMapping("/getCryptMessage")
    private ResponseEntity<ResponseMessage> generateAnEncryptedMessageForUser(Principal principal, @RequestBody ResponseMessage message) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
        String username = principal.getName();

        String encodedMessage = rsaUtils.cryptMessageForUser(username,message.getMessage());

        return ResponseEntity.ok(new ResponseMessage(encodedMessage));
    }

    @PostMapping("encryptMessage")
    private ResponseEntity<ResponseMessage> decodeMessage(Principal principal, @RequestBody SecretMessageRequest secretMessageRequest) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, IOException, InvalidKeySpecException {
        String username = principal.getName();
        String encryptMessage = rsaUtils.decryptMessageForUser(username,secretMessageRequest.getMessage());
        return ResponseEntity.ok(new ResponseMessage(encryptMessage));
    }

}
