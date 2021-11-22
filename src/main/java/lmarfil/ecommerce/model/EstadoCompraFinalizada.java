package lmarfil.ecommerce.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@DiscriminatorValue(value = "FINALIZADO")
@Data @EqualsAndHashCode(callSuper = true)
public class EstadoCompraFinalizada extends EstadoCompra{
	@Override
	public boolean estaFinalizada() {
		return true;
	}

	@Override
	public boolean estaCancelada() {
		return false;
	}

	@Override
	public boolean estaAbierta() {
		return false;
	}

	@Override
	public String getTipo() {
		return "Compra Finalizada";
	}	
	
}
