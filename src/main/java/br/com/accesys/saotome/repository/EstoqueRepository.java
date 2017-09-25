package br.com.accesys.saotome.repository;

import br.com.accesys.saotome.domain.Estoque;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Estoque entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

}
