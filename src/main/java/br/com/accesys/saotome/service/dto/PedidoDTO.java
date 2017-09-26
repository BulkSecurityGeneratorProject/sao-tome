package br.com.accesys.saotome.service.dto;

import br.com.accesys.saotome.domain.Comanda;
import br.com.accesys.saotome.domain.ItemPedido;
import lombok.Data;

import java.util.List;

@Data
public class PedidoDTO {

    private Comanda comanda;

    private List<ItemPedido> itens;
}
