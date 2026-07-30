package br.com.leonardo.gestao_vagas.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leonardo.gestao_vagas.exceptions.CompanyNotFoundExpection;
import br.com.leonardo.gestao_vagas.modules.company.entities.JobEntity;
import br.com.leonardo.gestao_vagas.modules.company.repositories.CompanyRepositories;
import br.com.leonardo.gestao_vagas.modules.company.repositories.JobRepository;

@Service
public class CreateJobUseCase {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CompanyRepositories companyRepositories;

    public JobEntity execute(JobEntity jobEntity){
        companyRepositories.findById(jobEntity.getCompanyID()).orElseThrow(() ->{
            throw new CompanyNotFoundExpection();

        });
        return this.jobRepository.save(jobEntity);

    }
    
}
