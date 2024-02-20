package br.com.thiago.todolist.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.thiago.todolist.users.UserModel;
import br.com.thiago.todolist.users.repository.InUserRepos;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private InUserRepos inUserRepos;
    
    @GetMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel) {

        var user = this.inUserRepos.findByUsername(userModel.getUsername());

        if(user != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
        }

        var hashredPassword = BCrypt.withDefaults()
            .hashToString(12, userModel.getPassword().toCharArray());
        
        userModel.setPassword(hashredPassword);

        

        var userCreated = this.inUserRepos.save(userModel);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);

    }
}
