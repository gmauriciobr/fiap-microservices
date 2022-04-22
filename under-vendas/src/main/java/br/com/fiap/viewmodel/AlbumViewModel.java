package br.com.fiap.viewmodel;

import br.com.fiap.model.Album;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class AlbumViewModel {

  private String token;

  private String nome;

  private List<FotoViewModel> fotos;

  public AlbumViewModel(Album album) {
    BeanUtils.copyProperties(album, this);
    fotos = Optional.ofNullable(album.getFotos()).map(FotoViewModel::parse).orElse(Collections.emptyList());
  }
}
