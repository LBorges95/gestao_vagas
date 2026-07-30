package br.com.leonardo.gestao_vagas.modules.candidate.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCandidateResponseDTO {

    @Schema(example = "Desenvolvedora Java")
    private String description;
    @Schema(example = "augusto")
    private String username;
    @Schema(example = "augusto.barros@gmail.com")
    private String email;
    @Schema(example = "Augusto Barros")
    private String name;
    private UUID id;
    
}
