package lmarfil.ecommerce.service;

import java.util.List;

import lmarfil.ecommerce.model.CarritoDeCompra;
import lmarfil.ecommerce.model.PromocionAplicada;
import lmarfil.ecommerce.model.dto.ComprasDTO;

public interface PromocionService {

	List<PromocionAplicada> tieneAlMenosCantidadDeProductos(Integer cantidadBase, Boolean esEspecial, Integer cantidadActual);

	List<PromocionAplicada> esClienteVip(List<ComprasDTO> comprasRealizadas, Double descuento, Double montoCompraBase);

	List<PromocionAplicada> descuentoPorProductosIguales(CarritoDeCompra carrito, int cantidadTotal, int cantidadCobrados);

}
