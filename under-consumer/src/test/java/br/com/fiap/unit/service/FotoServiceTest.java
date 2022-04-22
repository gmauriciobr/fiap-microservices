package br.com.fiap.unit.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import br.com.fiap.dto.ProcessamentoFotoDTO;
import br.com.fiap.service.FotoService;
import br.com.fiap.util.FotoUtil;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith({MockitoExtension.class, RandomBeansExtension.class})
public class FotoServiceTest {

  @InjectMocks
  private FotoService fotoService;

  @Mock
  RabbitTemplate rabbitTemplate;

  ProcessamentoFotoDTO processamentoFotoDTO;

  @BeforeAll
  void setUp() throws IOException {
    processamentoFotoDTO = ProcessamentoFotoDTO.builder()
      .id(1L)
      .filename(FotoUtil.FOTO_TESTE)
      .qualidade(10)
      .file(FotoUtil.getInstance().carregaIByteResource())
      .build();
  }

  @Test
  void dadoFotoValida_quandoRecebeMessagem_entaoSucesso() {
    var message = new Jackson2JsonMessageConverter().toMessage(processamentoFotoDTO, new MessageProperties());
    Assertions.assertDoesNotThrow(() -> {
      fotoService.execute(message);
      verify(rabbitTemplate, times(1)).convertAndSend(anyString(), any(ProcessamentoFotoDTO.class));
    });
  }

}
