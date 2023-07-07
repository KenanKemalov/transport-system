package com.example.transportsystemj8.services;

import com.example.transportsystemj8.data.entity.User;
import com.example.transportsystemj8.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService implements UserDetailsService {

    private Pattern passwordPattern = Pattern.compile("^(?=.*?[0-9]).{8,}$");

    //@Autowired
//    private AuthenticationManager authenticationManager;
//    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    public UserService(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserRepository userRepository) {
//        this.authenticationManager = authenticationManager;
//        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            return user.get();
        } else {
            throw new UsernameNotFoundException(MessageFormat.format("User with username {0} cannot be found.", username));
        }
    }

    public boolean checkIfUserExists(User user){
//        Optional<User> foundUser = userRepository.findByUsernameAndUserRole(user.getUsername(), user.getUserRole());
        Optional<User> foundUser = userRepository.findByUsername(user.getUsername());
        return foundUser.isPresent();
    }

    public void saveUser(User user) {
        // Perform any additional validation or logic
        userRepository.save(user);
    }

    public boolean validateUser(String password){
        Matcher matcher = passwordPattern.matcher(password);
        return matcher.matches();
    }

    public boolean validateNewPassword(String password, String confirmPassword){
        Matcher passwordMatcher = passwordPattern.matcher(password);
        Matcher confirmPasswordMatcher = passwordPattern.matcher(confirmPassword);
        return passwordMatcher.matches() && confirmPasswordMatcher.matches() && password.equals(confirmPassword);
//        return password.equals(confirmPassword);
    }

    public void updatePassword(User user, String newPassword){
        user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        userRepository.save(user);
    }
}
