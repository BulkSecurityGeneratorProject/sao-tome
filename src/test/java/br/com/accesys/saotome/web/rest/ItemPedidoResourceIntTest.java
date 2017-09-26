package br.com.accesys.saotome.web.rest;

import br.com.accesys.saotome.SaotomeApp;

import br.com.accesys.saotome.domain.ItemPedido;
import br.com.accesys.saotome.domain.Pedido;
import br.com.accesys.saotome.domain.Produto;
import br.com.accesys.saotome.repository.ItemPedidoRepository;
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
 * Test class for the ItemPedidoResource REST controller.
 *
 * @see ItemPedidoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SaotomeApp.class)
public class ItemPedidoResourceIntTest {

    private static final Long DEFAULT_QUANTIDADE = 1L;
    private static final Long UPDATED_QUANTIDADE = 2L;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restItemPedidoMockMvc;

    private ItemPedido itemPedido;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ItemPedidoResource itemPedidoResource = new ItemPedidoResource(itemPedidoRepository);
        this.restItemPedidoMockMvc = MockMvcBuilders.standaloneSetup(itemPedidoResource)
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
    public static ItemPedido createEntity(EntityManager em) {
        ItemPedido itemPedido = new ItemPedido()
            .quantidade(DEFAULT_QUANTIDADE);
        // Add required entity
        Pedido pedido = PedidoResourceIntTest.createEntity(em);
        em.persist(pedido);
        em.flush();
        itemPedido.setPedido(pedido);
        // Add required entity
        Produto produto = ProdutoResourceIntTest.createEntity(em);
        em.persist(produto);
        em.flush();
        itemPedido.setProduto(produto);
        return itemPedido;
    }

    @Before
    public void initTest() {
        itemPedido = createEntity(em);
    }

    @Test
    @Transactional
    public void createItemPedido() throws Exception {
        int databaseSizeBeforeCreate = itemPedidoRepository.findAll().size();

        // Create the ItemPedido
        restItemPedidoMockMvc.perform(post("/api/item-pedidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPedido)))
            .andExpect(status().isCreated());

        // Validate the ItemPedido in the database
        List<ItemPedido> itemPedidoList = itemPedidoRepository.findAll();
        assertThat(itemPedidoList).hasSize(databaseSizeBeforeCreate + 1);
        ItemPedido testItemPedido = itemPedidoList.get(itemPedidoList.size() - 1);
        assertThat(testItemPedido.getQuantidade()).isEqualTo(DEFAULT_QUANTIDADE);
    }

    @Test
    @Transactional
    public void createItemPedidoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itemPedidoRepository.findAll().size();

        // Create the ItemPedido with an existing ID
        itemPedido.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemPedidoMockMvc.perform(post("/api/item-pedidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPedido)))
            .andExpect(status().isBadRequest());

        // Validate the ItemPedido in the database
        List<ItemPedido> itemPedidoList = itemPedidoRepository.findAll();
        assertThat(itemPedidoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkQuantidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemPedidoRepository.findAll().size();
        // set the field null
        itemPedido.setQuantidade(null);

        // Create the ItemPedido, which fails.

        restItemPedidoMockMvc.perform(post("/api/item-pedidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPedido)))
            .andExpect(status().isBadRequest());

        List<ItemPedido> itemPedidoList = itemPedidoRepository.findAll();
        assertThat(itemPedidoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllItemPedidos() throws Exception {
        // Initialize the database
        itemPedidoRepository.saveAndFlush(itemPedido);

        // Get all the itemPedidoList
        restItemPedidoMockMvc.perform(get("/api/item-pedidos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemPedido.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE.intValue())));
    }

    @Test
    @Transactional
    public void getItemPedido() throws Exception {
        // Initialize the database
        itemPedidoRepository.saveAndFlush(itemPedido);

        // Get the itemPedido
        restItemPedidoMockMvc.perform(get("/api/item-pedidos/{id}", itemPedido.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(itemPedido.getId().intValue()))
            .andExpect(jsonPath("$.quantidade").value(DEFAULT_QUANTIDADE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingItemPedido() throws Exception {
        // Get the itemPedido
        restItemPedidoMockMvc.perform(get("/api/item-pedidos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemPedido() throws Exception {
        // Initialize the database
        itemPedidoRepository.saveAndFlush(itemPedido);
        int databaseSizeBeforeUpdate = itemPedidoRepository.findAll().size();

        // Update the itemPedido
        ItemPedido updatedItemPedido = itemPedidoRepository.findOne(itemPedido.getId());
        updatedItemPedido
            .quantidade(UPDATED_QUANTIDADE);

        restItemPedidoMockMvc.perform(put("/api/item-pedidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedItemPedido)))
            .andExpect(status().isOk());

        // Validate the ItemPedido in the database
        List<ItemPedido> itemPedidoList = itemPedidoRepository.findAll();
        assertThat(itemPedidoList).hasSize(databaseSizeBeforeUpdate);
        ItemPedido testItemPedido = itemPedidoList.get(itemPedidoList.size() - 1);
        assertThat(testItemPedido.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void updateNonExistingItemPedido() throws Exception {
        int databaseSizeBeforeUpdate = itemPedidoRepository.findAll().size();

        // Create the ItemPedido

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restItemPedidoMockMvc.perform(put("/api/item-pedidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemPedido)))
            .andExpect(status().isCreated());

        // Validate the ItemPedido in the database
        List<ItemPedido> itemPedidoList = itemPedidoRepository.findAll();
        assertThat(itemPedidoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteItemPedido() throws Exception {
        // Initialize the database
        itemPedidoRepository.saveAndFlush(itemPedido);
        int databaseSizeBeforeDelete = itemPedidoRepository.findAll().size();

        // Get the itemPedido
        restItemPedidoMockMvc.perform(delete("/api/item-pedidos/{id}", itemPedido.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ItemPedido> itemPedidoList = itemPedidoRepository.findAll();
        assertThat(itemPedidoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemPedido.class);
        ItemPedido itemPedido1 = new ItemPedido();
        itemPedido1.setId(1L);
        ItemPedido itemPedido2 = new ItemPedido();
        itemPedido2.setId(itemPedido1.getId());
        assertThat(itemPedido1).isEqualTo(itemPedido2);
        itemPedido2.setId(2L);
        assertThat(itemPedido1).isNotEqualTo(itemPedido2);
        itemPedido1.setId(null);
        assertThat(itemPedido1).isNotEqualTo(itemPedido2);
    }
}
