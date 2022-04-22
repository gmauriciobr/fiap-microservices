package br.com.fiap.controller;

import br.com.fiap.dto.PedidoDTO;
import br.com.fiap.service.PedidoService;
import br.com.fiap.viewmodel.PedidoViewModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RestController
@RequestMapping("/pedido")
@RequiredArgsConstructor
@Tag(name = "pedido", description = "Serviço para administração de pedido")
public class PedidoController {

  private final PedidoService pedidoService;

  @PostMapping
  @Operation(tags = {"pedido"}, summary = "Cadastro de pedido")
  public ResponseEntity<PedidoViewModel> cadastraPedido(@RequestBody @Valid PedidoDTO dto, UriComponentsBuilder uriBuilder) {
    log.info("Cadastrando pedido para: {} -  Album: [{}] Qtd Fotos: [{}]",
      dto.getNome(), dto.getTokenAlbum(), dto.getTokenFotos().size());
    var pedido = pedidoService.cadastraPedido(dto);
    var uri = uriBuilder.path("/pedido/{id}").buildAndExpand(pedido.getId()).toUri();
    return ResponseEntity.created(uri).body(new PedidoViewModel(pedido));
  }

  @GetMapping("/{id}")
  @Operation(tags = {"pedido"}, summary = "Busca de pedido")
  public ResponseEntity<PedidoViewModel> buscaPedidoPorId(@PathVariable Long id) {
    log.info("Consutalndo Pedido: [{}]", id);
    return ResponseEntity.ok(new PedidoViewModel(pedidoService.buscaPorId(id)));
  }
}
