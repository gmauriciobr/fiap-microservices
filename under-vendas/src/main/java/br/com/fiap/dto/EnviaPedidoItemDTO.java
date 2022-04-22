package br.com.fiap.dto;

import br.com.fiap.model.Foto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EnviaPedidoItemDTO {

  private String filename;
  private byte[] fotoOriginal;

  public EnviaPedidoItemDTO(Foto foto) {
    BeanUtils.copyProperties(foto, this);
  }

  public static List<EnviaPedidoItemDTO> parse(List<Foto> fotos) {
    return fotos.stream().map(EnviaPedidoItemDTO::new).collect(Collectors.toList());
  }

}
