package br.com.accesys.saotome.repository;

import br.com.accesys.saotome.domain.ItemPedido;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ItemPedido entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {

}
