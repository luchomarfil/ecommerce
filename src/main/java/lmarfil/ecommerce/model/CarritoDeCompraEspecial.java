package lmarfil.ecommerce.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@DiscriminatorValue("ESPECIAL")
@Data @EqualsAndHashCode(callSuper = true)
public class CarritoDeCompraEspecial extends CarritoDeCompra{

	@Override
	public Boolean esEspecial() {
		return true;
	}
}
