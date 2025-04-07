package br.com.thiago.todolist.tasks;

import br.com.thiago.todolist.tasks.controller.TaskController;
import br.com.thiago.todolist.tasks.repository.InTasksRepos;
import br.com.thiago.todolist.users.UserModel;
import br.com.thiago.todolist.users.repository.InUserRepos;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskControllerTest {

    @Test
    public void testCreateTaskWithValidUser() throws Exception {

        InTasksRepos taskRepo = mock(InTasksRepos.class);
        InUserRepos userRepo = mock(InUserRepos.class);

        TaskController controller = new TaskController();
        controller.taskRepository = taskRepo;
        controller.userRepository = userRepo;

        UUID userId = UUID.randomUUID();
        UserModel user = new UserModel();
        user.setId(userId);

        TaskModel taskData = new TaskModel();
        taskData.setTitle("Test Task");
        taskData.setDescription("Descrição");
        taskData.setPriority("Alta");
        taskData.setStartsAt(LocalDateTime.now());
        taskData.setEndsAt(LocalDateTime.now().plusDays(1));

        when(userRepo.findById(userId)).thenReturn(Optional.of(user));
        when(taskRepo.save(any(TaskModel.class))).thenReturn(taskData);

        ResponseEntity<Object> response = controller.createTask(userId, taskData);

        assertEquals(201, response.getStatusCodeValue());
        verify(taskRepo, times(1)).save(any(TaskModel.class));
    }

}
