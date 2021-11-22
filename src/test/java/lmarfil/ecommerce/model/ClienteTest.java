package lmarfil.ecommerce.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.equalToObject;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.isA;


import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lmarfil.ecommerce.repository.ClienteRepository;
import lmarfil.ecommerce.service.ClienteService;

@SpringBootTest
public class ClienteTest {

	@Autowired
	ClienteRepository carritoDeCompraRepository;
	@Autowired
	ClienteService    clienteService;
	
	@Test	
	public void deberiaGuardarUnCliente() {
		//given
		var c  = new Cliente();
		c.dni = 35000111L;
		c.nombre = "Cliente1";
		var c2 = new Cliente(); 
		c2.dni = 35000112L;
		c2.nombre = "Cliente2";
		//when
		clienteService.save(c);
		clienteService.save(c2);
		List<Cliente> clientes = clienteService.findAll();
		var cRecuperado =  clienteService.findById(c.dni).get();
		var cRecuperado2 = clienteService.findById(c2.dni).get();
		
		//then		
		assertThat(c,equalTo(cRecuperado));
		assertThat(c2,equalTo(cRecuperado2));
		
	}
}
