package com.basis.campina.xtarefas.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "`TB_RESPONSAVEL`")
public class Responsavel implements Serializable {

  private static final long serialVersionUID = -607445027998206048L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "`SQ_RESPONSAVEL`")
  @SequenceGenerator(name = "`SQ_RESPONSAVEL`", sequenceName = "`SQ_RESPONSAVEL`", allocationSize = 1)
  @Column(name = "`ID`")
  private Long id;

  @Column(name = "`NOME`")
  private String nome;

  @Column(name = "`EMAIL`")
  private String email;

  @Column(name = "`DATA_NASCIMENTO`")
  private LocalDate dataNascimento;

  @OneToMany(mappedBy = "responsavel", cascade = CascadeType.ALL)
  private List<Tarefa> tarefas;
}
