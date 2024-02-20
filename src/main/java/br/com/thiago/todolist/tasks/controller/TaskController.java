package br.com.thiago.todolist.tasks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.thiago.todolist.tasks.TaskModel;
import br.com.thiago.todolist.tasks.repository.InTasksRepos;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private InTasksRepos inTasksRepos;
    
    @PostMapping("/")
    public TaskModel create(@RequestBody TaskModel taskModel){
        
        var task = this.inTasksRepos.save(taskModel);

        return task;
    }
}
