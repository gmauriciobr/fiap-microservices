package br.com.fiap.dto;

import br.com.fiap.model.Fotografo;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
public class FotografoDTO {

  private String nome;
  private String email;

  public static Fotografo parse(FotografoDTO dto) {
    var fotografo = new Fotografo();
    BeanUtils.copyProperties(dto, fotografo);
    return fotografo;
  }
}
