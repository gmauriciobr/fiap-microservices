package br.com.fiap.unit.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.fiap.dto.FotografoDTO;
import br.com.fiap.dto.FotografoUpdateDTO;
import br.com.fiap.model.Fotografo;
import br.com.fiap.repository.FotografoRepository;
import br.com.fiap.service.FotografoService;
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
public class FotografoServiceTest {

  @InjectMocks
  FotografoService fotografoService;

  @Mock
  FotografoRepository fotografoRepository;

  @Random
  Long id;

  @Random
  Fotografo fotografo;

  @Random
  FotografoDTO fotografoDTO;

  @Random
  FotografoUpdateDTO fotografoUpdateDTO;

  @Test
  public void dadoIdValido_quandoBuscaPorId_entaoSucesso() {
    when(fotografoRepository.findById(anyLong())).thenReturn(Optional.of(fotografo));
    assertDoesNotThrow(() -> {
      var response = fotografoService.buscaPorId(id);
      assertEquals(fotografo.getId(), response.getId());
      verify(fotografoRepository, times(1)).findById(anyLong());
    });
  }

  @Test
  public void dadoIdInvalido_quandoBuscaPorId_entaoException() {
    when(fotografoRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThrows(ResponseStatusException.class, () -> {
      fotografoService.buscaPorId(id);
      verify(fotografoRepository, times(1)).findById(anyLong());
    });
  }

  @Test
  public void dadoFotografoValido_quandoCadastra_entaoSucesso() {
    when(fotografoRepository.save(any(Fotografo.class))).thenReturn(fotografo);
    assertDoesNotThrow(() -> {
      fotografoService.cadastra(fotografoDTO);
      verify(fotografoRepository, times(1)).save(any(Fotografo.class));
    });
  }

  @Test
  public void dadoCadastroValido_quandoAtualiza_entaoSucesso() {
    when(fotografoRepository.findById(anyLong())).thenReturn(Optional.of(fotografo));
    when(fotografoRepository.save(any(Fotografo.class))).thenReturn(fotografo);
    assertDoesNotThrow(() -> {
      fotografoService.atualiza(id, fotografoUpdateDTO);
      verify(fotografoRepository, times(1)).findById(anyLong());
      verify(fotografoRepository, times(1)).save(any(Fotografo.class));
    });
  }

  @Test
  public void dadoIdValido_quandoDeleta_entaoSucesso() {
    when(fotografoRepository.findById(anyLong())).thenReturn(Optional.of(fotografo));
    assertDoesNotThrow(() -> {
      fotografoService.deleta(id);
      verify(fotografoRepository, times(1)).findById(anyLong());
      verify(fotografoRepository, times(1)).delete(any(Fotografo.class));
    });
  }
}
