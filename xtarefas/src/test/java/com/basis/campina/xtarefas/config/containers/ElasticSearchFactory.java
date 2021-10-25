package com.basis.campina.xtarefas.config.containers;

import org.testcontainers.elasticsearch.ElasticsearchContainer;

import java.util.Objects;

public class ElasticSearchFactory {

  private static CustomElasticContainer container;

  public static ElasticsearchContainer getInstance(){
    if(Objects.isNull(container)){
      container = new CustomElasticContainer();
      container.start();
    }
    return container;
  }
}
