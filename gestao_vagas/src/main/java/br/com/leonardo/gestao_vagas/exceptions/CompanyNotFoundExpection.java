package br.com.leonardo.gestao_vagas.exceptions;

public class CompanyNotFoundExpection extends RuntimeException {
    public CompanyNotFoundExpection(){
        super("Company já existe");
    }
    
}