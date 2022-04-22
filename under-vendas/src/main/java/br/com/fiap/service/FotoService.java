package br.com.fiap.service;

import br.com.fiap.model.Foto;
import br.com.fiap.repository.FotoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class FotoService {

  private final FotoRepository fotoRepository;

  public Foto buscaFotoPorToken(String token) {
    return fotoRepository.findByToken(token)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Foto não encontrada"));
  }

}
