package br.com.accesys.saotome.repository;

import br.com.accesys.saotome.domain.Comanda;
import br.com.accesys.saotome.domain.Pedido;
import br.com.accesys.saotome.domain.Produto;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    default List<Pedido> buscarPorComanda(Comanda comanda) {
        return findAll(Example.of(new Pedido().comanda(comanda)));
    }
}
