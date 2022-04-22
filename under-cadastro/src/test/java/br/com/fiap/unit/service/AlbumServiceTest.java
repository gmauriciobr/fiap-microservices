package br.com.fiap.unit.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.fiap.dto.AlbumDTO;
import br.com.fiap.dto.AlbumUpdateDTO;
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
  Long id;

  @Random
  AlbumDTO albumDTO;

  @Random
  AlbumUpdateDTO albumUpdateDTO;

  @Test
  public void dadoIdValido_quandoBuscaPorId_entaoSucesso() {
    when(albumRepository.findById(anyLong())).thenReturn(Optional.of(album));
    assertDoesNotThrow(() -> {
      var response = albumService.buscaPorId(id);
      assertEquals(album.getId(), response.getId());
      verify(albumRepository, times(1)).findById(anyLong());
    });
  }

  @Test
  public void dadoIdInvalido_quandoBuscaPorId_entaoException() {
    when(albumRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThrows(ResponseStatusException.class, () -> {
      albumService.buscaPorId(id);
      verify(albumRepository, times(1)).findById(anyLong());
    });
  }

  @Test
  public void dadoCadastroValido_quandoCadastro_entaoSucesso() {
    when(albumRepository.save(any(Album.class))).thenReturn(album);
    assertDoesNotThrow(() -> {
      var response = albumService.cadastro(albumDTO);
      assertEquals(album.getId(), response.getId());
      verify(albumRepository, times(1)).save(any(Album.class));
    });
  }

  @Test
  public void dadoCadastroValido_quandoAtualizaAlbum_entaoSucesso() {
    when(albumRepository.findById(anyLong())).thenReturn(Optional.of(album));
    when(albumRepository.save(any(Album.class))).thenReturn(album);
    assertDoesNotThrow(() -> {
      var response = albumService.atualizaAlbum(id, albumUpdateDTO);
      assertEquals(album.getId(), response.getId());
      verify(albumRepository, times(1)).findById(anyLong());
      verify(albumRepository, times(1)).save(any(Album.class));
    });
  }

  @Test
  public void dadoIdValido_quandoDeleta_entaoSucesso() {
    when(albumRepository.findById(anyLong())).thenReturn(Optional.of(album));
    assertDoesNotThrow(() -> {
      albumService.deleta(id);
      verify(albumRepository, times(1)).findById(anyLong());
      verify(albumRepository, times(1)).delete(any(Album.class));
    });
  }
}
