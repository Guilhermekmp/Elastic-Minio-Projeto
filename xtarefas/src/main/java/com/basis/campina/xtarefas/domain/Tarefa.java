package com.basis.campina.xtarefas.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="`TB_TAREFA`")
public class Tarefa implements Serializable {

  private static final long serialVersionUID = -3064654499182731469L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "`SQ_TAREFA`")
  @SequenceGenerator(name = "`SQ_TAREFA`", sequenceName = "`SQ_TAREFA`", allocationSize = 1)
  @Column(name = "`ID`")
  private Long id;

  @Column(name = "`NOME`")
  private String nome;

  @Column(name = "`DATA_CONCLUSAO`")
  private LocalDate dataConclusao;

  @Column(name = "`DATA_INICIO`")
  private LocalDate dataInicio;

  @Column(name = "`STATUS`")
  private Boolean status;

  @OneToMany(mappedBy = "tarefa", cascade = CascadeType.ALL)
  private List<Anexo> anexos;

  @ManyToOne()
  @JoinColumn(name = "`RESPONSAVEL`")
  private Responsavel responsavel;
}
