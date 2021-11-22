package lmarfil.ecommerce.model.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class IdentificadorCarritoDTO {
	
	@NotNull
	Long dni;
	
	@NotNull
	Long idCarrito;

}
