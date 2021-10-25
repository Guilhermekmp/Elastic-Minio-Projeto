package com.basis.campina.xtarefas.config;

import com.basis.campina.xtarefas.service.dto.AnexoDTO;
import com.basis.campina.xtarefas.service.feign.DocumentClient;
import lombok.Setter;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@Setter
public class MockConfiguration {

  @MockBean
  private DocumentClient documentClient;

  private static String uuid;

  @PostConstruct
  public void mock(){
    Mockito.when(documentClient.salvar(new AnexoDTO())).thenReturn("Deu certo");
  }

}
