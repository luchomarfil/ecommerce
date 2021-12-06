package lmarfil.ecommerce.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Optional;

import javax.transaction.Transactional;

import org.assertj.core.api.Fail;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lmarfil.ecommerce.model.Compra;
import lmarfil.ecommerce.model.EstadoCompraCancelada;
import lmarfil.ecommerce.model.dto.CarritoDeCompraDTO;
import lmarfil.ecommerce.model.dto.CrearCarritoDTO;
import lmarfil.ecommerce.model.dto.IdentificadorCarritoDTO;

@SpringBootTest
@Transactional
public class CarritoService {

	@Autowired
	CarritoDeCompraService carritoService;
	@Autowired
	ComprasService compraService;
	
	@Test
	public void deberiaCrearUnCarrito(){
		//given
		CrearCarritoDTO crearCarrito = new CrearCarritoDTO();
		crearCarrito.setDni(30575999L);
		crearCarrito.setIsEspecial(true);
	
		//when
		CarritoDeCompraDTO creado = carritoService.crearNuevoCarrito(crearCarrito);		

		//then
		assertThat(creado.getCliente(),equalTo(crearCarrito.getDni()));
		assertThat(creado.getId(),Matchers.notNullValue());
		
	}
	
	@Test
	public void deberiaEliminarUnCarritoSolicitado() throws Exception{
		//given
		CrearCarritoDTO crearCarrito = new CrearCarritoDTO();
		crearCarrito.setDni(30575999L);
		crearCarrito.setIsEspecial(true);
		CarritoDeCompraDTO creado = carritoService.crearNuevoCarrito(crearCarrito);
		
		
		//when
		IdentificadorCarritoDTO eliminarDto = new IdentificadorCarritoDTO();
		eliminarDto.setDni(creado.getCliente());
		eliminarDto.setIdCarrito(creado.getId());
		carritoService.eliminarCarrito(eliminarDto);

		//then
		Optional<Compra> compra  = compraService.findByClienteDniAndCarritoDeCompraId(creado.getCliente(), creado.getId());		
		
		if(compra.isEmpty()) {
			Fail.fail("No se pudo terminar el test", new RuntimeException());
		}
		
		assertThat(compra.get().getEstadosAnteriores(), Matchers.hasSize(1));
		assertThat(compra.get().getEstadoOrdenDeCompra(),Matchers.isA(EstadoCompraCancelada.class));
		
	}
	
}
