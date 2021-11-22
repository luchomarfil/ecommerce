package lmarfil.ecommerce.model.dto;

import java.util.List;

import lombok.Data;

@Data
public class ComprasDTO {

	Double monto;
	Double montoSinDescuentos;
	Long id;
	Long idCarrito;
	List<PromocionDTO> promociones;
	List<DetalleProductoDTO> productos;
}
