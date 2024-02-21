package br.com.thiago.todolist.tasks.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.thiago.todolist.tasks.TaskModel;
import br.com.thiago.todolist.tasks.repository.InTasksRepos;
import br.com.thiago.todolist.utils.Utils;
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

    @GetMapping("/")
    public List<TaskModel> list(HttpServletRequest request){

        var idUser = request.getAttribute("idUser");

        return this.inTasksRepos.findByIdUser((UUID) idUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody TaskModel taskModel, @PathVariable UUID id, HttpServletRequest request){

        var task = this.inTasksRepos.findById(id).orElse(null);

        if(task == null){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Task not found");
        }
        
        var idUser = request.getAttribute("idUser");

        if(!task.getIdUser().equals(idUser)){
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("User does not have permission to change this task");
        }

        Utils.copyNonNullProperties(taskModel, task);

        var result = this.inTasksRepos.save(task);

        return ResponseEntity.ok().body(result);
    }
}
