package br.com.thiago.todolist.tasks.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.thiago.todolist.tasks.TaskModel;
import java.util.List;


public interface InTasksRepos extends JpaRepository<TaskModel, UUID>{

    List<TaskModel> findByIdUser(UUID idUser);
    TaskModel findByIdAndIdUser(UUID id, UUID idUser);
} 