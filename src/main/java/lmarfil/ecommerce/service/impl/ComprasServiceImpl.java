package lmarfil.ecommerce.service.impl;

import static org.springframework.data.jpa.domain.Specification.where;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lmarfil.ecommerce.model.Compra;
import lmarfil.ecommerce.model.DetalleProducto;
import lmarfil.ecommerce.model.PromocionAplicada;
import lmarfil.ecommerce.model.dto.ComprasDTO;
import lmarfil.ecommerce.model.dto.DetalleProductoDTO;
import lmarfil.ecommerce.model.dto.FiltroCompraDTO;
import lmarfil.ecommerce.model.dto.PromocionDTO;
import lmarfil.ecommerce.repository.CompraRepository;
import lmarfil.ecommerce.repository.specs.CompraSpecs;
import lmarfil.ecommerce.service.ComprasService;

@Service
@Transactional
public class ComprasServiceImpl implements ComprasService {

	@Autowired
	CompraRepository compraRepository;
	
	@Autowired
	ObjectMapper mapper;
	
	@Autowired
	EntityManager entityManager;

	@Override
	public List<ComprasDTO> getComprasRealizadas(@Valid FiltroCompraDTO filtro) {
		
		Date desde = filtro.getFrom();
		Date hasta = filtro.getTo() != null ? filtro.getTo() : new Date();
				
		
		Specification<Compra> ordenarPorFecha = CompraSpecs.ordenarPorFecha();
		Specification<Compra> ordenarPorMonto = CompraSpecs.ordenarPorMonto();		
		Specification<Compra> orden = filtro.getOrden().equals("fecha") ? ordenarPorFecha : ordenarPorMonto;
		
		
		List<Compra> lista = compraRepository.findAll(
				  	 where(CompraSpecs.isFinalizada())
				  	 .and(CompraSpecs.isDeCliente(filtro.getDni()))
				  	 .and(CompraSpecs.isEntreFechas(desde,hasta))
				  	 .and(orden)
				  	 
		);
		
		return lista.stream()
			 .map( e-> generarCompraDTO(e))
			 .collect(Collectors.toList());
		
	}

	private ComprasDTO generarCompraDTO(Compra e) {
		ComprasDTO c = new ComprasDTO();
		c.setMonto(e.getMonto());
		c.setMontoSinDescuentos(e.getMontoSinDescuentos());
		c.setId(e.getId());
		c.setIdCarrito((e.getCarritoDeCompra().getId()));
		c.setPromociones(e.getPromocionesAplicadas().stream().map(promo->generarPromoDTO(promo)).collect(Collectors.toList()));
		c.setProductos(e.getCarritoDeCompra().getDetallesProductos().stream().map(prod->generarProductoDTO(prod)).collect(Collectors.toList())); 
		return c;
	}

	private DetalleProductoDTO generarProductoDTO(DetalleProducto prod) {
		return DetalleProductoDTO.crear(prod);
	}

	private PromocionDTO generarPromoDTO(PromocionAplicada promo) {
		PromocionDTO p = new PromocionDTO();
		p.setDescuento(promo.getDescuento());
		p.setResumen(promo.getResumen());
		return p;
	}

	@Override
	public Compra save(@Valid Compra compra) {		
		return compraRepository.save(compra);
	}

	@Override
	public Optional<Compra> findByClienteDniAndCarritoDeCompraId(Long dni, Long idCarrito) {
		return compraRepository.findByClienteDniAndCarritoDeCompraId(dni,idCarrito);
	}

	@Override
	public Compra finalizarCompra(Date fechaFinalizacion, List<PromocionAplicada> promocionesAplicadas, Compra compra) {
		compra.finalizar(fechaFinalizacion,promocionesAplicadas);		
		return this.save(compra);
	}

	
	
}
