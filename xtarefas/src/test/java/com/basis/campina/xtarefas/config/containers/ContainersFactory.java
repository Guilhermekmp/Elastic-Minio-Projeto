package com.basis.campina.xtarefas.config.containers;

import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;

import java.util.Objects;

public class ContainersFactory {

  @Container
  private static ContainersFactory containersFactory;

  @Container
  private static ElasticsearchContainer elasticsearchContainer;

  public static ContainersFactory getInstance(){
    if(Objects.isNull(elasticsearchContainer)){
      elasticsearchContainer = ElasticSearchFactory.getInstance();
    }

    if(Objects.isNull(containersFactory)){
      containersFactory = new ContainersFactory();
    }

    return containersFactory;
  }

}
