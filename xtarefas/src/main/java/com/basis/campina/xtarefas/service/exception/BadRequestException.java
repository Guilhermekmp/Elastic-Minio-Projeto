package com.basis.campina.xtarefas.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException{

  public BadRequestException(String mensagem){ this(mensagem,null);}

  public BadRequestException(String mensagem, Throwable motivo){ super(mensagem,motivo);}
}
