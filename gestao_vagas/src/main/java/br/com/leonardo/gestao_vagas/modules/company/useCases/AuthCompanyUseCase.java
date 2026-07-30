package br.com.leonardo.gestao_vagas.modules.company.useCases;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.leonardo.gestao_vagas.modules.company.dto.AuthCompanyDto;
import br.com.leonardo.gestao_vagas.modules.company.dto.AuthCompanyResponseDTO;
import br.com.leonardo.gestao_vagas.modules.company.repositories.CompanyRepositories;

@Service
public class AuthCompanyUseCase {

        @Value("${security.token.secret}")
        private String secretKey;

        @Autowired
        private  CompanyRepositories companyRepositories;
        @Autowired
        private  PasswordEncoder passwordEncoder;


      AuthCompanyUseCase(CompanyRepositories companyRepositories, PasswordEncoder passwordEncoder) {
            this.companyRepositories = companyRepositories;
            this.passwordEncoder = passwordEncoder;
      }


    public AuthCompanyResponseDTO execute(AuthCompanyDto authCompanyDto) throws AuthenticationException{

        var company = this.companyRepositories.findByUsername(authCompanyDto.getUsername()).orElseThrow(
            () ->{
                throw new UsernameNotFoundException("Company não encontrada");
            }
        );

          var senhasIguais =   this.passwordEncoder.matches(authCompanyDto.getPassword(), company.getPassword());

            if (!senhasIguais) {
                throw new AuthenticationException();
                
            }

            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            var expiresIn = Instant.now().plus(Duration.ofHours(2));

            var token = JWT.create().withIssuer("javagas")
            .withExpiresAt(expiresIn)
            .withSubject(company.getId().toString())
            .withClaim("roles", Arrays.asList("COMPANY"))
            .sign(algorithm);


          var  authCompanyResponseDTO=   AuthCompanyResponseDTO.builder()
            .acess_token(token)
            .expires_in(expiresIn.toEpochMilli())
            .build();

            return authCompanyResponseDTO;




    }
    
}
