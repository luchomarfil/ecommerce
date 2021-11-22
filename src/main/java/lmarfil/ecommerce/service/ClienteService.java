package lmarfil.ecommerce.service;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Size;

import lmarfil.ecommerce.model.Cliente;

public interface ClienteService {


	void editar(Cliente c);

	List<Cliente> findAll();

	Optional<Cliente> findById(Long dni);

	Cliente save(Cliente c);

	Cliente findOrCreateById(@Size(min = 1) Long dni);

}
