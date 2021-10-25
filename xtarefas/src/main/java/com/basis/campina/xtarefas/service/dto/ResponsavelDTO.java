package com.basis.campina.xtarefas.service.dto;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ResponsavelDTO implements Serializable {

  private static final long serialVersionUID = -1704713543257179233L;

  private Long id;

  private String nome;

  private String email;

  private List<TarefaDTO> tarefas;

  private LocalDate dataNascimento;
}
