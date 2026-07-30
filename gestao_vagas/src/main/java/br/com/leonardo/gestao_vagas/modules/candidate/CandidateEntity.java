package br.com.leonardo.gestao_vagas.modules.candidate;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
@Entity(name = "candidate")
public class CandidateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Schema(example =  "Ricardo Almeida", requiredMode = RequiredMode.REQUIRED)
    private String name;
    @NotBlank()
    @Pattern(regexp = "\\S+", message="O username não pode conter espaços")
    @Schema(example =  "ricardo.almeida", requiredMode = RequiredMode.REQUIRED)
    private String username;
    @Email(message = "O e-mail encontra-se em um formato inválido")
    @Schema(example =  "ricardo@gmail.com", requiredMode = RequiredMode.REQUIRED)
    private String email;

    @Length(min = 10, max =100)
    @Schema(example =  "admin@123", minLength = 10, maxLength = 100, requiredMode = RequiredMode.REQUIRED)
    private String password;
    @Schema(example =  "Desenvolvedor Ruby", description = "Breve descrição do candidato")
    private String description;
    private String curriculum;

    @CreationTimestamp
    private LocalDateTime createdAt;
    
}
