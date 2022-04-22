package br.com.fiap.viewmodel;

import br.com.fiap.model.Fotografo;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Setter
@Getter
public class FotografoViewModel {

  private Long id;
  private String nome;
  private List<AlbumResumidoViewModel> albums;

  public FotografoViewModel(Fotografo fotografo) {
    BeanUtils.copyProperties(fotografo, this);
    albums = Optional.ofNullable(fotografo.getAlbums()).map(AlbumResumidoViewModel::parse).orElse(Collections.emptyList());
  }
}
