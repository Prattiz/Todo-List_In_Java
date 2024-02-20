package br.com.thiago.todolist.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.thiago.todolist.users.UserModel;
import br.com.thiago.todolist.users.repository.IUserRepos;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserRepos iUserRepos;
    
    @GetMapping("/")
    public UserModel create(@RequestBody UserModel userModel) {
        return this.iUserRepos.save(userModel);

    }
}
