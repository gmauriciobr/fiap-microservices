package br.com.fiap.unit.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.fiap.model.Album;
import br.com.fiap.repository.AlbumRepository;
import br.com.fiap.service.AlbumService;
import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith({MockitoExtension.class, RandomBeansExtension.class})
public class AlbumServiceTest {

  @InjectMocks
  AlbumService albumService;

  @Mock
  AlbumRepository albumRepository;

  @Random
  Album album;

  @Random
  String token;

  @Test
  void dadoTokenValido_quandoBuscaAlgum_EntaoSucesso() {
    when(albumRepository.findByToken(anyString())).thenReturn(Optional.of(album));
    assertDoesNotThrow(() -> {
      albumService.buscaPorToken(token);
      verify(albumRepository, times(1)).findByToken(anyString());
    });
  }

  @Test
  void dadoTokenInvalida_quandoBuscaAlgum_EntaoSucesso() {
    when(albumRepository.findByToken(anyString())).thenReturn(Optional.empty());
    assertThrows(ResponseStatusException.class, () -> {
      albumService.buscaPorToken(token);
      verify(albumRepository, times(1)).findByToken(anyString());
    });
  }

}
