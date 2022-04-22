package br.com.fiap.integration.controller;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.fiap.dto.FotografoDTO;
import br.com.fiap.dto.FotografoUpdateDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
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
public class FotografoControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Random
  FotografoDTO fotografoDTO;

  @Random
  FotografoUpdateDTO fotografoUpdateDTO;

  @Test
  @Order(1)
  void dadoIdExistente_quandoBuscaFotografoPorId_entaoSucesso() throws Exception {
    mockMvc.perform(get("/fotografo/{id}", 1L))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @Order(2)
  void dadoCadastroValido_quandoCadastra_entaoSucesso() throws Exception {
    mockMvc.perform(post("/fotografo").contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(fotografoDTO)))
      .andExpect(status().isCreated())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.nome", is(fotografoDTO.getNome())));
  }

  @Test
  @Order(3)
  void dadoCadastroValido_quandoAtualiza_entaoSucesso() throws Exception {
    mockMvc.perform(put("/fotografo/{id}", 1L).contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(fotografoUpdateDTO)))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.nome", is(fotografoUpdateDTO.getNome())));
  }

  @Test
  @Order(4)
  void dadoIdExistente_quandoDeleta_entaoSucesso() throws Exception {
    mockMvc.perform(delete("/fotografo/{id}", 1L))
      .andExpect(status().isOk());
  }
}