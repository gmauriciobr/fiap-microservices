package br.com.fiap.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AlbumUpdateDTO {

  private String nome;
  private BigDecimal valorFoto;
  @Range(min = 1, max = 100)
  private int qualidade;

}
