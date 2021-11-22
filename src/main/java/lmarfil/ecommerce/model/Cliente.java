package lmarfil.ecommerce.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
public class Cliente {

	@Id
	Long dni;
	
	@NotNull
	String nombre;
	
	@Email
	String email;
	
}
