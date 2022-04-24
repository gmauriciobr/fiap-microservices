package br.com.fiap.integration.controller;

import static br.com.fiap.util.Constantes.TOKEN_FOTO;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
public class FotoControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  @Order(1)
  void dadoTokenValido_quandoDownload_EntaoSucesso() throws Exception {
    mockMvc.perform(get("/foto/{token}", TOKEN_FOTO)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk());
  }

}
