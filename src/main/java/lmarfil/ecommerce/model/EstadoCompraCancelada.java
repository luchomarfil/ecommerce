package lmarfil.ecommerce.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@DiscriminatorValue(value = "CANCELADO")
@Data @EqualsAndHashCode(callSuper = true)
public class EstadoCompraCancelada extends EstadoCompra{
	
	@Override	
	public boolean estaFinalizada() {
	
		return false;
	}

	@Override
	public boolean estaCancelada() {
	
		return true;
	}

	@Override
	public boolean estaAbierta() {
	
		return false;
	}

	@Override
	public String getTipo() {
		return "Compra Cancelada";
	}	
	
}
