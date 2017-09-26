package br.com.accesys.saotome.service;

import br.com.accesys.saotome.domain.ItemPedido;
import br.com.accesys.saotome.domain.Pedido;
import br.com.accesys.saotome.repository.ItemPedidoRepository;
import br.com.accesys.saotome.repository.PedidoRepository;
import br.com.accesys.saotome.service.dto.PedidoDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    private final ItemPedidoRepository itemPedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository, ItemPedidoRepository itemPedidoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.itemPedidoRepository = itemPedidoRepository;
    }

    public Pedido save(PedidoDTO pedido) {
        Pedido result = pedidoRepository.saveAndFlush(new Pedido().comanda(pedido.getComanda()).data(Instant.now()));

        List<ItemPedido> itens = pedido.getItens();
        itens.forEach(i -> i.setPedido(result));
        itemPedidoRepository.save(itens);

        return result;
    }
}
