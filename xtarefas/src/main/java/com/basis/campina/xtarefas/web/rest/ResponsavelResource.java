package com.basis.campina.xtarefas.web.rest;

import com.basis.campina.xtarefas.domain.elasticsearch.ResponsavelDocument;
import com.basis.campina.xtarefas.service.ResponsavelService;
import com.basis.campina.xtarefas.service.dto.ResponsavelDTO;
import com.basis.campina.xtarefas.service.filter.ResponsavelFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/responsaveis")
@RequiredArgsConstructor
@Slf4j
public class ResponsavelResource {

  private final ResponsavelService service;

  @GetMapping
  public ResponseEntity<List<ResponsavelDTO>> buscarTodos(){
    log.debug("Requisição REST request para buscar todos os Responsaveis");
    return ResponseEntity.ok(service.buscarTodos());
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponsavelDTO> buscarPorId(@PathVariable Long id){
    log.debug("Requisição REST request para buscar Responsavel por id");
    return ResponseEntity.ok(service.buscarPorId(id));
  }

  @PostMapping()
  public ResponseEntity<ResponsavelDTO> salvar(@RequestBody ResponsavelDTO responsavelDTO){
    log.debug("Requisição REST request para salvar Responsavel:", responsavelDTO);
    return ResponseEntity.ok(service.salvar(responsavelDTO));
  }

  @PutMapping()
  public ResponseEntity<ResponsavelDTO> editar(@RequestBody ResponsavelDTO responsavelDTO){
    log.debug("Requisição REST request para editar Responsavel:", responsavelDTO);
    return ResponseEntity.ok(service.editar(responsavelDTO));
  }

  @DeleteMapping ("/{id}")
  public ResponseEntity<Void> deletarPorId(@PathVariable Long id){
    log.debug("Requisição REST request para deletar Responsavel por id");
    service.remover(id);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/pesquisar")
  public ResponseEntity<Page<ResponsavelDocument>> pesquisar(@RequestBody ResponsavelFilter filter, Pageable pageable){
    return ResponseEntity.ok(service.pesquisar(filter,pageable));
  }

}
