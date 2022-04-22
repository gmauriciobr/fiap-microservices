package br.com.fiap.integration.controller;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
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
import org.springframework.mock.web.MockMultipartFile;
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
  void dadoArquivoValido_quandoUpload_EntaoSucesso() throws Exception {
    var file =
      new MockMultipartFile("file", "foto.jpg", MediaType.IMAGE_JPEG_VALUE, "foto".getBytes());
    mockMvc.perform(multipart("/album/{albumId}/foto", 1L)
        .file(file))
      .andExpect(status().isOk());
  }

  @Test
  @Order(2)
  void dadoIdValido_quandoDownload_EntaoSucesso() throws Exception {
    mockMvc.perform(get("/album/{albumId}/foto/{fotoId}", 1L, 1L)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk());
  }

  @Test
  @Order(3)
  void dadoIdValido_quandoDeleta_entaoSucesso() throws Exception {
    mockMvc.perform(delete("/album/{albumId}/foto/{fotoId}", 1L, 1L)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk());
  }

}