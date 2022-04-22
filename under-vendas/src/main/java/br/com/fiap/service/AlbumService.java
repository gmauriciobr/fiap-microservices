package br.com.fiap.service;

import br.com.fiap.model.Album;
import br.com.fiap.repository.AlbumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Service
@RestController
@RequestMapping("/album")
@RequiredArgsConstructor
public class AlbumService {

  private final AlbumRepository albumRepository;

  public Album buscaPorToken(String token) {
    return albumRepository.findByToken(token)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Album não encontrado"));
  }

}
