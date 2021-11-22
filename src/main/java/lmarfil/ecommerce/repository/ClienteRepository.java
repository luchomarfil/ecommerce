package lmarfil.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import lmarfil.ecommerce.model.Cliente;

@RestResource(exported = false)
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
