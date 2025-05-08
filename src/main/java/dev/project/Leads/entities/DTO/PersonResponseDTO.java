package dev.project.Leads.entities.DTO;


public record PersonResponseDTO(
        Long id,
        String name,
        String comment,
        String email
) {

}
