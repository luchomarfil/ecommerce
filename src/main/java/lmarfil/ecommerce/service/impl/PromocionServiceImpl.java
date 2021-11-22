package lmarfil.ecommerce.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lmarfil.ecommerce.model.CarritoDeCompra;
import lmarfil.ecommerce.model.DetalleProducto;
import lmarfil.ecommerce.model.PromocionAplicada;
import lmarfil.ecommerce.model.dto.ComprasDTO;
import lmarfil.ecommerce.service.PromocionService;

@Service
public class PromocionServiceImpl implements PromocionService {

	public static List<PromocionAplicada> crearSimple(PromocionAplicada promo) {
		List<PromocionAplicada> promos = new ArrayList<>();
		promos.add(promo);
		return promos;
	}
	
	@Override
	public List<PromocionAplicada> tieneAlMenosCantidadDeProductos(Integer cantidadBase, Boolean esEspecial, Integer cantidadActual) {
		
		if(cantidadBase <= cantidadActual) {
			return PromocionServiceImpl.crearSimple(new PromocionAplicada(esEspecial? 150D: 100D, "Descuento por más de " + cantidadBase + " productos", "Descuento por tener " + cantidadActual + " de productos"));
		}
		return new ArrayList<>();

		
	}

	@Override
	public List<PromocionAplicada> esClienteVip(List<ComprasDTO> comprasRealizadas, Double descuento, Double montoCompraBase) {
		
		var montoComprado = comprasRealizadas
									.stream()
									.map(c -> c.getMonto())
									.reduce(0D, Double::sum);
		
		if(montoComprado>=montoCompraBase) {
			
			return PromocionServiceImpl.crearSimple(new PromocionAplicada(500D, "Descuento por ser VIP","Descuento por ser VIP"));
			
		}
		
		return new ArrayList<>();
	}

	@Override
	public List<PromocionAplicada> descuentoPorProductosIguales(CarritoDeCompra carrito, int cantidadTotal,	int cantidadCobrados) {
		List<List<PromocionAplicada>> resultado = carrito
													.getDetallesProductos()
													.stream()
													.filter(dt -> dt.getCantidad()  >= cantidadTotal)		
													.map(e->promocionesParaDetalle(e,cantidadTotal,cantidadCobrados, Integer.MAX_VALUE))
													.collect(Collectors.toList());
		
		List<PromocionAplicada> resultadoAplanado = resultado
													.stream()
													.flatMap(x -> x.stream())
													.collect(Collectors.toList());
		
		return resultadoAplanado;
	}

	private  List<PromocionAplicada> promocionesParaDetalle(DetalleProducto e, Integer cantidadTotal, Integer cantidadCobrados, Integer topePromociones) {
		Integer cantidadDePromos = (int) e.getCantidad() / (int) cantidadTotal;
		List<PromocionAplicada> promos = new ArrayList<PromocionAplicada>();
		for (int i = 1; i < cantidadDePromos; i++) {
			var promo = new PromocionAplicada(
					e.getPrecio()*cantidadCobrados,
					String.format("Descuento %sx%s en productos iguales",cantidadTotal,cantidadCobrados),
					String.format("Descuento %sx%s en producto %s",cantidadTotal,cantidadCobrados,e.getProducto().getNombre()));
			promos.add(promo);			
		}
		return promos;
	}



}
