package lmarfil.ecommerce.model.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class EstadoCarritoDTO {

	@NotNull
	Long idCarrito;
	@NotNull
	Long dni;
	
}
