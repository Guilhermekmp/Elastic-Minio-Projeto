package com.basis.campina.xtarefas.web.rest;

import com.basis.campina.xtarefas.builder.ResponsavelBuilder;
import com.basis.campina.xtarefas.config.containers.AbstractTestIT;
import com.basis.campina.xtarefas.service.elasticsearch.ElasticSearchService;
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
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@Transactional
@Slf4j
@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ElasticSearchResourceIT extends AbstractTestIT<ElasticsearchResource> {

  private static final String API = "/api/elasticsearch/reindex/";

  @Autowired
  private ElasticSearchService elasticSearchService;

  @Autowired
  private ResponsavelBuilder builder;

  @BeforeEach
  public void init() {super.init(new ElasticsearchResource(elasticSearchService));}

  @Test
  @DisplayName("reindex todas as tabelas")
  @SneakyThrows
  public void reindexTodos(){
    builder.construir();

    getMockMvc().perform(get(API))
            .andExpect(status().isOk());

  }

  @Test
  @DisplayName("reindex uma tabela")
  @SneakyThrows
  public void reindexUnico() {
    builder.construir();

    getMockMvc().perform(get(API + "responsavel"))
            .andExpect(status().isOk());

  }
}
