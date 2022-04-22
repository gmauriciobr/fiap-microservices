package br.com.fiap.controller;

import br.com.fiap.service.FotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URLConnection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "foto", description = "Serviço para consulta de foto")
public class FotoController {

  private final FotoService fotoService;

  @GetMapping("/foto/{token}")
  @Operation(tags = {"foto"}, summary = "Download de Foto")
  public ResponseEntity<byte[]> download(@PathVariable String token) {
    log.info("Download de foto de baixa qualidade token: {}", token);
    var foto = fotoService.buscaFotoPorToken(token);
    return ResponseEntity.ok()
      .contentType(MediaType.parseMediaType(URLConnection.guessContentTypeFromName(foto.getFilename())))
      .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + foto.getFilename() + "\"")
      .body(foto.getFotoBaixaQualidade());
  }

}
