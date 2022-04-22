package br.com.fiap.controller;

import br.com.fiap.service.AlbumService;
import br.com.fiap.viewmodel.AlbumViewModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/album")
@RequiredArgsConstructor
@Tag(name = "album", description = "Serviço para consulta de album")
public class AlbumController {

  private final AlbumService albumService;

  @GetMapping("/{token}")
  @Operation(tags = {"album"}, summary = "Busca Album por Token")
  public ResponseEntity<AlbumViewModel> buscaAlbum(@PathVariable String token) {
    log.info("Buscando Album Token: {}", token);
    var album = albumService.buscaPorToken(token);
    return ResponseEntity.ok(new AlbumViewModel(album));
  }

}
