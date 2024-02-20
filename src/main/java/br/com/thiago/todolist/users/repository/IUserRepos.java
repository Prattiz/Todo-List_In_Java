package br.com.thiago.todolist.users.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.thiago.todolist.users.UserModel;



public interface IUserRepos extends JpaRepository<UserModel, UUID> {

    UserModel findByUsername(String username);
} 
