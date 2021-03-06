package com.basis.campina.xtarefas.service.mapper;

import com.basis.campina.xtarefas.domain.Tarefa;
import com.basis.campina.xtarefas.service.dto.TarefaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = ResponsavelMapper.class)
public interface TarefaMapper {

  @Mapping(target = "responsavel.id", source = "idResponsavel")
  Tarefa toEntity(TarefaDTO dto);

  @Mapping(target = "idResponsavel", source = "responsavel.id")
  TarefaDTO toDTO(Tarefa tarefa);

  List<TarefaDTO> toListagemDTO(List<Tarefa> tarefas);
}
