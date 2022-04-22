package br.com.fiap.unit.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.fiap.dto.ProcessamentoFotoDTO;
import br.com.fiap.model.Album;
import br.com.fiap.model.Foto;
import br.com.fiap.repository.FotoRepository;
import br.com.fiap.service.AlbumService;
import br.com.fiap.service.FotoService;
import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

@ActiveProfiles("test")
@ExtendWith({MockitoExtension.class, RandomBeansExtension.class})
public class FotoServiceTest {

  private static MockMultipartFile FOTO =
    new MockMultipartFile("data", "foto.jpg", "image/jpeg", "some xml".getBytes());

  @InjectMocks
  FotoService fotoService;

  @Mock
  FotoRepository fotoRepository;

  @Mock
  AlbumService albumService;

  @Mock
  RabbitTemplate rabbitTemplate;

  @Random
  Foto foto;

  @Random
  Long albumId;

  @Random
  Long fotoId;

  @Random
  Album album;

  @Random
  ProcessamentoFotoDTO processamentoFotoDTO;

  @Test
  public void dadoIdsValidos_quandoBuscaFoto_entaoSucesso() {
    when(fotoRepository.findByAlbumIdAndId(anyLong(), anyLong())).thenReturn(Optional.of(foto));
    assertDoesNotThrow(() -> {
      var response = fotoService.buscaFoto(albumId, fotoId);
      assertEquals(foto.getId(), response.getId());
      verify(fotoRepository, times(1)).findByAlbumIdAndId(anyLong(), anyLong());
    });
  }

  @Test
  public void dadoIdInvalidos_quandoBuscaFoto_entaoSucesso() {
    when(fotoRepository.findByAlbumIdAndId(anyLong(), anyLong())).thenReturn(Optional.empty());
    assertThrows(ResponseStatusException.class, () -> {
      fotoService.buscaFoto(albumId, fotoId);
      verify(fotoRepository, times(1)).findByAlbumIdAndId(anyLong(), anyLong());
    });
  }

  @Test
  public void dadoIdEAnexoValido_quandoUploadFoto_entaoSucesso() {
    when(albumService.buscaPorId(anyLong())).thenReturn(album);
    assertDoesNotThrow(() -> {
      fotoService.uploadFoto(Arrays.asList(FOTO), albumId);
      verify(albumService, times(1)).buscaPorId(anyLong());
      verify(rabbitTemplate, times(1)).convertAndSend(anyString(), any(ProcessamentoFotoDTO.class));
    });
  }

  @Test
  public void dadoMensagemValida_quandoSalvarFoto_entaoSucesso() {
    var jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
    var message = jackson2JsonMessageConverter.toMessage(processamentoFotoDTO, new MessageProperties());
    when(fotoRepository.findById(anyLong())).thenReturn(Optional.of(foto));
    assertDoesNotThrow(() -> {
      fotoService.salvarFotoBaixaQualidade(message);
      verify(fotoRepository, times(1)).findById(anyLong());
      verify(fotoRepository, times(1)).save(any(Foto.class));
    });
  }

  @Test
  public void dadoMensagemInvalida_quandoSalvarFoto_entaoSucesso() throws Exception {
    var message = new Message(SerializationUtils.serialize(processamentoFotoDTO));
    assertThrows(Exception.class, () -> {
      fotoService.salvarFotoBaixaQualidade(message);
    });
  }

  @Test
  public void dadoIdsValidos_quandoDeleta_entaoSucesso() {
    when(fotoRepository.findByAlbumIdAndId(anyLong(), anyLong())).thenReturn(Optional.of(foto));
    assertDoesNotThrow(() -> {
      fotoService.deleta(albumId, fotoId);
      verify(fotoRepository, times(1)).findByAlbumIdAndId(anyLong(), anyLong());
      verify(fotoRepository, times(1)).delete(any(Foto.class));
    });
  }

}
