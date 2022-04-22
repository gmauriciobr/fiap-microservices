package br.com.fiap.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ProcessamentoFotoDTO implements Serializable {

  private Long id;
  private String filename;
  private int qualidade;
  private byte[] file;

}
