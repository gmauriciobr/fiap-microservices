package br.com.fiap.unit.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.fiap.dto.EnviaPedidoDTO;
import br.com.fiap.dto.PedidoDTO;
import br.com.fiap.model.Album;
import br.com.fiap.model.Foto;
import br.com.fiap.model.Pedido;
import br.com.fiap.repository.FotoRepository;
import br.com.fiap.repository.PedidoRepository;
import br.com.fiap.service.AlbumService;
import br.com.fiap.service.PedidoService;
import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith({MockitoExtension.class, RandomBeansExtension.class})
public class PedidoServiceTest {

  @InjectMocks
  PedidoService pedidoService;

  @Mock
  PedidoRepository pedidoRepository;

  @Mock
  AlbumService albumService;

  @Mock
  FotoRepository fotoRepository;

  @Mock
  RabbitTemplate rabbitTemplate;

  @Random
  Pedido pedido;

  @Random
  Long pedidoId;

  @Random
  PedidoDTO pedidoDTO;

  @Random
  Album album;

  @Random(type = Foto.class, size = 3)
  List<Foto> fotos;

  @Test
  void dadoPedidoValido_quandoBuscaPorId_entaoSucesso() {
    when(pedidoRepository.findById(anyLong())).thenReturn(Optional.of(pedido));
    assertDoesNotThrow(() -> {
      pedidoService.buscaPorId(pedidoId);
      verify(pedidoRepository, times(1)).findById(anyLong());
    });
  }

  @Test
  void dadoPedidoInvalido_quandoBuscaPorId_entaoSucesso() {
    when(pedidoRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThrows(ResponseStatusException.class, () -> {
      pedidoService.buscaPorId(pedidoId);
      verify(pedidoRepository, times(1)).findById(anyLong());
    });
  }

  @Test
  void dadoPedidoValido_quandoCadastraPedido_entaoSucesso() {
    when(albumService.buscaPorToken(anyString())).thenReturn(album);
    when(fotoRepository.findByAlbumTokenAndTokenIn(anyString(), anySet())).thenReturn(fotos);
    assertDoesNotThrow(() -> {
      pedidoService.cadastraPedido(pedidoDTO);
      verify(albumService, times(1)).buscaPorToken(anyString());
      verify(fotoRepository, times(1)).findByAlbumTokenAndTokenIn(anyString(), anySet());
      verify(pedidoRepository, times(1)).save(any(Pedido.class));
      verify(rabbitTemplate, times(1)).convertAndSend(anyString(), any(EnviaPedidoDTO.class));
    });

  }



}
