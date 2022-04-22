package br.com.fiap.dto;

import br.com.fiap.model.Album;
import br.com.fiap.model.Foto;
import br.com.fiap.model.Pedido;
import br.com.fiap.model.PedidoItem;
import br.com.fiap.validation.AlbumConstraint;
import br.com.fiap.validation.FotosConstraint;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PedidoDTO {

  @NotNull
  private String nome;
  @NotNull
  private String email;
  @NotNull
  private String numeroCartao;
  @NotNull
  @AlbumConstraint
  private String tokenAlbum;
  @NotEmpty
  @FotosConstraint
  private Set<String> tokenFotos;

  public static Pedido parse(PedidoDTO dto, Album album, List<Foto> fotos) {
    Pedido pedido = new Pedido();
    BeanUtils.copyProperties(dto, pedido);
    pedido.setAlbum(album);
    pedido.setPedidoItems(fotos.stream().map(foto ->
       PedidoItem.builder().pedido(pedido).foto(foto).build()
    ).collect(Collectors.toList()));
    return pedido;
  }

}
