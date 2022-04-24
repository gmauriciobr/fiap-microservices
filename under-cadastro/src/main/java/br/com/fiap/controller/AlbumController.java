package br.com.fiap.controller;

import br.com.fiap.dto.AlbumDTO;
import br.com.fiap.dto.AlbumUpdateDTO;
import br.com.fiap.service.AlbumService;
import br.com.fiap.viewmodel.AlbumViewModel;
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
@RequestMapping("/album")
@RequiredArgsConstructor
@Tag(name = "album", description = "Serviço para administração de album")
public class AlbumController {

  private final AlbumService albumService;

  @GetMapping("/{id}")
  @Operation(tags = {"album"}, summary = "Busca Album")
  public ResponseEntity<AlbumViewModel> buscaAlbum(@PathVariable Long id) {
    log.info("Busca Album: [{}]", id);
    var album = albumService.buscaPorId(id);
    return ResponseEntity.ok(new AlbumViewModel(album));
  }

  @PostMapping
  @Operation(tags = {"album"}, summary = "Cadastra Album")
  public ResponseEntity<AlbumViewModel> cadastro(@RequestBody @Valid AlbumDTO dto, UriComponentsBuilder uriBuilder) {
    log.info("Cadastra Album Fotografo [{}]", dto.getFotografoId());
    var album = albumService.cadastro(dto);
    var uri = uriBuilder.path("/album/{id}").buildAndExpand(album.getId()).toUri();
    return ResponseEntity.created(uri).body(new AlbumViewModel(album));
  }

  @PutMapping("/{id}")
  @Operation(tags = {"album"}, summary = "Atualiza Album")
  public ResponseEntity<AlbumViewModel> atualizaAlbum(@PathVariable Long id, @RequestBody @Valid AlbumUpdateDTO dto) {
    log.info("Atualiza Album Id [{}]", id);
    return ResponseEntity.ok(new AlbumViewModel(albumService.atualizaAlbum(id, dto)));
  }

  @DeleteMapping("/{id}")
  @Operation(tags = {"album"}, summary = "Deleta Album")
  public ResponseEntity<Void> deleta(@PathVariable Long id) {
    log.info("Deleta Album Id [{}]", id);
    albumService.deleta(id);
    return ResponseEntity.ok().build();
  }


}
