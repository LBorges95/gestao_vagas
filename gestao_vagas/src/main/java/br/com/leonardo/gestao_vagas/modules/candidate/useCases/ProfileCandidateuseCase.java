package br.com.leonardo.gestao_vagas.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leonardo.gestao_vagas.exceptions.UserNotFoundException;
import br.com.leonardo.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.leonardo.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;

@Service
public class ProfileCandidateuseCase {
    
@Autowired
private CandidateRepository candidateRepository;

    public ProfileCandidateResponseDTO execute(UUID idCandidate){

        var candidate = this.candidateRepository.findById(idCandidate)
        .orElseThrow(() ->{
            throw new UserNotFoundException();

        });

        var candidateDto = ProfileCandidateResponseDTO.builder()
        .description(candidate.getDescription())
        .username(candidate.getUsername())
        .email(candidate.getEmail())
        .name(candidate.getName())
        .id(candidate.getId())
        .build();

        return candidateDto;
    }
}
