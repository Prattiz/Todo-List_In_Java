package br.com.thiago.todolist.users.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.thiago.todolist.users.UserModel;
import br.com.thiago.todolist.users.repository.InUserRepos;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/user")
@Tag(name = "Users", description = "Endpoints para gerenciamento de usuários")
public class UserController {

    private final InUserRepos inUserRepos;

    public UserController(InUserRepos inUserRepos) {
        this.inUserRepos = inUserRepos;
    }

    @PostMapping
    @Operation(
        summary = "Criar novo usuário",
        description = "Registra um novo usuário na aplicação",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Dados do novo usuário",
            content = @Content(
                mediaType = "application/json",
                examples = {
                    @ExampleObject(
                        name = "Exemplo de usuário",
                        value = """
                        {
                          "name": "Thiago Silva",
                          "username": "thiagosilva",
                          "password": "senha123"
                        }
                        """
                    )
                }
            )
        )
    )
    public ResponseEntity<?> create(@RequestBody UserModel userModel) {

        var user = inUserRepos.findByUsername(userModel.getUsername());

        if (user != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
        }

        var hashedPassword = BCrypt.withDefaults()
            .hashToString(12, userModel.getPassword().toCharArray());

        userModel.setPassword(hashedPassword);

        var userCreated = inUserRepos.save(userModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }


    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar usuário por ID",
        description = "Retorna os dados de um usuário específico com base no UUID"
    )
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        
        Optional<UserModel> user = inUserRepos.findById(id);

        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        return ResponseEntity.ok(user.get());
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Atualizar usuário",
        description = "Atualiza as informações de um usuário específico"
    )
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody UserModel updatedUser) {

        Optional<UserModel> existingUser = inUserRepos.findById(id);
        
        if (existingUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        UserModel user = existingUser.get();
        user.setName(updatedUser.getName());
        user.setUsername(updatedUser.getUsername());

        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
            String hashedPassword = BCrypt.withDefaults()
                .hashToString(12, updatedUser.getPassword().toCharArray());
            user.setPassword(hashedPassword);
        }

        var updated = inUserRepos.save(user);
        return ResponseEntity.ok(updated);
    }
}
