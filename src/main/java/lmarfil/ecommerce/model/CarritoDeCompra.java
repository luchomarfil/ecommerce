package lmarfil.ecommerce.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "tipo")
@DiscriminatorValue("NORMAL")
@Data
public class CarritoDeCompra {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	protected Long id;
	
	@OneToMany(cascade = CascadeType.ALL)
	protected List<DetalleProducto> detallesProductos = new ArrayList<DetalleProducto>();

	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	protected Date fechaCreacion = new Date();

	public void restarProducto(Producto producto, Integer cantidad) {
		//lo buscamos
		Optional<DetalleProducto> detalle = this.detallesProductos.stream().filter(e->e.getProducto().equals(producto)).findFirst();
		if(detalle.isPresent()) {
			var detalleActualizar = detalle.get();
			detalleActualizar.setCantidad(detalleActualizar.getCantidad()-cantidad);
			//si queda en 0 o negativo lo sacamos del carrito
			if(detalleActualizar.getCantidad()<=0) {
				this.detallesProductos.remove(detalleActualizar);
			}
		}
		
	}
	
	public void sumarProducto(Producto producto, Integer cantidad) {
		//lo buscamos
		Optional<DetalleProducto> detalle = this.detallesProductos.stream().filter(e->e.getProducto().getNombre().equals(producto.getNombre())).findFirst();
		//si no está lo creamos con cero
		DetalleProducto detalleParaActualizar = detalle.isEmpty()? agregarProductoEnLista(producto, 0) : detalle.get();		

		//le incrementamos la cantidad
		detalleParaActualizar.setCantidad(detalleParaActualizar.getCantidad()+cantidad);
	}

	private DetalleProducto agregarProductoEnLista(Producto producto, Integer cantidad) {
		DetalleProducto det = new DetalleProducto(producto, cantidad);
		this.detallesProductos.add(det);
		return det;
	}

	public Boolean esEspecial() {
		return false;
	}

	public boolean contieneProducto(Producto producto) {
		Optional<DetalleProducto> findFirst = this.getDetallesProductos()
				.stream()
				.filter(p->p.getProducto().getNombre()==producto.getNombre())
				.findFirst();
		
		return findFirst.isPresent();
	}
}
