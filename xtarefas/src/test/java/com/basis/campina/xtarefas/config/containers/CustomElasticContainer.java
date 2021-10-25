package com.basis.campina.xtarefas.config.containers;

import org.testcontainers.elasticsearch.ElasticsearchContainer;

public class CustomElasticContainer extends ElasticsearchContainer {

  private static final String ELASTIC_SEARCH_DOCKER = "docker.elastic.co/elasticsearch/elasticsearch:7.6.2";

  private static final String CLUSTER_NAME = "cluster.name";

  private static final String ELASTIC_SEARCH = "elasticsearch";

  public CustomElasticContainer() {
    super(ELASTIC_SEARCH_DOCKER);
    this.addFixedExposedPort(9200, 9200);
    this.addFixedExposedPort(9300, 9300);
    this.addEnv(CLUSTER_NAME, ELASTIC_SEARCH);
  }


}
