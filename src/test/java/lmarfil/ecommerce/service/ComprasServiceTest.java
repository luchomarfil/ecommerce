package lmarfil.ecommerce.service;

import static org.hamcrest.MatcherAssert.assertThat;

import java.sql.Time;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lmarfil.ecommerce.model.dto.ComprasDTO;
import lmarfil.ecommerce.model.dto.FiltroCompraDTO;

@SpringBootTest
public class ComprasServiceTest {

	@Autowired
	ComprasService comprasService;
	
	@Test
	public void deberiaListarLasComprasDeUnDni(){
		//given
		var filtro = new FiltroCompraDTO();
		filtro.setDni(3500000L);
		filtro.setFrom(Time.from(Instant.now().minus(5, ChronoUnit.DAYS)));
		//when
		List<ComprasDTO> comprasRealizadas = comprasService.getComprasRealizadas(filtro);		

		//then
		assertThat(comprasRealizadas,Matchers.empty());		
		
	}
	
	
}
