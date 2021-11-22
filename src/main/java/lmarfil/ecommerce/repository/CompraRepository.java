package lmarfil.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import lmarfil.ecommerce.model.Compra;

@RestResource(exported = false)
@Repository
public interface CompraRepository extends JpaRepository<Compra, Long>,JpaSpecificationExecutor<Compra> {

	Optional<Compra> findByClienteDniAndCarritoDeCompraId(Long dni, Long idCarrito);
}
