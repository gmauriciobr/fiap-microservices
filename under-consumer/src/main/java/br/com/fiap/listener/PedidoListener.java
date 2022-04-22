package br.com.fiap.listener;

import static br.com.fiap.config.RabbitMQConfig.ENVIA_PEDIDO_DIRECT_QUEUE;

import br.com.fiap.service.PedidoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PedidoListener {

  private final PedidoService pedidoService;

  @RabbitListener(queues = ENVIA_PEDIDO_DIRECT_QUEUE)
  public void consumer(Message message) throws Exception {
    log.info("Listener [{}]", ENVIA_PEDIDO_DIRECT_QUEUE);
    this.pedidoService.execute(message);
  }
}
