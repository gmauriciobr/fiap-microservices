package br.com.fiap.viewmodel;

import br.com.fiap.model.Foto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Setter
@Getter
public class FotoViewModel {

  private Long id;

  private String token;

  private String filename;

  private String fotoBaixaQualidade;

  public FotoViewModel(Foto foto) {
    BeanUtils.copyProperties(foto, this);
    fotoBaixaQualidade = String.format("/album/%s/foto/%s", foto.getAlbum().getId(), foto.getId());
  }

  public static List<FotoViewModel> parse(List<Foto> fotos) {
    return fotos.stream().map(FotoViewModel::new).collect(Collectors.toList());
  }
}
