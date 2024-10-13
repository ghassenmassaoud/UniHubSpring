package tn.esprit.pidevarctic.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tn.esprit.pidevarctic.Repository.UserRepository;
import tn.esprit.pidevarctic.entities.User;


import java.io.IOException;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationEntryPoint {
    private int maxAttempts = 3;
    private Map<String, Integer> attemptsCache = new HashMap<>();
    private long lockoutDuration = 5 * 60 * 1000;
    private ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private UserRepository userRepository;
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        if(authException.getMessage().equals("User already exists")){
            response.getOutputStream().print("Email already exists");
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
        if(authException.getMessage().equals("Bad credentials")){
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getOutputStream().print("invalid password");}
        if(authException.getMessage().equals("UserDetailsService returned null, which is an interface contract violation")){
            response.getOutputStream().print("invalid email");
            response.setStatus(HttpStatus.BAD_REQUEST.value());}
        if(authException.getMessage().equals("User is disabled")){
            response.getOutputStream().print("Account is disabled");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());}
        if(authException.getMessage().equals("User account is locked")){
            response.getOutputStream().print("Account is locked");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());}
        if(authException.getMessage().equals(" Full authentication is required to access this resource")){
            response.setStatus(HttpStatus.FORBIDDEN.value());}
        if(authException.getMessage().equals("Password is MODERATE")){
            response.getOutputStream().print("Password is MODERATE");
            response.setStatus(HttpStatus.BAD_REQUEST.value());}
        if(authException.getMessage().equals("Password is WEAK")){
            response.getOutputStream().print("Password is WEAK");
            response.setStatus(HttpStatus.BAD_REQUEST.value());}



//        Map<String, Object> data = new HashMap<>();
//
//        data.put(
//                "exception",
//                authException.getMessage());
//        response.getOutputStream()
//                .println(objectMapper.writeValueAsString(data));
//System.out.println(data);
    }
    @EventListener
    public void onAuthenticationFailure(AuthenticationFailureBadCredentialsEvent event) {
        Object principal = event.getAuthentication().getPrincipal();
        if (principal instanceof String) {
            String username = (String) event.getAuthentication().getPrincipal();
            if (username != null) {
                int attempts = attemptsCache.getOrDefault(username, 0) + 1;
                System.out.println(attempts+" Tentative");
                attemptsCache.put(username, attempts);
                if (attempts >= maxAttempts) {
                    lockAccount(username);

                }
            }

        }
    }
    private void lockAccount(String username) {
        User user = userRepository.findByEmail(username).orElse(null);
        if (user != null) {
            user.setLocked(true);
            user.setLockedReason("BadCredentials");
            user.setLockoutExpiration(LocalDateTime.now().plusMinutes(2));
            userRepository.save(user);
        }
    }

}
