package br.com.fiap.service;

import static br.com.fiap.config.rabbitmq.RabbitMQConfig.ENVIA_PEDIDO_DIRECT_QUEUE;

import br.com.fiap.dto.EnviaPedidoDTO;
import br.com.fiap.dto.EnviaPedidoItemDTO;
import br.com.fiap.dto.PedidoDTO;
import br.com.fiap.model.Pedido;
import br.com.fiap.model.PedidoItem;
import br.com.fiap.repository.FotoRepository;
import br.com.fiap.repository.PedidoRepository;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class PedidoService {

  private final PedidoRepository pedidoRepository;

  private final AlbumService albumService;

  private final FotoRepository fotoRepository;

  private final RabbitTemplate rabbitTemplate;

  public Pedido buscaPorId(Long id) {
    return pedidoRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
  }

  public Pedido cadastraPedido(PedidoDTO dto) {
    var album = albumService.buscaPorToken(dto.getTokenAlbum());
    var fotos = fotoRepository.findByAlbumTokenAndTokenIn(dto.getTokenAlbum(), dto.getTokenFotos());
    var pedido = PedidoDTO.parse(dto, album, fotos);
    pedidoRepository.save(pedido);
    enviaPedido(pedido);
    return pedido;
  }

  private void enviaPedido(Pedido pedido) {
    var dto = EnviaPedidoDTO.builder()
      .id(pedido.getId())
      .nome(pedido.getNome())
      .email(pedido.getEmail())
      .fotos(EnviaPedidoItemDTO.parse(pedido.getPedidoItems().stream().map(PedidoItem::getFoto).collect(Collectors.toList())))
      .build();
    rabbitTemplate.convertAndSend(ENVIA_PEDIDO_DIRECT_QUEUE, dto);
  }
}
