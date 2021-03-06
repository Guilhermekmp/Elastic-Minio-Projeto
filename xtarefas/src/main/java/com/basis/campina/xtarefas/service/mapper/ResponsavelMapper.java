package com.basis.campina.xtarefas.service.mapper;

import com.basis.campina.xtarefas.domain.Responsavel;
import com.basis.campina.xtarefas.service.dto.ResponsavelDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ResponsavelMapper {

  Responsavel toEntity(ResponsavelDTO dto);

  ResponsavelDTO toDTO(Responsavel responsavel);

  List<ResponsavelDTO> toListagemDTO(List<Responsavel> responsaveis);

}
