package br.com.thiago.todolist.users;

import br.com.thiago.todolist.users.repository.InUserRepos;
import br.com.thiago.todolist.users.controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Test
    public void testCreateUserSuccessfully() {

        InUserRepos mockRepo = mock(InUserRepos.class);
        UserController controller = new UserController(mockRepo);

        UserModel user = new UserModel();
        user.setUsername("TestUser");
        user.setPassword("123456789");

        when(mockRepo.findByUsername("testuser")).thenReturn(null);
        when(mockRepo.save(any(UserModel.class))).thenReturn(user);

        ResponseEntity<?> response = controller.create(user);

        assertEquals(201, response.getStatusCodeValue());
        verify(mockRepo, times(1)).save(any(UserModel.class));
    }

    @Test
    public void testGetUserNotFound() {
        
        InUserRepos mockRepo = mock(InUserRepos.class);
        UserController controller = new UserController(mockRepo);

        UUID fakeId = UUID.randomUUID();

        when(mockRepo.findById(fakeId)).thenReturn(Optional.empty());

        ResponseEntity<?> response = controller.getById(fakeId);

        assertEquals(404, response.getStatusCodeValue());
    }
}
