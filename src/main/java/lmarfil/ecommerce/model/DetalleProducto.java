package lmarfil.ecommerce.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class DetalleProducto {
	

	@Id	
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;
	
	
	@NotNull	
	Double precio;
	
	@NotNull
	Integer cantidad;
	
	@ManyToOne
	Producto producto;
	
	public DetalleProducto(Producto producto, Integer cantidad) {
		this.producto = producto;
		this.cantidad = cantidad;
		this.precio   = producto.getPrecio();
	}
	
}
