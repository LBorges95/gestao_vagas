package br.com.leonardo.gestao_vagas.exceptions;

import br.com.leonardo.gestao_vagas.modules.candidate.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor

public class ErrosMessageDTO {

    private String message;
    private String fild;
    
}
