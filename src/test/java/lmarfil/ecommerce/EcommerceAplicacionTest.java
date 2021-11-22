package lmarfil.ecommerce;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class EcommerceAplicacionTest {
	
	@Test
	void contextLoads() {
		
	}
/**	
	@Test
	void testRecta() {
		Receta r = new Receta();
		r.setNombre("Aaa");		
		recetaRepo.save(r);		
		Optional<Receta> findById = recetaRepo.findById(r.getId());
		assertTrue(findById.isPresent());		
		Logger.getLogger("").info(findById.toString());
	}
	
	@Test
	void testCategoria() {
		Categoria c = new Categoria();
		c.setNombre("Algo fantasticoaa");
		catRepo.save(c);
	}
	
	@Test
	void testCliente() {
		Cliente c = new Cliente();
		c.setNombre("Algoaa");
		clRepo.save(c);
	}

	@Test
	void testConfiguracion() {
		Configuracion c = new Configuracion();
		c.setClave("uno");
		c.setValor("dos");
		confRepo.save(c);
	}
	
	@Test
	void testItem() {
		Medida m = new Medida();
		m.setUnidad("cc");
		m.setAbreviatura("cc");
		m.setBase(1000);
		Item i = new Item();
		i.setNombre("a");
		i.setBase(12.0);
		i.setMedida(m);
		i.setPrecio(1.0);
		itRepo.save(i);
		
		Optional<Medida> findById = medidaRepo.findById(m.getId());
		
		Logger.getLogger("").info(findById.toString());


	}
	
	@Test
	void testMedida() {
		Iterable<Medida> findAll = medidaRepo.findAll();
		Iterator<Medida> iterator = findAll.iterator();
		while (iterator.hasNext()) {
			Medida medida = (Medida) iterator.next();
			Logger.getLogger("").info(medida.getAbreviatura() + "  " + medida.getItems().size());
		}
	}
	
	@Test
	void testPresupuesto() {
		Optional<Cliente> cl = clRepo.findById(4);
		EstadoPresupuesto e = new EstadoPresupuestoPropuesto();
		Presupuesto p = new Presupuesto();
		p.setCliente(cl.get());
		p.setEstadoPresupuesto(e);
		p.setNombre("nombre");
		preRepo.save(p);
	}
	
	@Test
	void testAllRepos() {
		recetaRepo.findById(1);
		catRepo.findById(1);
		clRepo.findById(1);
		confRepo.findById("1");
		itRepo.findById(1);
		medidaRepo.findById(1);
		preRepo.findById(1);
	}
	**/
}
