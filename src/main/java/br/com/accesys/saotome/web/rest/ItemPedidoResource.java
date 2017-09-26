package br.com.accesys.saotome.web.rest;

import br.com.accesys.saotome.domain.ItemPedido;
import br.com.accesys.saotome.domain.Pedido;
import br.com.accesys.saotome.repository.ItemPedidoRepository;
import br.com.accesys.saotome.service.PedidoService;
import br.com.accesys.saotome.service.dto.PedidoDTO;
import br.com.accesys.saotome.web.rest.util.HeaderUtil;
import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

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
        log.debug("REST request to get all ItemPedidos");
        return itemPedidoRepository.findAll();
    }

    /**
     * GET  /item-pedidos/:id : get the "id" itemPedido.
     */
    @GetMapping("/item-pedidos/{id}")
    @Timed
    public ResponseEntity<ItemPedido> getItemPedido(@PathVariable Long id) {
        log.debug("REST request to get ItemPedido : {}", id);
        ItemPedido itemPedido = itemPedidoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(itemPedido));
    }

    /**
     * DELETE  /item-pedidos/:id : delete the "id" itemPedido.
     */
    @DeleteMapping("/item-pedidos/{id}")
    @Timed
    public ResponseEntity<Void> deleteItemPedido(@PathVariable Long id) {
        log.debug("REST request to delete ItemPedido : {}", id);
        itemPedidoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
