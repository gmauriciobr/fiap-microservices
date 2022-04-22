package br.com.fiap.listener;

import static br.com.fiap.config.rabbitmq.RabbitMQConfig.GRAVA_IMAGEM_PROCESADA_DIRECT_QUEUE;

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

  @RabbitListener(queues = GRAVA_IMAGEM_PROCESADA_DIRECT_QUEUE)
  public void consumer(Message message) throws Exception {
    log.info("Listener [{}]", GRAVA_IMAGEM_PROCESADA_DIRECT_QUEUE);
    this.fotoService.salvarFotoBaixaQualidade(message);
  }
}
