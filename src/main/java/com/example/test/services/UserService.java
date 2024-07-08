package com.example.test.services;

import com.example.test.entities.RoleEntity;
import com.example.test.entities.UserEntity;
import com.example.test.repositories.RoleRepository;
import com.example.test.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository=roleRepository;
    }

    public String defaultUserService() {
        return "This is from the user service page";
    }


    @Transactional
    public ResponseEntity<String> signup(UserEntity user) {
        try {
            Set<RoleEntity> managedRoles = new HashSet<>();

            // Save roles if they do not exist and attach them to the persistence context
            for (RoleEntity role : user.getRoles()) {
                Optional<RoleEntity> existingRole = roleRepository.findByRole(role.getRole());
                if (existingRole.isEmpty()) {
                    roleRepository.save(role);
                    managedRoles.add(role);
                } else {
                    managedRoles.add(existingRole.get());
                }
            }

            // Attach managed roles to the user
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(managedRoles);

            // Save user
            userRepository.save(user);
            return new ResponseEntity<>("User has successfully signed up", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getAllUsers() {
        try {
            List<UserEntity> allUsers = userRepository.findAll();
            if (!allUsers.isEmpty()) {
                return new ResponseEntity<>(allUsers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getUserDetailsById(String id){
        try{
            Optional<UserEntity> userFound=userRepository.findById(Long.valueOf(id));
            if(userFound.isPresent()){
                return  new ResponseEntity<>(userFound.get(),HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<UserEntity> getByUserName(String username) {
        try {
            Optional<UserEntity> userFound = Optional.ofNullable(userRepository.findByUsername(username));
            return userFound.map(userEntity -> new ResponseEntity<>(userEntity, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteUserById(Long id) {
       try{
           userRepository.deleteById(id);
           return new ResponseEntity<>("User Deleted Sucessfully !!!",HttpStatus.OK);
       }catch (Exception e){
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
    }
}
