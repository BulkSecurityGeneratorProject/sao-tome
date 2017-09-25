package br.com.accesys.saotome.web.rest;

import br.com.accesys.saotome.SaotomeApp;

import br.com.accesys.saotome.domain.Comanda;
import br.com.accesys.saotome.repository.ComandaRepository;
import br.com.accesys.saotome.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ComandaResource REST controller.
 *
 * @see ComandaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SaotomeApp.class)
public class ComandaResourceIntTest {

    private static final String DEFAULT_IDENTIFICADOR = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICADOR = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DISPONIVEL = false;
    private static final Boolean UPDATED_DISPONIVEL = true;

    @Autowired
    private ComandaRepository comandaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restComandaMockMvc;

    private Comanda comanda;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ComandaResource comandaResource = new ComandaResource(comandaRepository);
        this.restComandaMockMvc = MockMvcBuilders.standaloneSetup(comandaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Comanda createEntity(EntityManager em) {
        Comanda comanda = new Comanda()
            .identificador(DEFAULT_IDENTIFICADOR)
            .disponivel(DEFAULT_DISPONIVEL);
        return comanda;
    }

    @Before
    public void initTest() {
        comanda = createEntity(em);
    }

    @Test
    @Transactional
    public void createComanda() throws Exception {
        int databaseSizeBeforeCreate = comandaRepository.findAll().size();

        // Create the Comanda
        restComandaMockMvc.perform(post("/api/comandas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comanda)))
            .andExpect(status().isCreated());

        // Validate the Comanda in the database
        List<Comanda> comandaList = comandaRepository.findAll();
        assertThat(comandaList).hasSize(databaseSizeBeforeCreate + 1);
        Comanda testComanda = comandaList.get(comandaList.size() - 1);
        assertThat(testComanda.getIdentificador()).isEqualTo(DEFAULT_IDENTIFICADOR);
        assertThat(testComanda.isDisponivel()).isEqualTo(DEFAULT_DISPONIVEL);
    }

    @Test
    @Transactional
    public void createComandaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = comandaRepository.findAll().size();

        // Create the Comanda with an existing ID
        comanda.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restComandaMockMvc.perform(post("/api/comandas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comanda)))
            .andExpect(status().isBadRequest());

        // Validate the Comanda in the database
        List<Comanda> comandaList = comandaRepository.findAll();
        assertThat(comandaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdentificadorIsRequired() throws Exception {
        int databaseSizeBeforeTest = comandaRepository.findAll().size();
        // set the field null
        comanda.setIdentificador(null);

        // Create the Comanda, which fails.

        restComandaMockMvc.perform(post("/api/comandas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comanda)))
            .andExpect(status().isBadRequest());

        List<Comanda> comandaList = comandaRepository.findAll();
        assertThat(comandaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllComandas() throws Exception {
        // Initialize the database
        comandaRepository.saveAndFlush(comanda);

        // Get all the comandaList
        restComandaMockMvc.perform(get("/api/comandas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comanda.getId().intValue())))
            .andExpect(jsonPath("$.[*].identificador").value(hasItem(DEFAULT_IDENTIFICADOR.toString())))
            .andExpect(jsonPath("$.[*].disponivel").value(hasItem(DEFAULT_DISPONIVEL.booleanValue())));
    }

    @Test
    @Transactional
    public void getComanda() throws Exception {
        // Initialize the database
        comandaRepository.saveAndFlush(comanda);

        // Get the comanda
        restComandaMockMvc.perform(get("/api/comandas/{id}", comanda.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(comanda.getId().intValue()))
            .andExpect(jsonPath("$.identificador").value(DEFAULT_IDENTIFICADOR.toString()))
            .andExpect(jsonPath("$.disponivel").value(DEFAULT_DISPONIVEL.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingComanda() throws Exception {
        // Get the comanda
        restComandaMockMvc.perform(get("/api/comandas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComanda() throws Exception {
        // Initialize the database
        comandaRepository.saveAndFlush(comanda);
        int databaseSizeBeforeUpdate = comandaRepository.findAll().size();

        // Update the comanda
        Comanda updatedComanda = comandaRepository.findOne(comanda.getId());
        updatedComanda
            .identificador(UPDATED_IDENTIFICADOR)
            .disponivel(UPDATED_DISPONIVEL);

        restComandaMockMvc.perform(put("/api/comandas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedComanda)))
            .andExpect(status().isOk());

        // Validate the Comanda in the database
        List<Comanda> comandaList = comandaRepository.findAll();
        assertThat(comandaList).hasSize(databaseSizeBeforeUpdate);
        Comanda testComanda = comandaList.get(comandaList.size() - 1);
        assertThat(testComanda.getIdentificador()).isEqualTo(UPDATED_IDENTIFICADOR);
        assertThat(testComanda.isDisponivel()).isEqualTo(UPDATED_DISPONIVEL);
    }

    @Test
    @Transactional
    public void updateNonExistingComanda() throws Exception {
        int databaseSizeBeforeUpdate = comandaRepository.findAll().size();

        // Create the Comanda

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restComandaMockMvc.perform(put("/api/comandas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comanda)))
            .andExpect(status().isCreated());

        // Validate the Comanda in the database
        List<Comanda> comandaList = comandaRepository.findAll();
        assertThat(comandaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteComanda() throws Exception {
        // Initialize the database
        comandaRepository.saveAndFlush(comanda);
        int databaseSizeBeforeDelete = comandaRepository.findAll().size();

        // Get the comanda
        restComandaMockMvc.perform(delete("/api/comandas/{id}", comanda.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Comanda> comandaList = comandaRepository.findAll();
        assertThat(comandaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Comanda.class);
        Comanda comanda1 = new Comanda();
        comanda1.setId(1L);
        Comanda comanda2 = new Comanda();
        comanda2.setId(comanda1.getId());
        assertThat(comanda1).isEqualTo(comanda2);
        comanda2.setId(2L);
        assertThat(comanda1).isNotEqualTo(comanda2);
        comanda1.setId(null);
        assertThat(comanda1).isNotEqualTo(comanda2);
    }
}
