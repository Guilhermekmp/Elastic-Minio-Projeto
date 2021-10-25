package com.basis.campina.xtarefas.web.rest;

import com.basis.campina.xtarefas.builder.TarefaBuilder;
import com.basis.campina.xtarefas.config.containers.AbstractTestIT;
import com.basis.campina.xtarefas.config.containers.ContainersFactory;
import com.basis.campina.xtarefas.domain.Tarefa;
import com.basis.campina.xtarefas.service.TarefaService;
import com.basis.campina.xtarefas.service.elasticsearch.TarefaElasticSearchService;
import com.basis.campina.xtarefas.service.event.TarefaEvent;
import com.basis.campina.xtarefas.service.filter.TarefaFilter;
import com.basis.campina.xtarefas.service.mapper.TarefaMapper;
import com.basis.campina.xtarefas.util.TestUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;

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
public class TarefaResourceIT extends AbstractTestIT<TarefaResource> {

  private static final String API = "/api/tarefas/";

  @Autowired
  private TarefaService tarefaService;

  @Autowired
  private TarefaElasticSearchService elasticSearchService;

  @Autowired
  private TarefaMapper mapper;

  @Autowired
  private TarefaBuilder tarefaBuilder;

  @Container
  private ContainersFactory containers = ContainersFactory.getInstance();

  @BeforeEach
  public void init() {super.init(new TarefaResource(tarefaService));}

  @Test
  @DisplayName("Salvar tarefa com sucesso")
  @SneakyThrows
  public void salvarTarefa(){

    Tarefa tarefa = tarefaBuilder.construirEntidade();

    getMockMvc().perform(post(API)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mapper.toDTO(tarefa))))
            .andExpect(status().isOk());

  }

  @Test
  @DisplayName("Salvar Tarefa erro")
  @SneakyThrows
  public void salvarTarefaErro(){
    Tarefa tarefa = tarefaBuilder.construirEntidade();
    tarefa.setDataInicio(LocalDate.now().minusYears(2));

    getMockMvc().perform(post(API)
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(mapper.toDTO(tarefa))))
            .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("Buscar todos Tarefas")
  @SneakyThrows
  public void buscarTodos(){
    tarefaBuilder.construir();
    tarefaBuilder.construir();

    getMockMvc().perform(get(API))
            .andExpect(jsonPath("$.[*]").isNotEmpty())
            .andExpect(status().isOk());

  }

  @Test
  @DisplayName("Buscar Tarefa Por id ")
  @SneakyThrows
  public void buscarPorId(){
    Tarefa tarefa = tarefaBuilder.construir();

    getMockMvc().perform(get(API + tarefa.getId()))
            .andExpect(jsonPath("$.nome").value(tarefa.getNome()))
            .andExpect(jsonPath("$.dataConclusao").value(tarefa.getDataConclusao().toString()))
            .andExpect(jsonPath("$.dataInicio").value(tarefa.getDataInicio().toString()))
            .andExpect(jsonPath("$.status").value(tarefa.getStatus()))
            .andExpect(jsonPath("$.idResponsavel").value(tarefa.getResponsavel().getId()))
            .andExpect(status().isOk());
  }

  @Test
  @DisplayName("Buscar Tarefa Por id inexistente ")
  @SneakyThrows
  public void buscarPorIdInexistente(){

    getMockMvc().perform(get(API + 999))
            .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("Editar com sucesso")
  @SneakyThrows
  public void editarTarefa(){
    Tarefa tarefa = tarefaBuilder.construir();
    tarefa.setNome("Guilherme");

    getMockMvc().perform(put(API)
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(mapper.toDTO(tarefa))))
            .andExpect(status().isOk());
  }

  @Test
  @DisplayName("Buscar tarefas paginados")
  @SneakyThrows
  public void buscarPaginado(){

    Tarefa tarefa = tarefaBuilder.construir();
    Tarefa proximo = tarefaBuilder.customizar(cust -> cust.setNome("Pagamento")).construir();

    elasticSearchService.reindex(new TarefaEvent(tarefa.getId()));
    elasticSearchService.reindex(new TarefaEvent(proximo.getId()));

    TarefaFilter filtro = new TarefaFilter();
    filtro.setQuerry(proximo.getNome());

    getMockMvc().perform(post(API + "pesquisar")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(getConverter().getObjectMapper().writeValueAsBytes(filtro)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").isNotEmpty());
  }

  @Test
  @DisplayName("Deletar Tarefa por Id")
  @SneakyThrows
  public void deletarPorId(){
    Tarefa tarefa = tarefaBuilder.construir();

    getMockMvc().perform(delete(API + tarefa.getId()))
            .andExpect(status().isOk());
  }
}
