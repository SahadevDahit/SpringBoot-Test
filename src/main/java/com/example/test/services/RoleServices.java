package com.example.test.services;

import com.example.test.entities.RoleEntity;
import com.example.test.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PatchMapping;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServices {

    @Autowired
    private final RoleRepository roleRepository;

    public RoleServices(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public ResponseEntity<?> getAllRoles() {
        try{
            List<RoleEntity> roles= roleRepository.findAll();
            return new ResponseEntity<>(roles,HttpStatus.OK);

        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    public ResponseEntity<RoleEntity> getRoleById(Long id){
                try{
                    Optional<RoleEntity> roleFound= roleRepository.findById(id);
                    if(roleFound.isPresent()){
                        return  new ResponseEntity<>(roleFound.get(),HttpStatus.OK);
                    }
                    return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }catch (Exception e){
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
        }

    public ResponseEntity<String> deleteRoleById(Long id) {
        try{
            roleRepository.deleteById(id);
            return new ResponseEntity<>("Role deleted Sucessfully !!! ",HttpStatus.OK);

        }catch(Exception e){
            return  new ResponseEntity<>("Error on deleting Role",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<RoleEntity> updateRole(Long id, RoleEntity role) {
        try {
            Optional<RoleEntity> roleFound = roleRepository.findById(id);
            if (roleFound.isPresent()) {
                RoleEntity existingRole = roleFound.get();
                existingRole.setRole(role.getRole());
                RoleEntity updatedRole = roleRepository.save(existingRole);
                return new ResponseEntity<>(updatedRole, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<RoleEntity> addRole(RoleEntity role) {
        try {
            RoleEntity savedRole = roleRepository.save(role);
            return new ResponseEntity<>(savedRole, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
