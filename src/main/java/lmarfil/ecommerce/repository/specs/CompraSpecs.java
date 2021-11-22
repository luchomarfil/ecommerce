package lmarfil.ecommerce.repository.specs;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import lmarfil.ecommerce.model.Cliente;
import lmarfil.ecommerce.model.Cliente_;
import lmarfil.ecommerce.model.Compra;
import lmarfil.ecommerce.model.Compra_;
import lmarfil.ecommerce.model.EstadoCompra;
import lmarfil.ecommerce.model.EstadoCompraFinalizada;
import lmarfil.ecommerce.model.EstadoCompra_;

@SuppressWarnings("serial")
public class CompraSpecs {

	
	public static Specification<Compra> isFinalizada() {
		return new Specification<Compra>() {
			public Predicate toPredicate(Root<Compra> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				Join<Compra, EstadoCompra> joinEstado = root.join(Compra_.estadoOrdenDeCompra);
				return builder.equal(joinEstado.type(),builder.literal(EstadoCompraFinalizada.class));  
			}
		};
	}

	public static Specification<Compra> isDeCliente(Long dni) {
		return new Specification<Compra>() {
			public Predicate toPredicate(Root<Compra> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				Path<Cliente> cliente = root.get(Compra_.cliente);
				return builder.equal(cliente.get(Cliente_.dni), dni);
			}
		};
	}

	public static Specification<Compra> isEntreFechas(Date desde, Date hasta) {
		return new Specification<Compra>() {
			public Predicate toPredicate(Root<Compra> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				Join<Compra, EstadoCompra> joinEstado = root.join(Compra_.estadoOrdenDeCompra);
				return builder.between(joinEstado.get(EstadoCompra_.fechaInicio), desde, hasta);
			}
		};
	}

	public static Specification<Compra> ordenarPorFecha() {
		return new Specification<Compra>() {
			public Predicate toPredicate(Root<Compra> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				Join<Compra, EstadoCompra> joinEstado = root.join(Compra_.estadoOrdenDeCompra);
				query.orderBy(builder.desc(joinEstado.get(EstadoCompra_.fechaInicio)));
				return builder.and(new Predicate[0]);				
			}
		};
	}

	public static Specification<Compra> ordenarPorMonto() {
		return new Specification<Compra>() {
			public Predicate toPredicate(Root<Compra> root, CriteriaQuery<?> query, CriteriaBuilder builder) {				
				query.orderBy(builder.desc(root.get(Compra_.monto)));
				return builder.and(new Predicate[0]);				
			}
		};
	}


}