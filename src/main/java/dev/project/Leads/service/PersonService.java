package dev.project.Leads.service;

import dev.project.Leads.entities.DTO.PersonRequest;
import dev.project.Leads.entities.DTO.PersonResponseDTO;
import dev.project.Leads.entities.model.Person;
import dev.project.Leads.repository.PersonRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private final PasswordEncoder passwordConfig;
    private final PersonRepository repository;

    public PersonService(PasswordEncoder passwordConfig, PersonRepository repository) {
        this.passwordConfig = passwordConfig;
        this.repository = repository;
    }

    public PersonResponseDTO createUser(PersonRequest dto) {
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
}
