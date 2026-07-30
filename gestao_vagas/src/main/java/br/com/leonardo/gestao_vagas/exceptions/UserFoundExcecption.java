package br.com.leonardo.gestao_vagas.exceptions;

public class UserFoundExcecption extends RuntimeException {
    public UserFoundExcecption(){
        super("Usuário já existe");
    }
    
}
