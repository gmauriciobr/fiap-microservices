package br.com.fiap.unit.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.fiap.dto.EnviaPedidoDTO;
import br.com.fiap.service.PedidoService;
import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith({MockitoExtension.class, RandomBeansExtension.class})
public class PedidoServiceTest {

  @InjectMocks
  private PedidoService pedidoService;

  @Random
  EnviaPedidoDTO enviaPedidoDTO;

  @Test
  void dadoDadosValidos_quandoExecuta_entaoSucesso() {
    var message = new Jackson2JsonMessageConverter().toMessage(enviaPedidoDTO, new MessageProperties());
    assertDoesNotThrow(() -> {
      pedidoService.execute(message);
    });
  }

  @Test
  void dadoDadosInvalidos_quandoExecuta_entaoSucesso() {
    var message = new Jackson2JsonMessageConverter().toMessage("Teste", new MessageProperties());
    assertThrows(IOException.class, () -> {
      pedidoService.execute(message);
    });
  }

}
