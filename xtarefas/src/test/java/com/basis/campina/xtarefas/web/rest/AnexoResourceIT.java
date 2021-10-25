package com.basis.campina.xtarefas.web.rest;

import com.basis.campina.xtarefas.builder.AnexoBuilder;
import com.basis.campina.xtarefas.config.containers.AbstractTestIT;
import com.basis.campina.xtarefas.config.containers.ContainersFactory;
import com.basis.campina.xtarefas.domain.Anexo;
import com.basis.campina.xtarefas.service.AnexoService;
import com.basis.campina.xtarefas.service.elasticsearch.AnexoElasticSearchService;
import com.basis.campina.xtarefas.service.event.AnexoEvent;
import com.basis.campina.xtarefas.service.feign.DocumentClient;
import com.basis.campina.xtarefas.service.filter.AnexoFilter;
import com.basis.campina.xtarefas.service.mapper.AnexoMapper;
import com.basis.campina.xtarefas.util.TestUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@Transactional
@Slf4j
@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AnexoResourceIT extends AbstractTestIT<AnexoResource> {

  private static final String API = "/api/anexos/";

  @Autowired
  private AnexoService anexoService;

  @Autowired
  private AnexoElasticSearchService elasticSearchService;

  @Autowired
  private AnexoMapper mapper;

  @Autowired
  private AnexoBuilder anexoBuilder;

  @Autowired
  private DocumentClient documentClient;

  @Container
  private ContainersFactory containers = ContainersFactory.getInstance();

  @BeforeEach
  public void init() {super.init(new AnexoResource(anexoService));}

  @Test
  @DisplayName("Salvar anexo com sucesso")
  @SneakyThrows
  public void salvarAnexo(){

    Anexo anexo = anexoBuilder.construirEntidade();

    getMockMvc().perform(post(API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mapper.toDTO(anexo))))
            .andExpect(status().isOk());

  }

  @Test
  @DisplayName("Salvar Anexo erro")
  @SneakyThrows
  public void salvarAnexoErro(){
    Anexo anexo = anexoBuilder.construirEntidade();
    anexo.setFile(null);

    getMockMvc().perform(post(API)
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(mapper.toDTO(anexo))))
            .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("Buscar todos Anexos")
  @SneakyThrows
  public void buscarTodos(){
    anexoBuilder.construir();
    anexoBuilder.construir();

    getMockMvc().perform(get(API))
            .andExpect(jsonPath("$.[*]").isNotEmpty())
            .andExpect(status().isOk());

  }

  @Test
  @DisplayName("Buscar Anexo Por id ")
  @SneakyThrows
  public void buscarPorId(){
    Anexo anexo = anexoBuilder.construir();

    Mockito.when(documentClient.buscarDocument(anexo.getUuid())).thenReturn(mapper.toDTO(anexo));

    getMockMvc().perform(get(API + anexo.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.file").value(anexo.getFile()))
            .andExpect(jsonPath("$.uuid").value(anexo.getUuid()))
            .andExpect(jsonPath("$.fileName").value(anexo.getFileName()))
            .andExpect(jsonPath("$.idTarefa").value(anexo.getTarefa().getId()));

  }


  @Test
  @DisplayName("Buscar Anexo Por id sem arquivo")
  @SneakyThrows
  public void buscarPorIdSemArquivo(){
    Anexo anexo = anexoBuilder.construir();

    Mockito.when(documentClient.buscarDocument(anexo.getUuid())).thenReturn(null);

    getMockMvc().perform(get(API + anexo.getId()))
            .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("Buscar Anexo Por id inexistente ")
  @SneakyThrows
  public void buscarPorIdInexistente(){

    getMockMvc().perform(get(API + 999))
            .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("Editar com sucesso")
  @SneakyThrows
  public void editarAnexo(){
    Anexo anexo = anexoBuilder.construir();
    anexo.setFileName("Teste 2");

    Mockito.when(documentClient.buscarDocument(anexo.getUuid())).thenReturn(mapper.toDTO(anexo));

    getMockMvc().perform(put(API)
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(mapper.toDTO(anexo))))
            .andExpect(status().isOk());
  }

  @Test
  @DisplayName("Buscar anexos paginados")
  @SneakyThrows
  public void buscarPaginado(){

    Anexo anexo = anexoBuilder.construir();
    Anexo proximo = anexoBuilder.customizar(cust -> cust.setFileName("Pagamento")).construir();

    elasticSearchService.reindex(new AnexoEvent(anexo.getId()));
    elasticSearchService.reindex(new AnexoEvent(proximo.getId()));

    AnexoFilter filtro = new AnexoFilter();
    filtro.setQuerry(proximo.getFileName());

    getMockMvc().perform(post(API + "pesquisar")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(getConverter().getObjectMapper().writeValueAsBytes(filtro)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").isNotEmpty());
  }

  @Test
  @DisplayName("Deletar Anexo por Id")
  @SneakyThrows
  public void deletarPorId(){
    Anexo anexo = anexoBuilder.construir();

    Mockito.when(documentClient.buscarDocument(anexo.getUuid())).thenReturn(mapper.toDTO(anexo));
    getMockMvc().perform(delete(API + anexo.getId()))
            .andExpect(status().isOk());
  }
}
