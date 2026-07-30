package br.com.leonardo.gestao_vagas.modules.candidate.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.leonardo.gestao_vagas.exceptions.UserFoundExcecption;
import br.com.leonardo.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.leonardo.gestao_vagas.modules.candidate.CandidateRepository;


@Service
public class CreateCandedateUseCase {
    private final CandidateRepository candidateRepository;

    CreateCandedateUseCase(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;
    
   public CandidateEntity execute(CandidateEntity candidateEntity){
      this.candidateRepository.findByUsernameOrEmail(candidateEntity.getUsername(),candidateEntity.getEmail()).ifPresent((user) ->{
                 throw new UserFoundExcecption();
        });
           
      var password = passwordEncoder.encode(candidateEntity.getPassword());
      candidateEntity.setPassword(password);
        return   this.candidateRepository.save(candidateEntity);

    }
    
}
