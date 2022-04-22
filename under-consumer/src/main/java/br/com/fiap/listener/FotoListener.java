package br.com.fiap.listener;

import static br.com.fiap.config.RabbitMQConfig.PROCESSA_IMAGEM_DIRECT_QUEUE;

import br.com.fiap.service.FotoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FotoListener {

  private final FotoService fotoService;

  @RabbitListener(queues = PROCESSA_IMAGEM_DIRECT_QUEUE)
  public void consumer(Message message) throws Exception {
    log.info("Listener [{}]", PROCESSA_IMAGEM_DIRECT_QUEUE);
    this.fotoService.execute(message);
  }

}
