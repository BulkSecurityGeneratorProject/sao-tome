package br.com.accesys.saotome.web.rest;

import br.com.accesys.saotome.domain.ItemPedido;
import br.com.accesys.saotome.domain.Pedido;
import br.com.accesys.saotome.repository.ItemPedidoRepository;
import br.com.accesys.saotome.service.PedidoService;
import br.com.accesys.saotome.service.dto.PedidoDTO;
import br.com.accesys.saotome.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ItemPedidoResource {

    private final Logger log = LoggerFactory.getLogger(ItemPedidoResource.class);

    private static final String ENTITY_NAME = "itemPedido";

    private final PedidoService pedidoService;

    private final ItemPedidoRepository itemPedidoRepository;

    public ItemPedidoResource(PedidoService pedidoService, ItemPedidoRepository itemPedidoRepository) {
        this.pedidoService = pedidoService;
        this.itemPedidoRepository = itemPedidoRepository;
    }

    /**
     * POST  /item-pedidos : Create a new itemPedido.
     */
    @PostMapping("/item-pedidos")
    @Timed
    public ResponseEntity<Pedido> createItemPedido(@RequestBody PedidoDTO pedido) throws URISyntaxException {
        Pedido result = pedidoService.save(pedido);
        return ResponseEntity.created(new URI("/api/item-pedidos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * GET  /item-pedidos : get all the itemPedidos.
     */
    @GetMapping("/item-pedidos")
    @Timed
    public List<ItemPedido> getAllItemPedidos() {
        return itemPedidoRepository.findAll();
    }
}
