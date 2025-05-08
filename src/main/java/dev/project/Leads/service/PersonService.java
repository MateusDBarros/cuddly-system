package dev.project.Leads.service;

import dev.project.Leads.entities.DTO.PersonRequest;
import dev.project.Leads.entities.DTO.PersonResponseDTO;
import dev.project.Leads.entities.model.Person;
import dev.project.Leads.repository.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private final PasswordEncoder passwordConfig;
    private final PersonRepository repository;

    public PersonService(PasswordEncoder passwordConfig, PersonRepository repository) {
        this.passwordConfig = passwordConfig;
        this.repository = repository;
    }

    public PersonResponseDTO createPerson(PersonRequest dto) {
        Person person = new Person();
        person.setName(dto.name());
        person.setComment(dto.comment());
        person.setEmail(dto.email());
        person.setPassword(dto.password());

        String senhaCriptografada = passwordConfig.encode(dto.password());
        person.setPassword(senhaCriptografada);

        repository.save(person);

        return new PersonResponseDTO(person.getId(), person.getName(), person.getEmail(), person.getComment());
    }

    public List<PersonResponseDTO> listPerson() {
        return repository.findAll().stream().map(person -> new PersonResponseDTO(person.getId(), person.getName(), person.getComment(), person.getEmail())).toList();
    }

    public PersonResponseDTO update(Long id, PersonRequest dto) {
        Person person = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pessoa não encotrada"));

        person.setName(dto.name());
        person.setEmail(dto.email());

        if (dto.password() != null && !dto.password().isBlank()) {
            person.setPassword(passwordConfig.encode(dto.password()));
        }

        repository.save(person);

        return new PersonResponseDTO(
                person.getId(),
                person.getName(),
                person.getEmail(),
                person.getComment()
        );
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Pessoa não encontrada");
        }
        repository.deleteById(id);
    }

}
