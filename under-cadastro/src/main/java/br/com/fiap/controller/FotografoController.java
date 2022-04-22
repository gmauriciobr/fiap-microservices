package br.com.fiap.controller;

import br.com.fiap.dto.FotografoDTO;
import br.com.fiap.dto.FotografoUpdateDTO;
import br.com.fiap.service.FotografoService;
import br.com.fiap.viewmodel.FotografoViewModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RestController
@RequestMapping("/fotografo")
@RequiredArgsConstructor
@Tag(name = "fotografo", description = "Serviço para administração do fotografo")
public class FotografoController {

  private final FotografoService fotografoService;

  @GetMapping("/{id}")
  @Operation(tags = {"fotografo"}, summary = "Busca Fotografo")
  public ResponseEntity<FotografoViewModel> buscaFotografoPorId(@PathVariable Long id) {
    log.info("Busca Fotografo [{}]", id);
    return ResponseEntity.ok(new FotografoViewModel(fotografoService.buscaPorId(id)));
  }

  @PostMapping
  @Operation(tags = {"fotografo"}, summary = "Cadastra Fotografo")
  public ResponseEntity<FotografoViewModel> cadastra(@RequestBody @Valid FotografoDTO dto, UriComponentsBuilder uriBuilder) {
    log.info("Cadastra Fotografo [{}]", dto);
    var fotografo = fotografoService.cadastra(dto);
    var uri = uriBuilder.path("/fotografo/{id}").buildAndExpand(fotografo.getId()).toUri();
    return ResponseEntity.created(uri).body(new FotografoViewModel(fotografo));
  }

  @PutMapping("/{id}")
  @Operation(tags = {"fotografo"}, summary = "Atualiza Fotografo")
  public ResponseEntity<FotografoViewModel> atualiza(@PathVariable Long id, @RequestBody @Valid FotografoUpdateDTO dto) {
    log.info("Atualiza Fotografo [{}]", id);
    return ResponseEntity.ok(new FotografoViewModel(fotografoService.atualiza(id, dto)));
  }

  @DeleteMapping ("/{id}")
  @Operation(tags = {"fotografo"}, summary = "Deleta Fotografo")
  public ResponseEntity<Void> deleta(@PathVariable Long id) {
    log.info("Deleta Fotografo [{}]", id);
    fotografoService.deleta(id);
    return ResponseEntity.ok().build();
  }

}
