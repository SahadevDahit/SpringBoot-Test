package com.example.test.controllers;

import com.example.test.entities.RoleEntity;
import com.example.test.services.RoleServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.Role;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleServices roleServices;

    @GetMapping("/")
    public ResponseEntity<?> getRoles(){
        return roleServices.getAllRoles();
    }

    @PostMapping("/")
    public ResponseEntity<RoleEntity> addRole(@RequestBody RoleEntity role){
        System.out.println("Role ---> "+role);
        return roleServices.addRole(role);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleEntity> getRoleById(@PathVariable Long id){
        return roleServices.getRoleById(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RoleEntity> updateRole(@PathVariable Long id,@RequestBody  RoleEntity role){
        return roleServices.updateRole(id,role);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoleById(@PathVariable Long id){
        return roleServices.deleteRoleById(id);
    }



}
