package br.com.fiap.service;

import br.com.fiap.dto.AlbumDTO;
import br.com.fiap.dto.AlbumUpdateDTO;
import br.com.fiap.model.Album;
import br.com.fiap.repository.AlbumRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AlbumService {

  private final AlbumRepository albumRepository;

  public Album buscaPorId(Long id) {
    return albumRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Album não encontrado"));
  }

  public Album cadastro(AlbumDTO dto) {
    var album = AlbumDTO.parse(dto);
    album.setToken(UUID.randomUUID().toString());
    return albumRepository.save(album);
  }

  public Album atualizaAlbum(Long id, AlbumUpdateDTO dto) {
    var album = buscaPorId(id);
    BeanUtils.copyProperties(dto, album);
    album.setToken(UUID.randomUUID().toString());
    return albumRepository.save(album);
  }

  public void deleta(Long id) {
    albumRepository.delete(buscaPorId(id));
  }
}
