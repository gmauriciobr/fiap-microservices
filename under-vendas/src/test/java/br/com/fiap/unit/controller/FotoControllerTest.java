package br.com.fiap.unit.controller;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.fiap.controller.FotoController;
import br.com.fiap.model.Foto;
import br.com.fiap.service.FotoService;
import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import org.junit.jupiter.api.BeforeAll;
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
@WebMvcTest(FotoController.class)
@ExtendWith({MockitoExtension.class, RandomBeansExtension.class})
public class FotoControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  FotoService fotoService;

  @Random
  Foto foto;

  @Random
  String token;

  @BeforeAll
  void setUp() {
    foto.setFilename("imagem.jpg");
  }

  @Test
  public void dadoTokenValido_quandoDownload_EntaoSucesso() throws Exception {
    when(fotoService.buscaFotoPorToken(anyString())).thenReturn(foto);
    mockMvc.perform(get("/foto/{token}", token)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk());
  }

  @Test
  public void dadoTolenInvalido_quandoDownload_EntaoSucesso() throws Exception {
    when(fotoService.buscaFotoPorToken(anyString())).thenThrow(ResponseStatusException.class);
    mockMvc.perform(get("/foto/{token}", token)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest());
  }

}