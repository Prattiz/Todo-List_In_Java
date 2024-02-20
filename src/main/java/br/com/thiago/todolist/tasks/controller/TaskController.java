package br.com.thiago.todolist.tasks.controller;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.thiago.todolist.tasks.TaskModel;
import br.com.thiago.todolist.tasks.repository.InTasksRepos;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private InTasksRepos inTasksRepos;
    
    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request){
        
        var idUser = request.getAttribute("idUser");

        taskModel.setIdUser((UUID) idUser);

        var currentDate = LocalDateTime.now();
        
        if(currentDate.isAfter(taskModel.getStartsAt()) || currentDate.isAfter(taskModel.getEndsAt())){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("The start date or end date must be greater than the current date");
        }

        if(taskModel.getStartsAt().isAfter(taskModel.getEndsAt())){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("The start date must be less than the end date");
        }

        var task = this.inTasksRepos.save(taskModel);



        return ResponseEntity.status(HttpStatus.OK).body(task);
    }
}
