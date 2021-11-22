package lmarfil.ecommerce.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@DiscriminatorValue(value = "ABIERTA")
@Data @EqualsAndHashCode(callSuper = true)
public class EstadoCompraAbierta extends EstadoCompra{
	
	@Override
	public boolean estaFinalizada() {
		return false;
	}

	@Override
	public boolean estaCancelada() {
		return false;
	}

	@Override
	public boolean estaAbierta() {
		return true;
	}

	@Override
	public String getTipo() {
		return "Compra Abierta";
	}	
	
}
