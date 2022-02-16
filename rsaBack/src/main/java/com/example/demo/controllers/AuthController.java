package com.example.demo.controllers;

import com.example.demo.EmailServiceImpl;
import com.example.demo.models.DeactiveUser;
import com.example.demo.models.User;
import com.example.demo.payload.request.LoginRequest;
import com.example.demo.payload.request.RegisterRequest;
import com.example.demo.payload.request.SignupRequest;
import com.example.demo.payload.response.JwtResponse;
import com.example.demo.payload.response.ResponseMessage;
import com.example.demo.repositories.DeactiveUserRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.security.jwt.JwtUtils;
import com.example.demo.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DeactiveUserRepository deactiveUserRepository;

    @Autowired
    EmailServiceImpl emailService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getFirstName(),
                userDetails.getLastName(),
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Error: Email is already in use!"));
        }

        DeactiveUser deactiveUser = new DeactiveUser(signUpRequest.getFirstName(),
                signUpRequest.getLastName(),
                signUpRequest.getEmail(),
                signUpRequest.getUsername(),
                encoder.encode(signUpRequest.getPassword()));
        if(deactiveUserRepository.existsByEmail(signUpRequest.getEmail()))
            deactiveUserRepository.delete( deactiveUserRepository.findByEmail(signUpRequest.getEmail()));

        deactiveUserRepository.save(deactiveUser);
        emailService.sendSimpleMessage(signUpRequest.getEmail(),"this is you activation code.",deactiveUser.getActivationCode());
        return ResponseEntity.ok(new ResponseMessage("Register mail successfully send for user!"));
    }

    @PostMapping("/signup/activate")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        if (!deactiveUserRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Error: email not found!"));
        }
        DeactiveUser deactiveUser = deactiveUserRepository.findByEmail(registerRequest.getEmail());
        if(registerRequest.getActivationCode().equals(deactiveUser.getActivationCode())){
            User user = new User(
                    deactiveUser.getFirstName(),
                    deactiveUser.getLastName(),
                    deactiveUser.getEmail(),
                    deactiveUser.getUsername(),
                    deactiveUser.getPassword());
            userRepository.save(user);
            deactiveUserRepository.delete(deactiveUser);
            return ResponseEntity.ok(new ResponseMessage("User registered successfully!"));
        }
        return ResponseEntity.badRequest().body(new ResponseMessage("Error: the activation Code is incorrect!"));
    }

}