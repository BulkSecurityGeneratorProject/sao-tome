package br.com.accesys.saotome.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.accesys.saotome.domain.Comanda;

import br.com.accesys.saotome.repository.ComandaRepository;
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
 * REST controller for managing Comanda.
 */
@RestController
@RequestMapping("/api")
public class ComandaResource {

    private final Logger log = LoggerFactory.getLogger(ComandaResource.class);

    private static final String ENTITY_NAME = "comanda";

    private final ComandaRepository comandaRepository;

    public ComandaResource(ComandaRepository comandaRepository) {
        this.comandaRepository = comandaRepository;
    }

    /**
     * POST  /comandas : Create a new comanda.
     *
     * @param comanda the comanda to create
     * @return the ResponseEntity with status 201 (Created) and with body the new comanda, or with status 400 (Bad Request) if the comanda has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/comandas")
    @Timed
    public ResponseEntity<Comanda> createComanda(@Valid @RequestBody Comanda comanda) throws URISyntaxException {
        log.debug("REST request to save Comanda : {}", comanda);
        if (comanda.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new comanda cannot already have an ID")).body(null);
        }
        Comanda result = comandaRepository.save(comanda);
        return ResponseEntity.created(new URI("/api/comandas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /comandas : Updates an existing comanda.
     *
     * @param comanda the comanda to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated comanda,
     * or with status 400 (Bad Request) if the comanda is not valid,
     * or with status 500 (Internal Server Error) if the comanda couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/comandas")
    @Timed
    public ResponseEntity<Comanda> updateComanda(@Valid @RequestBody Comanda comanda) throws URISyntaxException {
        log.debug("REST request to update Comanda : {}", comanda);
        if (comanda.getId() == null) {
            return createComanda(comanda);
        }
        Comanda result = comandaRepository.save(comanda);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, comanda.getId().toString()))
            .body(result);
    }

    /**
     * GET  /comandas : get all the comandas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of comandas in body
     */
    @GetMapping("/comandas")
    @Timed
    public List<Comanda> getAllComandas() {
        log.debug("REST request to get all Comandas");
        return comandaRepository.findAll();
        }

    /**
     * GET  /comandas/:id : get the "id" comanda.
     *
     * @param id the id of the comanda to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the comanda, or with status 404 (Not Found)
     */
    @GetMapping("/comandas/{id}")
    @Timed
    public ResponseEntity<Comanda> getComanda(@PathVariable Long id) {
        log.debug("REST request to get Comanda : {}", id);
        Comanda comanda = comandaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(comanda));
    }

    /**
     * DELETE  /comandas/:id : delete the "id" comanda.
     *
     * @param id the id of the comanda to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/comandas/{id}")
    @Timed
    public ResponseEntity<Void> deleteComanda(@PathVariable Long id) {
        log.debug("REST request to delete Comanda : {}", id);
        comandaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
