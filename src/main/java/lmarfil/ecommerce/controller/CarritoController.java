package lmarfil.ecommerce.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lmarfil.ecommerce.model.dto.AgregarProductoDTO;
import lmarfil.ecommerce.model.dto.CarritoDeCompraDTO;
import lmarfil.ecommerce.model.dto.CrearCarritoDTO;
import lmarfil.ecommerce.model.dto.IdentificadorCarritoDTO;
import lmarfil.ecommerce.model.dto.EstadoCarritoDTO;
import lmarfil.ecommerce.model.dto.QuitarProductoDTO;
import lmarfil.ecommerce.service.CarritoDeCompraService;

@RestController
@RequestMapping("/carrito")
public class CarritoController {

	@Autowired
	CarritoDeCompraService carritoService;
	
	@GetMapping(value = "/ping",
				produces = MediaType.APPLICATION_JSON_VALUE
	)	
	public ResponseEntity<String> test() {
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
	
	@Operation(summary = "Estado del carrito")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
					     description = "El carrito de compras se muestra", 
						 content = @Content),
			@ApiResponse(responseCode = "400", description = "Si no se ha especificado correctamente", content = @Content) })	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CarritoDeCompraDTO> estadoCarrito(@Valid IdentificadorCarritoDTO identificadorCarrito){
		CarritoDeCompraDTO carrito = carritoService.estadoCarrito(identificadorCarrito);
		return ResponseEntity.ok(carrito);
	}
	
	@Operation(summary = "Crea un carrito para el cliente representado por el dni")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
					     description = "El carrito de compras se ha creado", 
						 content = @Content),
			@ApiResponse(responseCode = "400", description = "Si no se ha especificado correctamente", content = @Content) })	
	@PostMapping
	public ResponseEntity<CarritoDeCompraDTO> crearNuevoCarrito(@RequestBody @Valid CrearCarritoDTO crearCarrito){
		CarritoDeCompraDTO carrito = carritoService.crearNuevoCarrito(crearCarrito);
		return ResponseEntity.ok(carrito);
	}
	
	@Operation(summary = "Elimina un carrito para un cliente")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
					     description = "El carrito de compras se ha eliminado, la compra es cancelada", 
						 content = @Content),
			@ApiResponse(responseCode = "400", description = "Si no se ha especificado correctamente", content = @Content) })	
	@DeleteMapping
	public ResponseEntity<String> eliminarCarrito(@RequestBody @Valid IdentificadorCarritoDTO eliminarCarrito){
		carritoService.eliminarCarrito(eliminarCarrito);
		return ResponseEntity.ok("deleted");
	}
	
	@Operation(summary = "Agrega una cantidad de producto al carrito")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
					     description = "El producto ha aumentado cantidad en el carrito", 
						 content = @Content),
			@ApiResponse(responseCode = "400", description = "Si no se ha especificado correctamente", content = @Content) })	
	@PostMapping(value="/agregarProducto")
	public ResponseEntity<CarritoDeCompraDTO> agregarProducto(@RequestBody @Valid AgregarProductoDTO producto){
		CarritoDeCompraDTO carrito = carritoService.agregarProducto(producto);
		return ResponseEntity.ok(carrito);
	}
	
	@Operation(summary = "Quita una cantidad de producto al carrito")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
					     description = "El producto ha disminuido en cantidad del carrito", 
						 content = @Content),
			@ApiResponse(responseCode = "400", description = "Si no se ha especificado correctamente", content = @Content) })	
	@PostMapping(value="/quitarProducto")
	public ResponseEntity<CarritoDeCompraDTO> quitarProducto(@RequestBody @Valid QuitarProductoDTO producto){
		CarritoDeCompraDTO carrito = carritoService.quitarProducto(producto);
		return ResponseEntity.ok(carrito);
	}
	
	@Operation(summary = "Finalizar carrito")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
					     description = "El carrito se ha cerrado", 
						 content = @Content),
			@ApiResponse(responseCode = "400", description = "Si no se ha especificado correctamente", content = @Content) })	
	@PostMapping(value="/finalizarCarrito")
	public ResponseEntity<CarritoDeCompraDTO> finalizarCarrito(@RequestBody @Valid IdentificadorCarritoDTO identificadorCarrito){
		CarritoDeCompraDTO carrito = carritoService.finalizarCarrito(identificadorCarrito);
		return ResponseEntity.ok(carrito);
	}
}
