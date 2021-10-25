package com.basis.campina.xtarefas.service;

import com.basis.campina.xtarefas.domain.Tarefa;
import com.basis.campina.xtarefas.domain.elasticsearch.TarefaDocument;
import com.basis.campina.xtarefas.repository.TarefaRepository;
import com.basis.campina.xtarefas.repository.elastic.TarefaSearchRepository;
import com.basis.campina.xtarefas.service.dto.TarefaDTO;
import com.basis.campina.xtarefas.service.event.TarefaEvent;
import com.basis.campina.xtarefas.service.exception.BadRequestException;
import com.basis.campina.xtarefas.service.filter.TarefaFilter;
import com.basis.campina.xtarefas.service.mapper.TarefaMapper;
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
public class TarefaService {

  private final TarefaRepository repository;
  private final TarefaMapper mapper;
  private final ApplicationEventPublisher applicationEventPublisher;
  private final TarefaSearchRepository tarefaSearchRepository;

  public TarefaDTO salvar(TarefaDTO dto){
    if(dto.getDataInicio().isBefore(LocalDate.now())){
      throw new BadRequestException("Data de inicio é antes de atual");
    }
    Tarefa tarefa = repository.save(mapper.toEntity(dto));
    applicationEventPublisher.publishEvent(new TarefaEvent(tarefa.getId()));
    return mapper.toDTO(tarefa);
  }

  @Transactional(readOnly = true)
  public TarefaDTO buscarPorId(Long id){
    return mapper.toDTO(repository.findById(id).orElseThrow(() -> new BadRequestException(MensagemUtil.ANEXO_NAO_ENCOTRADO)));
  }

  @Transactional(readOnly = true)
  public List<TarefaDTO> buscarTodos(){
    return mapper.toListagemDTO(repository.findAll());
  }
  
  public void remover(Long id){
    repository.deleteById(id);
    tarefaSearchRepository.deleteById(id);
  }
  
  public TarefaDTO editar(TarefaDTO dto){
    this.buscarPorId(dto.getId());
    return this.salvar(dto);
  }

  public Page<TarefaDocument> pesquisar(TarefaFilter tarefaFilter, Pageable pageable){
    return tarefaSearchRepository.search(tarefaFilter.getFilter(),pageable);
  }
}
