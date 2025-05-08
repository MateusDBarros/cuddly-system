package dev.project.Leads.service;

import dev.project.Leads.entities.DTO.UserRequest;
import dev.project.Leads.entities.DTO.UserResponseDTO;
import dev.project.Leads.entities.model.User;
import dev.project.Leads.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;

    public UserService(PasswordEncoder passwordConfig, UserRepository repository) {
        this.passwordEncoder = passwordConfig;
        this.repository = repository;
    }

    public UserResponseDTO createPerson(UserRequest dto) {
        User user = new User();
        user.setName(dto.name());
        user.setComment(dto.comment());
        user.setEmail(dto.email());

        String senhaCriptografada = passwordEncoder.encode(dto.password());
        user.setPassword(senhaCriptografada);
        repository.save(user);

        return new UserResponseDTO(user.getId(), user.getName(), user.getComment(), user.getEmail());
    }

    public List<UserResponseDTO> listPerson() {
        return repository.findAll().stream().map(user -> new UserResponseDTO(user.getId(), user.getName(), user.getComment(), user.getEmail())).toList();
    }

    public UserResponseDTO update(Long id, UserRequest dto) {
        User user = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pessoa não encotrada"));

        user.setName(dto.name());
        user.setEmail(dto.email());

        if (dto.password() != null && !dto.password().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.password()));
        }
        repository.save(user);

        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getComment()
        );
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Pessoa não encontrada");
        }
        repository.deleteById(id);
    }

}
