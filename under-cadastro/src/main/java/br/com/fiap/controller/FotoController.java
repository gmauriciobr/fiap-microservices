package br.com.fiap.controller;

import br.com.fiap.service.FotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URLConnection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "foto", description = "Serviço para administração de foto")
public class FotoController {

  private final FotoService fotoService;

  @GetMapping("/album/{albumId}/foto/{id}")
  @Operation(tags = {"foto"}, summary = "Download de Foto")
  public ResponseEntity<byte[]> download(@PathVariable Long albumId, @PathVariable Long id) {
    log.info("Download Foto - Album [{}] Foto [{}]", albumId, id);
    var foto = fotoService.buscaFoto(albumId, id);
    return ResponseEntity.ok()
      .contentType(MediaType.parseMediaType(URLConnection.guessContentTypeFromName(foto.getFilename())))
      .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + foto.getFilename() + "\"")
      .body(foto.getFotoBaixaQualidade());
  }

  @PostMapping(value = "/album/{albumId}/foto", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
  @Operation(tags = {"foto"}, summary = "Upload de Foto")
  public ResponseEntity<Void> upload(@RequestPart List<MultipartFile> file, @PathVariable Long albumId) {
    log.info("Upload Foto - Album [{}]", albumId);
    fotoService.uploadFoto(file, albumId);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/album/{albumId}/foto/{id}")
  @Operation(tags = {"foto"}, summary = "Upload de Foto")
  public ResponseEntity<Void> deleta(@PathVariable Long albumId, @PathVariable Long id) {
    log.info("Remove Foto - Album [{}] Foto [{}]", albumId, id);
    fotoService.deleta(albumId, id);
    return ResponseEntity.ok().build();
  }

}
