package br.com.fiap.viewmodel;

import br.com.fiap.model.Album;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Setter
@Getter
public class AlbumViewModel {

  private String token;

  private String nome;

  private BigDecimal valorFoto;

  private List<FotoViewModel> fotos;

  public AlbumViewModel(Album album) {
    BeanUtils.copyProperties(album, this);
    fotos = Optional.ofNullable(album.getFotos()).map(FotoViewModel::parse).orElse(Collections.emptyList());
  }
}
