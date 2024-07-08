package com.example.test.jwt;

import com.example.test.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class JwtAuthenticationController {

    private final UserDetailsService userDetailsService;
    @Autowired
    private final AuthenticationManager authenticationManager;

    private final JwtHelper jwtHelper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final UserRepository userRepository; // Moved @Autowired to constructor

    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
        authenticate(request.getUsername(), request.getPassword());

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());

        String token = jwtHelper.generateToken(userDetails);

        JwtResponse response = JwtResponse.builder()
                .jwtToken(token)
                .username(userDetails.getUsername()).build();
        return ResponseEntity.ok(response);
    }

    private void authenticate(String username, String password) {
        try {
            log.info("password --->"+password);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid Credentials");
        }
    }
}
