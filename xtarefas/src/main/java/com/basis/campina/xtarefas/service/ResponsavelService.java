package com.basis.campina.xtarefas.service;

import com.basis.campina.xtarefas.domain.Responsavel;
import com.basis.campina.xtarefas.domain.elasticsearch.ResponsavelDocument;
import com.basis.campina.xtarefas.repository.ResponsavelRepository;
import com.basis.campina.xtarefas.repository.elastic.ResponsavelSearchRepository;
import com.basis.campina.xtarefas.service.dto.ResponsavelDTO;
import com.basis.campina.xtarefas.service.event.ResponsavelEvent;
import com.basis.campina.xtarefas.service.exception.BadRequestException;
import com.basis.campina.xtarefas.service.filter.ResponsavelFilter;
import com.basis.campina.xtarefas.service.mapper.ResponsavelMapper;
import com.basis.campina.xtarefas.service.util.MensagemUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class ResponsavelService {

  private final ResponsavelRepository repository;
  private final ResponsavelMapper mapper;
  private final ApplicationEventPublisher applicationEventPublisher;
  private final ResponsavelSearchRepository responsavelSearchRepository;

  public ResponsavelDTO salvar(ResponsavelDTO dto){
    if(dto.getDataNascimento().isAfter(LocalDate.now())){
      throw new BadRequestException("Data de nascimento é futura");
    }
    Responsavel responsavel = repository.save(mapper.toEntity(dto));
    applicationEventPublisher.publishEvent(new ResponsavelEvent(responsavel.getId()));
    return mapper.toDTO(responsavel);
  }

  @Transactional(readOnly = true)
  public ResponsavelDTO buscarPorId(Long id){
    return mapper.toDTO(repository.findById(id).orElseThrow(() -> new BadRequestException(MensagemUtil.RESPONSAVEL_NAO_ENCOTRADO)));
  }

  @Transactional(readOnly = true)
  public List<ResponsavelDTO> buscarTodos(){
    return mapper.toListagemDTO(repository.findAll());
  }

  public void remover(Long id){
    repository.deleteById(id);
    responsavelSearchRepository.deleteById(id);
  }

  public ResponsavelDTO editar(ResponsavelDTO dto){
    this.buscarPorId(dto.getId());
    return this.salvar(dto);
  }

  public Page<ResponsavelDocument> pesquisar(ResponsavelFilter responsavelFilter, Pageable pageable){
    return responsavelSearchRepository.search(responsavelFilter.getFilter(),pageable);
  }
}
