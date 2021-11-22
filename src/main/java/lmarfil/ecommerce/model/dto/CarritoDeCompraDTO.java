package lmarfil.ecommerce.model.dto;

import java.util.List;

import lombok.Data;

@Data
public class CarritoDeCompraDTO {
	
	Long id;
	Long cliente;
	public List<DetalleProductoDTO> productos;
	public String estado;
	Boolean esEspecial;

}
