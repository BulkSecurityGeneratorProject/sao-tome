package br.com.accesys.saotome.web.rest;

import br.com.accesys.saotome.SaotomeApp;
import br.com.accesys.saotome.domain.Comanda;
import br.com.accesys.saotome.domain.ItemPedido;
import br.com.accesys.saotome.domain.Pedido;
import br.com.accesys.saotome.domain.Produto;
import br.com.accesys.saotome.repository.ItemPedidoRepository;
import br.com.accesys.saotome.service.PedidoService;
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
import java.time.Instant;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    private PedidoService pedidoService;

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
        final ItemPedidoResource itemPedidoResource = new ItemPedidoResource(pedidoService, itemPedidoRepository);
        this.restItemPedidoMockMvc = MockMvcBuilders.standaloneSetup(itemPedidoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemPedido createEntity(EntityManager em) {
        ItemPedido itemPedido = new ItemPedido().quantidade(DEFAULT_QUANTIDADE);
        // Add required entity
        Pedido pedido = createPedido(em);
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

    private static Pedido createPedido(EntityManager em) {
        Pedido pedido = new Pedido().data(Instant.now());
        Comanda comanda = ComandaResourceIntTest.createEntity(em);
        em.persist(comanda);
        em.flush();
        pedido.setComanda(comanda);
        return pedido;
    }

    @Before
    public void initTest() {
        itemPedido = createEntity(em);
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
}
