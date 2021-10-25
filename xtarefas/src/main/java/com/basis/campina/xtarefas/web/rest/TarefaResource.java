package com.basis.campina.xtarefas.web.rest;

import com.basis.campina.xtarefas.domain.elasticsearch.TarefaDocument;
import com.basis.campina.xtarefas.service.TarefaService;
import com.basis.campina.xtarefas.service.dto.TarefaDTO;
import com.basis.campina.xtarefas.service.filter.TarefaFilter;
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
@RequestMapping("/api/tarefas")
@RequiredArgsConstructor
@Slf4j
public class TarefaResource {
  
  private final TarefaService service;
  
  @GetMapping
  public ResponseEntity<List<TarefaDTO>> buscarTodos(){
    log.debug("Requisição REST request para buscar todas as Tarefas.");
    return ResponseEntity.ok(service.buscarTodos());
  }
  
  @GetMapping("/{id}")
  public ResponseEntity<TarefaDTO> buscarPorId(@PathVariable Long id){
    log.debug("Requisição REST request para buscar Tarefa por id.");
    return ResponseEntity.ok(service.buscarPorId(id));
  }
  
  @PostMapping()
  public ResponseEntity<TarefaDTO> salvar(@RequestBody TarefaDTO tarefaDTO){
    log.debug("Requisição REST request para salvar o Tarefa: {}", tarefaDTO);
    return ResponseEntity.ok(service.salvar(tarefaDTO));
  }

  @PutMapping()
  public ResponseEntity<TarefaDTO> editar(@RequestBody TarefaDTO tarefaDTO){
    log.debug("Requisição REST request para editar o Tarefa: {}", tarefaDTO);
    return ResponseEntity.ok(service.editar(tarefaDTO));
  }

  @DeleteMapping ("/{id}")
  public ResponseEntity<Void> deletarPorId(@PathVariable Long id){
    log.debug("Requisição REST request para deletar Tarefa por id.");
    service.remover(id);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/pesquisar")
  public ResponseEntity<Page<TarefaDocument>> pesquisar(@RequestBody TarefaFilter filter, Pageable pageable){
    return ResponseEntity.ok(service.pesquisar(filter,pageable));
  }

}
