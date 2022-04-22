package br.com.fiap.unit.controller;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.fiap.controller.PedidoController;
import br.com.fiap.dto.PedidoDTO;
import br.com.fiap.model.Foto;
import br.com.fiap.model.Pedido;
import br.com.fiap.model.PedidoItem;
import br.com.fiap.repository.AlbumRepository;
import br.com.fiap.repository.FotoRepository;
import br.com.fiap.service.PedidoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

@ActiveProfiles("test")
@TestInstance(PER_CLASS)
@WebMvcTest(PedidoController.class)
@ExtendWith({MockitoExtension.class, RandomBeansExtension.class})
public class PedidoControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  PedidoService pedidoService;

  @MockBean
  AlbumRepository albumRepository;

  @MockBean
  FotoRepository fotoRepository;

  @Random
  PedidoDTO pedidoDTO;

  @Random
  Pedido pedido;

  @Random
  Long id;

  @Random(type = PedidoItem.class, size = 3)
  List<PedidoItem> pedidoItems;

  @Random(type = Foto.class, size = 3)
  List<Foto> fotos;

  @BeforeAll
  void setUp() {
    for (int i = 0; i < pedidoItems.size(); i++) {
      pedidoItems.get(i).setFoto(fotos.get(i));
    }
    pedido.setPedidoItems(pedidoItems);
  }

  @BeforeEach
  void beforeEach() {
    when(albumRepository.existsByToken(anyString())).thenReturn(true);
    when(fotoRepository.existsByTokenIn(anySet())).thenReturn(true);
  }

  @Test
  void dadoPedidoValido_quandoCadastraPedido_entaoSucesso() throws Exception {
    when(pedidoService.cadastraPedido(any(PedidoDTO.class))).thenReturn(pedido);
    mockMvc.perform(post("/pedido").contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(pedidoDTO)))
      .andExpect(status().isCreated())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.nome", is(pedido.getNome())));
  }

  @Test
  void dadoPedidoInvalido_quandoCadastraPedido_entaoSucesso() throws Exception {
    var pedidoDTO = PedidoDTO.builder().build();
    mockMvc.perform(post("/pedido").contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(pedidoDTO)))
      .andExpect(status().isBadRequest())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void dadoIdValido_quandoBuscaAlbum_entaoSucesso() throws Exception {
    when(pedidoService.buscaPorId(anyLong())).thenReturn(pedido);
    mockMvc.perform(get("/pedido/{id}", id))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andDo(print());
  }

  @Test
  void dadoIdInvalido_quandoBuscaAlbum_entaoException() throws Exception {
    when(pedidoService.buscaPorId(anyLong())).thenThrow(ResponseStatusException.class);
    mockMvc.perform(get("/pedido/{token}", id))
      .andExpect(status().isBadRequest())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

}
