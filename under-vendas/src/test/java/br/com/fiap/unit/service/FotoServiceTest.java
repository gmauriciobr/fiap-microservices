package br.com.fiap.unit.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.fiap.model.Foto;
import br.com.fiap.repository.FotoRepository;
import br.com.fiap.service.FotoService;
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
public class FotoServiceTest {

  @InjectMocks
  FotoService fotoService;

  @Mock
  FotoRepository fotoRepository;

  @Random
  Foto foto;

  @Random
  String token;

  @Test
  void dadoTokenValido_quandoBuscaFoto_entaoSucesso() {
    when(fotoRepository.findByToken(anyString())).thenReturn(Optional.of(foto));
    assertDoesNotThrow(() -> {
      fotoService.buscaFotoPorToken(token);
      verify(fotoRepository, times(1)).findByToken(anyString());
    });
  }

  @Test
  void dadoTokenInvalido_quandoBuscaFoto_entaoSucesso() {
    when(fotoRepository.findByToken(anyString())).thenReturn(Optional.empty());
    assertThrows(ResponseStatusException.class, () -> {
      fotoService.buscaFotoPorToken(token);
      verify(fotoRepository, times(1)).findByToken(anyString());
    });
  }

}
