package lmarfil.ecommerce.service;

import javax.validation.Valid;

import lmarfil.ecommerce.model.dto.AgregarProductoDTO;
import lmarfil.ecommerce.model.dto.CarritoDeCompraDTO;
import lmarfil.ecommerce.model.dto.CrearCarritoDTO;
import lmarfil.ecommerce.model.dto.IdentificadorCarritoDTO;
import lmarfil.ecommerce.model.dto.QuitarProductoDTO;

public interface CarritoDeCompraService {

	CarritoDeCompraDTO crearNuevoCarrito(@Valid CrearCarritoDTO crearCarrito);

	void eliminarCarrito(@Valid IdentificadorCarritoDTO eliminarCarrito);

	CarritoDeCompraDTO agregarProducto(@Valid AgregarProductoDTO producto);

	CarritoDeCompraDTO quitarProducto(@Valid QuitarProductoDTO producto);

	CarritoDeCompraDTO estadoCarrito(@Valid IdentificadorCarritoDTO identificadorCarrito);

	CarritoDeCompraDTO finalizarCarrito(@Valid IdentificadorCarritoDTO identificadorCarrito);

}
