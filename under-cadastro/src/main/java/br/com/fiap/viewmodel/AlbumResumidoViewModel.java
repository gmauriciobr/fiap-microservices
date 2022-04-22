package br.com.fiap.viewmodel;

import br.com.fiap.model.Album;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Setter
@Getter
public class AlbumResumidoViewModel {

  private Long id;
  private String nome;
  private String token;
  private Long quantidadeFotos;

  public AlbumResumidoViewModel(Album album) {
    BeanUtils.copyProperties(album, this);
  }

  public static List<AlbumResumidoViewModel> parse(List<Album> albums) {
    return albums.stream().map(AlbumResumidoViewModel::new).collect(Collectors.toList());
  }

}
