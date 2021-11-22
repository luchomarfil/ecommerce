package lmarfil.ecommerce.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
public class Producto {
	
	@Id	
	String nombre;
	
	@NotNull	
	Double precio;
	
}
