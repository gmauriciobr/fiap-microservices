package br.com.fiap.service;

import static br.com.fiap.config.RabbitMQConfig.GRAVA_IMAGEM_PROCESADA_DIRECT_QUEUE;

import br.com.fiap.dto.ProcessamentoFotoDTO;
import br.com.fiap.helper.ImagemHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FotoService {

  private final RabbitTemplate rabbitTemplate;

  public void execute(Message message) throws Exception {
      try {
        ProcessamentoFotoDTO dto = new ObjectMapper().readValue(message.getBody(), ProcessamentoFotoDTO.class);
        InputStream inputStream = new ByteArrayInputStream(dto.getFile());
        var file = ImagemHelper.compressImageAndWaterMark(
          ImagemHelper.getExtensaoFile(dto.getFilename()), ImageIO.read(inputStream), dto.getQualidade());
        dto.setFile(file);
        enviarFilaImagemProcessada(dto);
      } catch (Exception e) {
        log.error("Erro ao tratar foto: ", e);
        throw e;
      }
  }

  private void enviarFilaImagemProcessada(ProcessamentoFotoDTO dto) {
    rabbitTemplate.convertAndSend(GRAVA_IMAGEM_PROCESADA_DIRECT_QUEUE, dto);
  }

}
