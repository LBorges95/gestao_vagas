package br.com.leonardo.gestao_vagas.modules.company.useCases;

import java.net.PasswordAuthentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.leonardo.gestao_vagas.exceptions.UserFoundExcecption;
import br.com.leonardo.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.leonardo.gestao_vagas.modules.company.repositories.CompanyRepositories;

@Service
public class CreateCompanyUseCases {

    @Autowired
    private CompanyRepositories companyRepositories;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CompanyEntity execute(CompanyEntity companyEntity){

        this.companyRepositories.findByUsernameOrEmail(companyEntity.getUsername(), companyEntity.getEmail())
        .ifPresent((user ) -> {
            throw new UserFoundExcecption();
        });

            var password = passwordEncoder.encode(companyEntity.getPassword());
            companyEntity.setPassword(password);
        return this.companyRepositories.save(companyEntity);
    }
    
}
