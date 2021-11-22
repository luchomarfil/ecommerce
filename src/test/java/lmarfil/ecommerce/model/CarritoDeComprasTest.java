package lmarfil.ecommerce.model;

import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.isA;

import java.util.Date;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;

import lmarfil.ecommerce.repository.CarritoDeCompraRepository;

@SpringBootTest
public class CarritoDeComprasTest {

	@Autowired
	CarritoDeCompraRepository carritoDeCompraRepository;
	
	@Test
	public void deberiaSeleccionarCarritosDeCompra(){
		//given
		var fechaCreacion = new Date();
		
		var carrito = new CarritoDeCompra();
		carrito.fechaCreacion = fechaCreacion;
		
		var carritoEspecial = new CarritoDeCompraEspecial();
		carritoEspecial.fechaCreacion = fechaCreacion;
		
		carritoDeCompraRepository.save(carrito);
		carritoDeCompraRepository.save(carritoEspecial);
		
		//when
		List<CarritoDeCompra> findAll = carritoDeCompraRepository.findAll();		

		//then
		
		assertThat(findAll, everyItem(anyOf(isA(CarritoDeCompra.class),isA(CarritoDeCompraEspecial.class))));		
		
	}
	
	@Test
	public void deberiaSeleccionarCarritosDeCompraEspeciales(){
		//given
		var fechaCreacion = new Date();
		
		var carrito = new CarritoDeCompra();
		carrito.fechaCreacion = fechaCreacion;
		
		var carritoEspecial = new CarritoDeCompraEspecial();
		carritoEspecial.fechaCreacion = fechaCreacion;
		
		carritoDeCompraRepository.save(carrito);
		carritoDeCompraRepository.save(carritoEspecial);
		
		//when
		List<CarritoDeCompra> findAll = carritoDeCompraRepository.findAll(Example.of(new CarritoDeCompraEspecial()));


		//then
		assertThat(findAll, Matchers.everyItem(isA(CarritoDeCompraEspecial.class)));		
		
	}
	
}
