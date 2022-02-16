package com.example.demo.controllers;

import com.example.demo.EmailServiceImpl;
import com.example.demo.models.DeactiveUser;
import com.example.demo.models.User;
import com.example.demo.payload.request.RegisterRequest;
import com.example.demo.payload.response.ResponseMessage;
import com.example.demo.payload.response.UserDetailsResponse;
import com.example.demo.repositories.DeactiveUserRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    DeactiveUserRepository deactiveUserRepository;
    @Autowired
    EmailServiceImpl emailService;

    @GetMapping("/details")
    private ResponseEntity<UserDetailsResponse> getDetails(Principal principal) throws NoSuchAlgorithmException {
        String username = principal.getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        UserDetailsResponse userDetails = new UserDetailsResponse(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getUsername()
        );
        return ResponseEntity.ok(userDetails);
    }
    @PutMapping("/update")
    private ResponseEntity<ResponseMessage> updateUser(Principal principal, @RequestBody User newUserData) throws NoSuchAlgorithmException {
        String username = principal.getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        DeactiveUser deactiveUser = new DeactiveUser(
                newUserData.getFirstName(),
                newUserData.getLastName(),
                newUserData.getEmail(),
                "", "");

        if(deactiveUserRepository.existsByEmail(newUserData.getEmail()))
            deactiveUserRepository.delete( deactiveUserRepository.findByEmail(newUserData.getEmail()));

        deactiveUserRepository.save(deactiveUser);
        emailService.sendSimpleMessage(newUserData.getEmail(),"this is you activation code.",deactiveUser.getActivationCode());
        return ResponseEntity.ok(new ResponseMessage("Register mail successfully send for user!"));
    }

    @PostMapping("/update/activate")
    private ResponseEntity<ResponseMessage> activateUpdateUser(Principal principal, @RequestBody RegisterRequest registerRequest) throws NoSuchAlgorithmException {
        String username = principal.getName();
        User user = userRepository.findByUsername(username).orElseThrow();

        DeactiveUser deactiveUser = deactiveUserRepository.findByEmail(registerRequest.getEmail());
        if(deactiveUser.getActivationCode().equals(registerRequest.getActivationCode())){
            user.setFirstName(deactiveUser.getFirstName());
            user.setLastName(deactiveUser.getLastName());
            user.setEmail(deactiveUser.getEmail());
            userRepository.save(user);
            deactiveUserRepository.delete(deactiveUser);
            return ResponseEntity.ok(new ResponseMessage("User successfully updated!"));
        }
        return ResponseEntity.badRequest().body(new ResponseMessage("Error: the activation Code is incorrect!"));
    }
}
