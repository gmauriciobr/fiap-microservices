package br.com.fiap.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EnviaPedidoItemDTO {

  private String filename;
  private byte[] fotoOriginal;

}
