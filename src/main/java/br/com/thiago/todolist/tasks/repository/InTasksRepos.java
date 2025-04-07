package br.com.thiago.todolist.tasks.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.thiago.todolist.tasks.TaskModel;

public interface InTasksRepos extends JpaRepository<TaskModel, UUID> {
    List<TaskModel> findByUserId(UUID userId);
    TaskModel findByIdAndUserId(UUID taskId, UUID userId);
}
