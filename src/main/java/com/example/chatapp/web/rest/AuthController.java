package com.example.chatapp.web.rest;

import com.example.chatapp.model.dto.UserDTO;
import com.example.chatapp.model.request.LoginRequest;
import com.example.chatapp.security.jwt.JWTFilter;
import com.example.chatapp.security.jwt.TokenProvider;
import com.example.chatapp.service.AuthService;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final TokenProvider tokenProvider;
    private final AuthService authService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public AuthController(TokenProvider tokenProvider,
                          AuthService authService,
                          AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.tokenProvider = tokenProvider;
        this.authService = authService;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @PostMapping("/login")
    public ResponseEntity<JWTToken> login(@RequestBody LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new JWTToken(jwt, authentication.getName()), httpHeaders, HttpStatus.OK);
    }

    @PostMapping(value = "/register", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void register(@RequestPart("userInfo") UserDTO userDTO,
                         @RequestPart("image") MultipartFile image) {
        authService.createUser(userDTO, image);
    }


    static class JWTToken {
        private String idToken;

        private String userId;

        JWTToken(String idToken, String username) {
            this.idToken = idToken;
            this.userId = username;
        }

        @JsonProperty("accessToken")
        String getIdToken() {
            return this.idToken;
        }

        @JsonProperty("userId")
        String getUserId() {return this.userId;}
    }
}
