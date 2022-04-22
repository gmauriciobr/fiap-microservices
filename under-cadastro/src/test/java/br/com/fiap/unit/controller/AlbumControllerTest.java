package br.com.fiap.unit.controller;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
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

import br.com.fiap.controller.AlbumController;
import br.com.fiap.dto.AlbumDTO;
import br.com.fiap.dto.AlbumUpdateDTO;
import br.com.fiap.dto.FotografoDTO;
import br.com.fiap.dto.FotografoUpdateDTO;
import br.com.fiap.model.Album;
import br.com.fiap.model.Foto;
import br.com.fiap.repository.AlbumRepository;
import br.com.fiap.repository.FotografoRepository;
import br.com.fiap.service.AlbumService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import java.util.List;
import java.util.Optional;
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
  FotografoDTO fotografoDTO;

  @Random
  FotografoUpdateDTO fotografoUpdateDTO;

  @BeforeEach
  void beforeEach() {
    album.setFotos(fotos);
  }

  @Test
  void dadoIdValido_quandoBuscaAlbum_entaoSucesso() throws Exception {
    when(albumService.buscaPorId(anyLong())).thenReturn(album);
    mockMvc.perform(get("/album/{id}", 1L))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void dadoIdInvalido_quandoBuscaAlbum_entaoException() throws Exception {
    when(albumService.buscaPorId(anyLong())).thenThrow(ResponseStatusException.class);
    mockMvc.perform(get("/album/{id}", 1L))
      .andExpect(status().isBadRequest())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void dadoCadastroValido_quandoCadastro_entaoSucesso() throws Exception {
    mockMvc.perform(post("/album").contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(criaAlbumDTO())))
      .andExpect(status().isBadRequest())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void dadoCadastroFotografoInvalido_quandoCadastro_entaoSucesso() throws Exception {
    when(fotografoRepository.findById(anyLong())).thenReturn(Optional.empty());
    mockMvc.perform(post("/album").contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(criaAlbumDTO())))
      .andExpect(status().isBadRequest())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void dadoCadastroQualidadeInvalida_quandoCadastro_entaoSucesso() throws Exception {
    var albumDTO = AlbumDTO.builder().fotografoId(1L).qualidade(-1).build();
    mockMvc.perform(post("/album").contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(albumDTO)))
      .andExpect(status().isBadRequest())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void dadoCadastroValido_quandoAtualizaAlbum_entaoSucesso() throws Exception {
    when(albumService.atualizaAlbum(anyLong(), any(AlbumUpdateDTO.class))).thenReturn(album);
    mockMvc.perform(put("/album/{id}", 1L).contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(criaAlbumUpdateDTO())))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.qualidade", is(album.getQualidade())));
  }

  @Test
  void dadoIdExistente_quandoDeleta_entaoSucesso() throws Exception {
    mockMvc.perform(delete("/album/{id}", 1L))
      .andExpect(status().isOk());
  }

  private static AlbumDTO criaAlbumDTO() {
    return AlbumDTO.builder().fotografoId(1L).nome("Teste").qualidade(50).build();
  }

  private static AlbumUpdateDTO criaAlbumUpdateDTO() {
    return AlbumUpdateDTO.builder().nome("Novo Nome").qualidade(10).build();
  }
}