package com.miguelhenrique.todosimple.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.miguelhenrique.todosimple.models.User;
import com.miguelhenrique.todosimple.models.dto.UserCreateDTO;
import com.miguelhenrique.todosimple.models.dto.UserUpdateDTO;
import com.miguelhenrique.todosimple.services.UserSevice;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    
    @Autowired
    private UserSevice userSevice;

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable long id) {
        User obj = userSevice.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody UserCreateDTO obj) {
        User user = this.userSevice.fromDTO(obj);
        User newUser = this.userSevice.create(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newUser.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody UserUpdateDTO obj, @PathVariable Long id) {
        obj.setId(id);
        User user = this.userSevice.fromDTO(obj);
        this.userSevice.update(user);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.userSevice.delete(id);
        return ResponseEntity.noContent().build();
    }

}
