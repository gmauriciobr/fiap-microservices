package br.com.fiap.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EnviaPedidoDTO {

  private Long id;
  private String nome;
  private String email;
  private String tokenAlbum;
  private List<EnviaPedidoItemDTO> fotos;

}
