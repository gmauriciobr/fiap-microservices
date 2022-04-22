package br.com.fiap.unit.controller;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.TestInstance.Lifecycle.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.fiap.controller.FotografoController;
import br.com.fiap.dto.FotografoDTO;
import br.com.fiap.dto.FotografoUpdateDTO;
import br.com.fiap.model.Fotografo;
import br.com.fiap.service.FotografoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
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

@ActiveProfiles("test")
@TestInstance(PER_CLASS)
@WebMvcTest(FotografoController.class)
@ExtendWith({MockitoExtension.class, RandomBeansExtension.class})
public class FotografoControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  FotografoService fotografoService;

  @Random
  FotografoDTO fotografoDTO;

  @Random
  FotografoUpdateDTO fotografoUpdateDTO;

  @Random
  Fotografo fotografo;

  @Test
  void dadoIdExistente_quandoBuscaFotografoPorId_entaoSucesso() throws Exception {
    when(fotografoService.buscaPorId(anyLong())).thenReturn(fotografo);
    mockMvc.perform(get("/fotografo/{id}", 1L))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void dadoIdFotografoInvalido_quandoBuscaFotografoPorId_entaoSucesso() throws Exception {
    when(fotografoService.buscaPorId(anyLong())).thenReturn(fotografo);
    mockMvc.perform(get("/fotografo/{id}", 1L))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void dadoCadastroValido_quandoCadastra_entaoSucesso() throws Exception {
    when(fotografoService.cadastra(any(FotografoDTO.class))).thenReturn(fotografo);
    mockMvc.perform(post("/fotografo").contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(fotografoDTO)))
      .andExpect(status().isCreated())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.nome", is(fotografo.getNome())));
  }

  @Test
  void dadoCadastroValido_quandoAtualiza_entaoSucesso() throws Exception {
    when(fotografoService.atualiza(anyLong(), any(FotografoUpdateDTO.class))).thenReturn(fotografo);
    mockMvc.perform(put("/fotografo/{id}", 1L).contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(fotografoUpdateDTO)))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.nome", is(fotografo.getNome())));
  }

  @Test
  void dadoIdExistente_quandoDeleta_entaoSucesso() throws Exception {
    mockMvc.perform(delete("/fotografo/{id}", 1L))
      .andExpect(status().isOk());
  }
}