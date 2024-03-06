package com.example.chatapp.security;

import com.example.chatapp.domain.User;
import com.example.chatapp.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DomainUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    public DomainUserDetailService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if(userOpt.isEmpty()){
            throw new UsernameNotFoundException("User not found !");
        }
        return new CustomUser(userOpt.get());
    }
}
