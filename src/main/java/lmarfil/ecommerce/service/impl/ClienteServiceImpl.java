package lmarfil.ecommerce.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.transaction.Transactional;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lmarfil.ecommerce.model.Cliente;
import lmarfil.ecommerce.repository.ClienteRepository;
import lmarfil.ecommerce.service.ClienteService;

@Service
@Transactional
public class ClienteServiceImpl implements ClienteService{

	Random r = new Random();
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Override
	public Cliente save(Cliente c) {
		return clienteRepository.save(c);
	}
	
	@Override	
	public void editar(Cliente c) {
		clienteRepository.save(c);
	}

	@Override
	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	@Override
	public Optional<Cliente> findById(Long dni) {
		return clienteRepository.findById(dni);
	}

	@Override
	public Cliente findOrCreateById(@Size(min = 1) Long dni) {		
		var clienteOpt = this.findById(dni);
		var cliente = clienteOpt.isEmpty() ? null : clienteOpt.get();
		if(cliente==null) {
			var c = new Cliente();
			c.setDni(dni);
			c.setNombre("Guest"+(Math.abs(r.nextInt())));
			this.save(c);
			cliente = c;
		}
		return cliente;		
	}
	

}
