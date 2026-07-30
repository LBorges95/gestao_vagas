package br.com.leonardo.gestao_vagas.modules.company.controllers;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import br.com.leonardo.gestao_vagas.exceptions.CompanyNotFoundExpection;
import br.com.leonardo.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.leonardo.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.leonardo.gestao_vagas.modules.company.repositories.CompanyRepositories;
import br.com.leonardo.gestao_vagas.modules.utils.TestUtils;



@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CreateJobControllerTest {

@Autowired
private MockMvc mvc;

private final ObjectMapper objectMapper = new ObjectMapper();

@Autowired
private CompanyRepositories companyRepositories;

/* 
private void setup(){

    mvc = MockMvcBuilders.webAppContextSetup(context)
    .apply(SecurityMockMvcConfigurers.springSecurity())
    .build();
}*/

    @Test
    public void should_be_able_to_create_a_new_jon() throws Exception{

        var company = CompanyEntity.builder()
        .description("COMPANY_DESCRIPTION")
        .email("EMAIL@COMPANY.COM")
        .password("12314312asdasda")
        .username("Company_Name")
        .name("COMPANY_NAME")
        .build();

        company = companyRepositories.saveAndFlush(company);

       var createJobDTO =  CreateJobDTO.builder()
        .benefits("BENEFITS_TEST")
        .description("DESCRIPTION_TEST")
        .level("LEVEL_TEST")
        .build();

      var result =   mvc.perform(post("/company/job/")
        .contentType(MediaType.APPLICATION_JSON)
       .content(objectMapper.writeValueAsString(createJobDTO))
       .header("Authorization", TestUtils.generateToken(company.getId(),"JAVAGAS_@123#"))
    )
       .andExpect(status().isOk())
       .andReturn();


        System.out.println(result.getResponse().getContentAsString());

    }

    @Test
    public void should_not_be_able_to_create_a_new_job_if_company_not_found() throws JsonProcessingException, Exception{

           var createJobDTO =  CreateJobDTO.builder()
        .benefits("BENEFITS_TEST")
        .description("DESCRIPTION_TEST")
        .level("LEVEL_TEST")
        .build();

      
     mvc.perform(post("/company/job/")
        .contentType(MediaType.APPLICATION_JSON)
       .content(objectMapper.writeValueAsString(createJobDTO))
       .header("Authorization", TestUtils.generateToken(UUID.randomUUID(),"JAVAGAS_@123#")));
     /*  .andExpect(MockMvcResultMatchers.status().isBadRequest())); */

    }
    
    
}

