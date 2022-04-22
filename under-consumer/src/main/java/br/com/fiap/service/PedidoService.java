package br.com.fiap.service;

import br.com.fiap.dto.EnviaPedidoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PedidoService {

  public void execute(Message message) throws IOException {
    try {
      EnviaPedidoDTO dto = new ObjectMapper().readValue(message.getBody(), EnviaPedidoDTO.class);
      log.info("Enviando por email pedido [{}] total de [{}] fotos", dto.getId(), dto.getFotos().size());
    } catch (IOException e) {
      log.error("Erro ao enviar peddido", e);
      throw e;
    }
  }

}
