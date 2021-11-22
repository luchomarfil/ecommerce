package lmarfil.ecommerce.model.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AgregarProductoDTO {
	
	@NotNull
	Long idCarrito;
	
	@NotNull
	Long dni;
	
	@NotEmpty
	String nombre;
	
	@NotNull
	@Min(value = 1)
	Integer cantidad;
}
