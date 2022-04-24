package br.com.fiap.integration.controller;

import static br.com.fiap.util.Constantes.TOKEN_ALBUM;
import static br.com.fiap.util.Constantes.TOKEN_FOTO;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.fiap.dto.PedidoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import java.util.Set;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(RandomBeansExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(PER_CLASS)
public class PedidoControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  @Order(1)
  void dadoPedidoValido_quandoCadastraPedido_EntaoSucesso() throws Exception {
    mockMvc.perform(post("/pedido")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(createPedidoDTO())))
      .andExpect(status().isCreated());
  }

  @Test
  @Order(1)
  void dadoIdValido_quandoBuscaPedidoPorId_EntaoSucesso() throws Exception {
    mockMvc.perform(get("/pedido/{id}", 1L)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.nome", is(createPedidoDTO().getNome())));
  }

  private PedidoDTO createPedidoDTO() {
    return PedidoDTO.builder()
      .nome("Guilherme")
      .email("guilherme@email.com")
      .numeroCartao("1234123412341234")
      .tokenAlbum(TOKEN_ALBUM)
      .tokenFotos(Set.of(TOKEN_FOTO))
      .build();
  }

}
