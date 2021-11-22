package lmarfil.ecommerce.model.dto;

import lmarfil.ecommerce.model.DetalleProducto;
import lombok.Data;

@Data
public class DetalleProductoDTO {
	
	Integer cantidad;
	String nombre;
	Double precioUnidad;
	Double precioTotal;

	

	public static DetalleProductoDTO crear(DetalleProducto e) {
		DetalleProductoDTO d = new DetalleProductoDTO();
		d.setNombre(e.getProducto().getNombre());
		d.setCantidad(e.getCantidad());
		d.setPrecioUnidad(e.getPrecio());
		d.setPrecioTotal(e.getPrecio()*e.getCantidad());
		return d;
	}

}
