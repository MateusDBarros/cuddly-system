package dev.project.Leads.controller;

import dev.project.Leads.entities.DTO.UserRequest;
import dev.project.Leads.entities.DTO.UserResponseDTO;
import dev.project.Leads.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> createPerson(@Valid @RequestBody UserRequest person) {
        service.createPerson(person);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario criado com sucesso");
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> listUsers() {
        List<UserResponseDTO> responseDTOList = service.listPerson();
        return ResponseEntity.status(HttpStatus.OK).body(responseDTOList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long id, @Valid @RequestBody UserRequest person) {
        UserResponseDTO responseDTO = service.update(id, person);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
