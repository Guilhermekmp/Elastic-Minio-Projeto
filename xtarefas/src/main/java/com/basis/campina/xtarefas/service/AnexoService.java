package com.basis.campina.xtarefas.service;

import com.basis.campina.xtarefas.domain.Anexo;
import com.basis.campina.xtarefas.domain.elasticsearch.AnexoDocument;
import com.basis.campina.xtarefas.repository.AnexoRepository;
import com.basis.campina.xtarefas.repository.elastic.AnexoSearchRepository;
import com.basis.campina.xtarefas.service.dto.AnexoDTO;
import com.basis.campina.xtarefas.service.event.AnexoEvent;
import com.basis.campina.xtarefas.service.exception.BadRequestException;
import com.basis.campina.xtarefas.service.feign.DocumentClient;
import com.basis.campina.xtarefas.service.filter.AnexoFilter;
import com.basis.campina.xtarefas.service.mapper.AnexoMapper;
import com.basis.campina.xtarefas.service.util.MensagemUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@Service
@Transactional
public class AnexoService {

  private final AnexoRepository repository;
  private final AnexoMapper mapper;
  private final DocumentClient documentClient;
  private final ApplicationEventPublisher applicationEventPublisher;
  private final AnexoSearchRepository anexoSearchRepository;

  public AnexoDTO salvar(AnexoDTO dto){
    if (Objects.isNull(dto.getFile())){
      throw new BadRequestException("Arquivo está vazio");
    }
    dto.setUuid(UUID.randomUUID().toString());
    documentClient.salvar(dto);
    Anexo anexo = repository.save(mapper.toEntity(dto));
    applicationEventPublisher.publishEvent(new AnexoEvent(anexo.getId()));
    return mapper.toDTO(anexo);
  }

  @Transactional(readOnly = true)
  public AnexoDTO buscarPorId(Long id){
    AnexoDTO anexoDTO = mapper.toDTO(repository.findById(id)
            .orElseThrow(() -> new BadRequestException(MensagemUtil.ANEXO_NAO_ENCOTRADO)));
    AnexoDTO arquivo = documentClient.buscarDocument(anexoDTO.getUuid());
    if(Objects.isNull(arquivo)){
      throw new BadRequestException("Arquivo não encontrado");
    }
    anexoDTO.setFile(arquivo.getFile());
    return anexoDTO;
  }

  @Transactional(readOnly = true)
  public List<AnexoDTO> buscarTodos(){
    return mapper.toListagemDTO(repository.findAll());
  }
  
  public void remover(Long id){
    AnexoDTO dto = this.buscarPorId(id);
    documentClient.deletarDocument(dto.getUuid());
    repository.deleteById(id);
    anexoSearchRepository.deleteById(id);
  }
  
  public AnexoDTO editar(AnexoDTO dto){
    this.buscarPorId(dto.getId());
    return this.salvar(dto);
  }

  public Page<AnexoDocument> pesquisar(AnexoFilter anexoFilter, Pageable pageable){
    return anexoSearchRepository.search(anexoFilter.getFilter(),pageable);
  }

}
