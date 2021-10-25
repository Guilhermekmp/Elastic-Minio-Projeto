package com.basis.campina.xtarefas.service.feign;

import com.basis.campina.xtarefas.service.dto.AnexoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="xdocumentos", url = "${application.feign.url-documents}")
public interface DocumentClient {

  @GetMapping("api/documents/{uuid}")
  AnexoDTO buscarDocument(@PathVariable("uuid") String uuid);

  @PostMapping("api/documents")
  String salvar(AnexoDTO anexoDTO);

  @DeleteMapping("api/documents/{uuid}")
  void deletarDocument(@PathVariable("uuid") String uuid);
}
