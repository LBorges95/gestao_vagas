package br.com.leonardo.gestao_vagas.modules.candidate.UseCases;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.leonardo.gestao_vagas.exceptions.JobNotFoundException;
import br.com.leonardo.gestao_vagas.exceptions.UserNotFoundException;
import br.com.leonardo.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.leonardo.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.leonardo.gestao_vagas.modules.candidate.entity.ApplyJobEntity;
import br.com.leonardo.gestao_vagas.modules.candidate.repository.ApplyJobRepository;
import br.com.leonardo.gestao_vagas.modules.candidate.useCases.ApplyJobCandidateUseCase;
import br.com.leonardo.gestao_vagas.modules.company.entities.JobEntity;
import br.com.leonardo.gestao_vagas.modules.company.repositories.JobRepository;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateUseCaseTest {
    @InjectMocks
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

      @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private ApplyJobRepository applyJobRepository;


    @Test
    @DisplayName("should note be able to apply job with candidate not found")
    public void should_not_be_able_to_apply_job_with_candidate_not_foun(){

       try{
         applyJobCandidateUseCase.execute(null, null);
       }catch(Exception e){
        assertThat(e).isInstanceOf(UserNotFoundException.class);
       }

    }

    @Test
    public void should_not_be_able_apply_job_with_not_found(){
      var idCandidate = UUID.randomUUID();

      var candidate = new CandidateEntity();
      candidate.setId(idCandidate);
      when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(candidate));

        try{
         applyJobCandidateUseCase.execute(idCandidate, null);
       }catch(Exception e){
        assertThat(e).isInstanceOf(JobNotFoundException.class);
       }
    }

    @Test
    public void should_be_able_to_create_a_new_apply_job(){

      var idCandidate = UUID.randomUUID();
      var idJoB = UUID.randomUUID();

      var applyJob = ApplyJobEntity.builder().candidateId(idCandidate)
      .jobId(idJoB).build();

      
      var applyJobCreated = ApplyJobEntity.builder().id(UUID.randomUUID()).build();

    

      when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(new CandidateEntity()));
      when(jobRepository.findById(idJoB)).thenReturn(Optional.of(new JobEntity()));

      when(applyJobRepository.save(applyJob)).thenReturn(applyJobCreated);

      var result = applyJobCandidateUseCase.execute(idCandidate, idJoB);

      assertThat(result).hasFieldOrProperty("id");

      assertNotNull(result.getId());

    }

    
}
