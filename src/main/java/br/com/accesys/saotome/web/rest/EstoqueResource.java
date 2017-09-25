package br.com.accesys.saotome.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.accesys.saotome.domain.Estoque;

import br.com.accesys.saotome.repository.EstoqueRepository;
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
 * REST controller for managing Estoque.
 */
@RestController
@RequestMapping("/api")
public class EstoqueResource {

    private final Logger log = LoggerFactory.getLogger(EstoqueResource.class);

    private static final String ENTITY_NAME = "estoque";

    private final EstoqueRepository estoqueRepository;

    public EstoqueResource(EstoqueRepository estoqueRepository) {
        this.estoqueRepository = estoqueRepository;
    }

    /**
     * POST  /estoques : Create a new estoque.
     *
     * @param estoque the estoque to create
     * @return the ResponseEntity with status 201 (Created) and with body the new estoque, or with status 400 (Bad Request) if the estoque has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/estoques")
    @Timed
    public ResponseEntity<Estoque> createEstoque(@Valid @RequestBody Estoque estoque) throws URISyntaxException {
        log.debug("REST request to save Estoque : {}", estoque);
        if (estoque.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new estoque cannot already have an ID")).body(null);
        }
        Estoque result = estoqueRepository.save(estoque);
        return ResponseEntity.created(new URI("/api/estoques/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /estoques : Updates an existing estoque.
     *
     * @param estoque the estoque to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated estoque,
     * or with status 400 (Bad Request) if the estoque is not valid,
     * or with status 500 (Internal Server Error) if the estoque couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/estoques")
    @Timed
    public ResponseEntity<Estoque> updateEstoque(@Valid @RequestBody Estoque estoque) throws URISyntaxException {
        log.debug("REST request to update Estoque : {}", estoque);
        if (estoque.getId() == null) {
            return createEstoque(estoque);
        }
        Estoque result = estoqueRepository.save(estoque);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, estoque.getId().toString()))
            .body(result);
    }

    /**
     * GET  /estoques : get all the estoques.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of estoques in body
     */
    @GetMapping("/estoques")
    @Timed
    public List<Estoque> getAllEstoques() {
        log.debug("REST request to get all Estoques");
        return estoqueRepository.findAll();
        }

    /**
     * GET  /estoques/:id : get the "id" estoque.
     *
     * @param id the id of the estoque to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the estoque, or with status 404 (Not Found)
     */
    @GetMapping("/estoques/{id}")
    @Timed
    public ResponseEntity<Estoque> getEstoque(@PathVariable Long id) {
        log.debug("REST request to get Estoque : {}", id);
        Estoque estoque = estoqueRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(estoque));
    }

    /**
     * DELETE  /estoques/:id : delete the "id" estoque.
     *
     * @param id the id of the estoque to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/estoques/{id}")
    @Timed
    public ResponseEntity<Void> deleteEstoque(@PathVariable Long id) {
        log.debug("REST request to delete Estoque : {}", id);
        estoqueRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
