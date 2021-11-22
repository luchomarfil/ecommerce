package lmarfil.ecommerce.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
public class Compra {

	@Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;
	
	@ManyToOne
	@NotNull
	Cliente cliente;
	
	@OneToOne(cascade = CascadeType.ALL)
	@NotNull
	CarritoDeCompra carritoDeCompra;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@NotNull
	EstadoCompra estadoOrdenDeCompra;
	
	@OneToMany(cascade = CascadeType.ALL,   orphanRemoval = true, fetch = FetchType.LAZY)		
	@OrderBy(value = "id desc")
	private List<EstadoCompra> estadosAnteriores = new ArrayList<>();
	
	
	@OneToMany(cascade = CascadeType.ALL)
	List<PromocionAplicada> promocionesAplicadas;
	
	
	Double monto;
	
	Double montoSinDescuentos;

	public void eliminar() {
		this.getEstadoOrdenDeCompra().setFechaFin(new Date());
		this.estadosAnteriores.add(getEstadoOrdenDeCompra());
		this.setEstadoOrdenDeCompra(new EstadoCompraCancelada());
	}

	public void finalizar(Date fechaFinalizacion, List<PromocionAplicada> promocionesAplicadas) {
		//cambiamos el estado
		this.getEstadoOrdenDeCompra().setFechaFin(fechaFinalizacion);
		this.estadosAnteriores.add(getEstadoOrdenDeCompra());
		this.setEstadoOrdenDeCompra(new EstadoCompraFinalizada());		
		
		//calculamos el monto sin descuento
		this.setMontoSinDescuentos(this.getCarritoDeCompra()
									.getDetallesProductos()
									.stream()
									.map(d->d.getPrecio()*d.getCantidad())
									.reduce(0D, Double::sum));
		
		//aplicamos promociones
		
		this.setMonto(getMontoSinDescuentos()-(montoADescontarDePromociones(promocionesAplicadas)));
		
		//guardamos las promociones aplicadas
		this.promocionesAplicadas.addAll(promocionesAplicadas);
	}

	private Double montoADescontarDePromociones(List<PromocionAplicada> promocionesAplicadas2) {
		return promocionesAplicadas2.stream().map(e-> e.getDescuento()).reduce(0D,Double::sum);
	}
	
}
