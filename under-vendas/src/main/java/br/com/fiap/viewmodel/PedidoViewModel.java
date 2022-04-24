package br.com.fiap.viewmodel;

import br.com.fiap.model.Pedido;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.internal.util.collections.CollectionHelper;
import org.springframework.beans.BeanUtils;

@Setter
@Getter
public class PedidoViewModel {

  private Long id;

  private String nome;

  private String email;

  private BigDecimal valorTotal;

  private List<String> fotos;

  public PedidoViewModel(Pedido pedido) {
    BeanUtils.copyProperties(pedido, this);
    if (CollectionHelper.isNotEmpty(pedido.getPedidoItems())) {
      fotos = pedido.getPedidoItems().stream().map(a -> a.getFoto().getFilename()).collect(Collectors.toList());
    }
  }
}
