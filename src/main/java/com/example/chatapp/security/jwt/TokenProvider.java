package com.example.chatapp.security.jwt;

import com.example.chatapp.domain.User;
import com.example.chatapp.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TokenProvider {
    private static final Logger log = LoggerFactory.getLogger(TokenProvider.class);
    private String secretKey  = "NzRkYWQ5ZWViZjkxYjg4ZTUyZWIxODFiMTBhOWFlMjhjYzk3YTE3Yjc2YjY0NTM3YjYyMmE3YTdkZWQzOGY3NzB" +
            "jMjViOTMyNTFmMDkwYzhhZmViMWViYzllZjM2MmQxMTNkZDQ4YTEzNzAyYTg1NzA4MWE0OWFiOGQxY2I4Njg=";
    private long tokenExpire = 86400;
    private static final String AUTHORITIES_KEY = "auth";
    private static final String INVALID_JWT_TOKEN = "Invalid JWT token !";
    private final Key key;
    private final JwtParser jwtParser;
    private final long tokenValidityInMilliSecond;
    private final UserRepository userRepository;


    public TokenProvider(UserRepository userRepository) {
        log.debug("Using base64-encode JWT secret key");
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        key = Keys.hmacShaKeyFor(keyBytes);
        this.jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
        this.tokenValidityInMilliSecond = 1000 * tokenExpire;
        this.userRepository = userRepository;
    }

    public String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        long now = (new Date()).getTime();
        Optional<User> userOtp = userRepository.findByEmail(authentication.getName());
        User user = userOtp.get();
        Date validity = new Date(now + this.tokenValidityInMilliSecond);

        return Jwts
                .builder()
                .setSubject(user.getId())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(this.key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = this.jwtParser.parseClaimsJws(token).getBody();
        Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .filter(auth -> auth.trim().isEmpty())
                .map(SimpleGrantedAuthority::new)
                .toList();

        org.springframework.security.core.userdetails.User principal = new
                org.springframework.security.core.userdetails.User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String token) {
        try{
           this.jwtParser.parseClaimsJws(token);

           return true;
        } catch (Exception e){
            log.error(e.getMessage());
        }

        return false;
    }
}
