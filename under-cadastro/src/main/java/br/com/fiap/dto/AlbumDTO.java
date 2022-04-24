package br.com.fiap.dto;

import br.com.fiap.model.Album;
import br.com.fiap.model.Fotografo;
import br.com.fiap.validation.FotografoConstraint;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.BeanUtils;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AlbumDTO {

  @NotNull
  @FotografoConstraint
  private Long fotografoId;
  private String nome;
  private BigDecimal valorFoto;
  @Range(min = 1, max = 100)
  private int qualidade;

  public static Album parse(AlbumDTO dto) {
    var entity = new Album();
    entity.setFotografo(new Fotografo(dto.getFotografoId()));
    BeanUtils.copyProperties(dto, entity);
    return entity;
  }
}
