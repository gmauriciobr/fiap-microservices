package br.com.fiap.unit.listener;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import br.com.fiap.dto.EnviaPedidoDTO;
import br.com.fiap.listener.PedidoListener;
import br.com.fiap.service.PedidoService;
import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@TestInstance(PER_CLASS)
@ExtendWith({MockitoExtension.class, RandomBeansExtension.class})
public class PedidoListenerTest {

  @InjectMocks
  PedidoListener pedidoListener;

  @Mock
  PedidoService pedidoService;

  @Random
  EnviaPedidoDTO enviaPedidoDTO;

  @Test
  void dadoMessageValida_quandoEnviarPedido_entaoSucesso() throws Exception {
    var jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
    var message = jackson2JsonMessageConverter.toMessage(enviaPedidoDTO, new MessageProperties());
    pedidoListener.consumer(message);
  }

}
