package lmarfil.ecommerce.model.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data	
public class CrearCarritoDTO {
	
	@NotNull
	Long dni;
	
	@NotNull
	Boolean isEspecial;
}
