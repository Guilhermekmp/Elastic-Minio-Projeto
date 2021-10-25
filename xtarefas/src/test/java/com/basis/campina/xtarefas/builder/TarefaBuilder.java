package com.basis.campina.xtarefas.builder;

import com.basis.campina.xtarefas.domain.Tarefa;
import com.basis.campina.xtarefas.service.TarefaService;
import com.basis.campina.xtarefas.service.dto.TarefaDTO;
import com.basis.campina.xtarefas.service.mapper.TarefaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collection;

@Component
public class TarefaBuilder extends ConstrutorDeEntidade<Tarefa> {

  @Autowired
  private TarefaService tarefaService;

  @Autowired
  private TarefaMapper tarefaMapper;

  @Autowired
  private ResponsavelBuilder responsavelBuilder;

  @Override
  public Tarefa construirEntidade() {
    Tarefa tarefa = new Tarefa();
    tarefa.setNome("Test");
    tarefa.setStatus(true);
    tarefa.setDataInicio(LocalDate.now().plusDays(21));
    tarefa.setDataConclusao(LocalDate.now().plusMonths(11));
    tarefa.setResponsavel(responsavelBuilder.construir());
    return tarefa;
  }

  @Override
  protected Tarefa persistir(Tarefa entidade) {
    TarefaDTO dto = tarefaService.salvar(tarefaMapper.toDTO(entidade));
    return tarefaMapper.toEntity(dto);
  }

  @Override
  public Tarefa obterPorId(Long id) {
    return null;
  }

  @Override
  public Collection<Tarefa> obterTodos() {
    return null;
  }
}
