package br.com.accesys.saotome.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.accesys.saotome.domain.ItemPedido;

import br.com.accesys.saotome.repository.ItemPedidoRepository;
import br.com.accesys.saotome.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ItemPedido.
 */
@RestController
@RequestMapping("/api")
public class ItemPedidoResource {

    private final Logger log = LoggerFactory.getLogger(ItemPedidoResource.class);

    private static final String ENTITY_NAME = "itemPedido";

    private final ItemPedidoRepository itemPedidoRepository;

    public ItemPedidoResource(ItemPedidoRepository itemPedidoRepository) {
        this.itemPedidoRepository = itemPedidoRepository;
    }

    /**
     * POST  /item-pedidos : Create a new itemPedido.
     *
     * @param itemPedido the itemPedido to create
     * @return the ResponseEntity with status 201 (Created) and with body the new itemPedido, or with status 400 (Bad Request) if the itemPedido has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/item-pedidos")
    @Timed
    public ResponseEntity<ItemPedido> createItemPedido(@Valid @RequestBody ItemPedido itemPedido) throws URISyntaxException {
        log.debug("REST request to save ItemPedido : {}", itemPedido);
        if (itemPedido.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new itemPedido cannot already have an ID")).body(null);
        }
        ItemPedido result = itemPedidoRepository.save(itemPedido);
        return ResponseEntity.created(new URI("/api/item-pedidos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /item-pedidos : Updates an existing itemPedido.
     *
     * @param itemPedido the itemPedido to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated itemPedido,
     * or with status 400 (Bad Request) if the itemPedido is not valid,
     * or with status 500 (Internal Server Error) if the itemPedido couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/item-pedidos")
    @Timed
    public ResponseEntity<ItemPedido> updateItemPedido(@Valid @RequestBody ItemPedido itemPedido) throws URISyntaxException {
        log.debug("REST request to update ItemPedido : {}", itemPedido);
        if (itemPedido.getId() == null) {
            return createItemPedido(itemPedido);
        }
        ItemPedido result = itemPedidoRepository.save(itemPedido);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, itemPedido.getId().toString()))
            .body(result);
    }

    /**
     * GET  /item-pedidos : get all the itemPedidos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of itemPedidos in body
     */
    @GetMapping("/item-pedidos")
    @Timed
    public List<ItemPedido> getAllItemPedidos() {
        log.debug("REST request to get all ItemPedidos");
        return itemPedidoRepository.findAll();
        }

    /**
     * GET  /item-pedidos/:id : get the "id" itemPedido.
     *
     * @param id the id of the itemPedido to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the itemPedido, or with status 404 (Not Found)
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
     *
     * @param id the id of the itemPedido to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/item-pedidos/{id}")
    @Timed
    public ResponseEntity<Void> deleteItemPedido(@PathVariable Long id) {
        log.debug("REST request to delete ItemPedido : {}", id);
        itemPedidoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
