package lmarfil.ecommerce.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "tipo")
@Data
abstract public class EstadoCompra {	

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;
	
	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date fechaInicio = new Date();
	
	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaFin;

	public abstract boolean estaFinalizada();
	public abstract boolean estaCancelada();
	public abstract boolean estaAbierta();	
	
	public abstract String getTipo();
}
