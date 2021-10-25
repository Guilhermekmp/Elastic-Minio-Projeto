package com.basis.campina.xtarefas.service.dto;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AnexoDTO implements Serializable {

  private static final long serialVersionUID = 234660441581691264L;

  private Long id;

  private String file;

  private String fileName;

  private String uuid;

  private Long idTarefa;
}
