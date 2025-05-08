package dev.project.Leads.entities.DTO;


public record UserResponseDTO(
        Long id,
        String name,
        String comment,
        String email
) {

}
