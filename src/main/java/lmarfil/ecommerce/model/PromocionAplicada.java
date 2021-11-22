package lmarfil.ecommerce.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class PromocionAplicada {
	
	public PromocionAplicada(Double descuento, String promocion, String resumen) {
		this.descuento = descuento;
		this.promocion = promocion;
		this.resumen = resumen;
	}

	@Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;
	
	//como las promociones no las persisto entonces no las refiero 
	//aca va a ir una descripción que refiera a la promocion. eg: 4x3 en productos iguales
	String promocion;
	//aca va a ir el resumen de la promocion. eg 4x3 en producto Leche (valor total: x, con descuento: y)
	String resumen;
		
	Double descuento;
}
