package br.com.thiago.todolist.tasks.controller;

// import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.thiago.todolist.tasks.TaskModel;
import br.com.thiago.todolist.tasks.repository.InTasksRepos;
import br.com.thiago.todolist.users.UserModel;
import br.com.thiago.todolist.users.repository.InUserRepos;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/tasks")
@Tag(name = "Tasks", description = "Endpoints para gerenciamento de tarefas")
public class TaskController {

    @Autowired
    private InTasksRepos taskRepository;

    @Autowired
    private InUserRepos userRepository;

    
    @PostMapping("/{userId}")
    @Operation(
        summary = "Criar tarefa para um usuário",
        description = "Cria uma nova tarefa vinculada a um usuário específico",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados da nova tarefa",
            content = @Content(
                mediaType = "application/json",
                examples = {
                    @ExampleObject(
                        name = "Exemplo de tarefa",
                        value = """
                        {
                          "title": "Estudar Spring Boot",
                          "description": "Revisar anotações e fazer projeto",
                          "priority": "Alta",
                          "startsAt": "2025-04-07T10:00:00",
                          "endsAt": "2025-04-07T12:00:00"
                        }
                        """
                    )
                }
            )
        )
    )
    public ResponseEntity<Object> createTask(@PathVariable UUID userId, @RequestBody TaskModel taskData) {

        Optional<UserModel> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(404).body("Usuário não encontrado.");
        }

        TaskModel task = new TaskModel();
        task.setUser(optionalUser.get());
        task.setTitle(taskData.getTitle());
        task.setDescription(taskData.getDescription());
        task.setPriority(taskData.getPriority());
        task.setStartsAt(taskData.getStartsAt());
        task.setEndsAt(taskData.getEndsAt());
        task.setUser(optionalUser.get());

        TaskModel savedTask = taskRepository.save(task);

        return ResponseEntity.status(201).body(savedTask);
    }

    // @GetMapping("/{userId}")
    // public ResponseEntity<List<TaskModel>> getUserTasks(@PathVariable UUID userId) {

    //     List<TaskModel> tasks = taskRepository.findByUserId(userId);
    //     return ResponseEntity.ok(tasks);
    // }
}
