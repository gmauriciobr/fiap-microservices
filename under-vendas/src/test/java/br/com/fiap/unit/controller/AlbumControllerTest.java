package br.com.fiap.unit.controller;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.fiap.controller.AlbumController;
import br.com.fiap.model.Album;
import br.com.fiap.model.Foto;
import br.com.fiap.repository.AlbumRepository;
import br.com.fiap.repository.FotografoRepository;
import br.com.fiap.service.AlbumService;
import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import java.util.List;
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
@WebMvcTest(AlbumController.class)
@ExtendWith({MockitoExtension.class, RandomBeansExtension.class})
public class AlbumControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  AlbumService albumService;

  @MockBean
  AlbumRepository albumRepository;

  @MockBean
  FotografoRepository fotografoRepository;

  @Random
  Album album;

  @Random(type = Foto.class, size = 3)
  List<Foto> fotos;

  @Random
  String token;

  @BeforeEach
  void beforeEach() {
    album.setFotos(fotos);
  }

  @Test
  void dadoIdValido_quandoBuscaAlbum_entaoSucesso() throws Exception {
    when(albumService.buscaPorToken(anyString())).thenReturn(album);
    mockMvc.perform(get("/album/{token}", token))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void dadoIdInvalido_quandoBuscaAlbum_entaoException() throws Exception {
    when(albumService.buscaPorToken(anyString())).thenThrow(ResponseStatusException.class);
    mockMvc.perform(get("/album/{token}", token))
      .andExpect(status().isBadRequest())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

}
