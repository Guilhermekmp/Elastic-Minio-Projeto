package com.basis.campina.xtarefas.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "`TB_ANEXO`")
public class Anexo implements Serializable {

  private static final long serialVersionUID = -3819082393237055892L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "`SQ_ANEXO`")
  @SequenceGenerator(name = "`SQ_ANEXO`", sequenceName = "`SQ_ANEXO`", allocationSize = 1)
  @Column(name = "`ID`")
  private Long id;

  @Column(name="`FILE`")
  private String file;

  @Column(name="`FILENAME`")
  private String fileName;

  @Column(name="`UUID`")
  private String uuid;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "`TAREFA`")
  private Tarefa tarefa;

}
