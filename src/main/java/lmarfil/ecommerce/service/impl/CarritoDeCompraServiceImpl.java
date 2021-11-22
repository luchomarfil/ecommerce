package lmarfil.ecommerce.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lmarfil.ecommerce.model.CarritoDeCompra;
import lmarfil.ecommerce.model.CarritoDeCompraEspecial;
import lmarfil.ecommerce.model.Cliente;
import lmarfil.ecommerce.model.Compra;
import lmarfil.ecommerce.model.EstadoCompraAbierta;
import lmarfil.ecommerce.model.Producto;
import lmarfil.ecommerce.model.PromocionAplicada;
import lmarfil.ecommerce.model.dto.AgregarProductoDTO;
import lmarfil.ecommerce.model.dto.CarritoDeCompraDTO;
import lmarfil.ecommerce.model.dto.ComprasDTO;
import lmarfil.ecommerce.model.dto.CrearCarritoDTO;
import lmarfil.ecommerce.model.dto.DetalleProductoDTO;
import lmarfil.ecommerce.model.dto.FiltroCompraDTO;
import lmarfil.ecommerce.model.dto.IdentificadorCarritoDTO;
import lmarfil.ecommerce.model.dto.QuitarProductoDTO;
import lmarfil.ecommerce.repository.CarritoDeCompraRepository;
import lmarfil.ecommerce.repository.ProductoRepository;
import lmarfil.ecommerce.service.CarritoDeCompraService;
import lmarfil.ecommerce.service.ClienteService;
import lmarfil.ecommerce.service.ComprasService;
import lmarfil.ecommerce.service.PromocionService;

@Service
@Transactional
public class CarritoDeCompraServiceImpl implements CarritoDeCompraService{

	@Autowired
	EntityManager entityManager;
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	ComprasService comprasService;
	
	@Autowired
	CarritoDeCompraRepository carritoRepository;
	
	@Autowired
	ProductoRepository productoRepository;
	
	@Autowired
	PromocionService promocionService;
	
	private CarritoDeCompraDTO generarDTOCarrito(Compra compra, CarritoDeCompra carrito) {
		CarritoDeCompraDTO carritoDTO = new CarritoDeCompraDTO();
		carritoDTO.setId(carrito.getId());
		carritoDTO.setCliente(compra.getCliente().getDni());
		carritoDTO.setProductos(carrito.getDetallesProductos()
				.stream()
				.map(e->DetalleProductoDTO.crear(e))
				.collect(Collectors.toList()));
		carritoDTO.setEstado(compra.getEstadoOrdenDeCompra().getTipo());
		carritoDTO.setEsEspecial(carrito.esEspecial());
		return carritoDTO;
	}

	
	@Override
	public 	CarritoDeCompraDTO crearNuevoCarrito(@Valid CrearCarritoDTO crearCarrito){
		
		//si no existe el cliente lo creamos
		Cliente cliente = clienteService.findOrCreateById(crearCarrito.getDni());
		
		//creamos la orden y el carrito para el cliente;
		Compra compra = new Compra();
		CarritoDeCompra carrito = crearCarrito.getIsEspecial() ? new CarritoDeCompraEspecial() : new CarritoDeCompra();
		compra.setEstadoOrdenDeCompra(new EstadoCompraAbierta());
		compra.setCliente(cliente);
		compra.setCarritoDeCompra(carrito);
		
		entityManager.flush();
		comprasService.save(compra);
		
		//retornamos el dto del carrito creado que pertenece a una compra solicitada
		return generarDTOCarrito(compra, carrito);
		
	}


	@Override
	public void eliminarCarrito(@Valid IdentificadorCarritoDTO eliminarCarrito) {
		Long dni = eliminarCarrito.getDni();
		Long idCarrito = eliminarCarrito.getIdCarrito();
		
		//existe el cliente?
		Cliente cliente = clienteService.findById(dni).orElseThrow(()->new RuntimeException("No existe el cliente"));
		
		//existe el carrito?
		CarritoDeCompra carrito = carritoRepository.findById(idCarrito).orElseThrow(()-> new RuntimeException("No existe el carrito"));
		
		//esta relacionado el carrito con el cliente en una compra?
		Compra compra = comprasService.findByClienteDniAndCarritoDeCompraId(dni,idCarrito).orElseThrow(()-> new RuntimeException("El carrito no se encuentra asociado con el cliente"));
		
		//está la compra pendiente?
		if(!compra.getEstadoOrdenDeCompra().estaAbierta()) {
			throw new RuntimeException("La compra debe estar abierta");
		}
		
		//entonces se puede eliminar. (Marcamos con estado cancelado)
		compra.eliminar();
		entityManager.flush();
		
		comprasService.save(compra);
		
	}

	@Override
	public CarritoDeCompraDTO agregarProducto(@Valid AgregarProductoDTO agregarProductoDto) {
		//existe el cliente?
		Cliente cliente = clienteService.findById(agregarProductoDto.getDni()).orElseThrow(()->new RuntimeException("No existe el cliente"));
		
		//existe el carrito?
		CarritoDeCompra carrito = carritoRepository.findById(agregarProductoDto.getIdCarrito()).orElseThrow(()-> new RuntimeException("No existe el carrito"));
		
		//esta relacionado el carrito con el cliente en una compra?
		Compra compra = comprasService.findByClienteDniAndCarritoDeCompraId(agregarProductoDto.getDni(),agregarProductoDto.getIdCarrito()).orElseThrow(()-> new RuntimeException("El carrito no se encuentra asociado con el cliente"));
		
		//existe el producto?
		Producto producto = productoRepository.findById(agregarProductoDto.getNombre()).orElseThrow(()-> new RuntimeException("No existe el producto, use el /api/rest/productoes para darlo de alta"));
		
		//está la compra pendiente?
		if(!compra.getEstadoOrdenDeCompra().estaAbierta()) {
			throw new RuntimeException("La compra debe estar pendiente para agregar productos");
		}
		
		carrito.sumarProducto(producto,agregarProductoDto.getCantidad());				
		
		carritoRepository.save(carrito);
		
		return generarDTOCarrito(compra, carrito);
			
	}



	@Override
	public CarritoDeCompraDTO quitarProducto(@Valid QuitarProductoDTO quitarProductoDTO) {
		//existe el cliente?
		clienteService.findById(quitarProductoDTO.getDni()).orElseThrow(()->new RuntimeException("No existe el cliente"));
		
		//existe el carrito?
		CarritoDeCompra carrito = carritoRepository.findById(quitarProductoDTO.getIdCarrito()).orElseThrow(()-> new RuntimeException("No existe el carrito"));
		
		//esta relacionado el carrito con el cliente en una compra?
		Compra compra = comprasService.findByClienteDniAndCarritoDeCompraId(quitarProductoDTO.getDni(),quitarProductoDTO.getIdCarrito()).orElseThrow(()-> new RuntimeException("El carrito no se encuentra asociado con el cliente"));
		
		//existe el producto?
		Producto producto = productoRepository.findById(quitarProductoDTO.getNombre()).orElseThrow(()-> new RuntimeException("No existe el producto, use el /api/rest/productoes para darlo de alta"));
		
		//está la compra pendiente?
		if(!compra.getEstadoOrdenDeCompra().estaAbierta()) {
			throw new RuntimeException("La compra debe estar pendiente para agregar productos");
		}
		
		//existe el producto en el carrito?
		if(!carrito.contieneProducto(producto)) {
			throw new RuntimeException("El producto no se encuentra en el carrito");
		}
		carrito.restarProducto(producto,quitarProductoDTO.getCantidad());				
		
		carritoRepository.save(carrito);
		
		return generarDTOCarrito(compra, carrito);
	}


	@Override
	public CarritoDeCompraDTO estadoCarrito(@Valid IdentificadorCarritoDTO identificadorCarrito) {
		//existe el cliente?
		clienteService.findById(identificadorCarrito.getDni()).orElseThrow(()->new RuntimeException("No existe el cliente"));
		
		//existe el carrito?
		CarritoDeCompra carrito = carritoRepository.findById(identificadorCarrito.getIdCarrito()).orElseThrow(()-> new RuntimeException("No existe el carrito"));
		
		//esta relacionado el carrito con el cliente en una compra?
		Compra compra = comprasService.findByClienteDniAndCarritoDeCompraId(identificadorCarrito.getDni(),identificadorCarrito.getIdCarrito()).orElseThrow(()-> new RuntimeException("El carrito no se encuentra asociado con el cliente"));

		return generarDTOCarrito(compra, carrito);

	}


	@Override
	public CarritoDeCompraDTO finalizarCarrito(@Valid IdentificadorCarritoDTO identificadorCarrito) {
		//existe el cliente?
		clienteService.findById(identificadorCarrito.getDni()).orElseThrow(()->new RuntimeException("No existe el cliente"));
		
		//existe el carrito?
		CarritoDeCompra carrito = carritoRepository.findById(identificadorCarrito.getIdCarrito()).orElseThrow(()-> new RuntimeException("No existe el carrito"));
		
		//esta relacionado el carrito con el cliente en una compra?
		Compra compra = comprasService.findByClienteDniAndCarritoDeCompraId(identificadorCarrito.getDni(),identificadorCarrito.getIdCarrito()).orElseThrow(()-> new RuntimeException("El carrito no se encuentra asociado con el cliente"));

		//debe estar la compra pendiente
		if(!compra.getEstadoOrdenDeCompra().estaAbierta()) {
			throw new RuntimeException("La compra debe estar pendiente para finalizar el carrito");
		}
		
		//la fecha para la finalizacion del carrito
		Calendar calendarFechaFinalizacion = Calendar.getInstance();
		
		Calendar fechaFinalizacionMin = Calendar.getInstance(); 
		fechaFinalizacionMin.set(
			calendarFechaFinalizacion.get(Calendar.DAY_OF_MONTH),
			calendarFechaFinalizacion.get(Calendar.YEAR),
			calendarFechaFinalizacion.getActualMinimum(Calendar.DATE)
		);
		
		Calendar fechaFinalizacionMax = Calendar.getInstance();
		fechaFinalizacionMax.set(
			calendarFechaFinalizacion.get(Calendar.DAY_OF_MONTH),
			calendarFechaFinalizacion.get(Calendar.YEAR),
			calendarFechaFinalizacion.getActualMaximum(Calendar.DATE)
		);
		
		//recabamos info de promociones
		List<PromocionAplicada> promocionesAplicadas = new ArrayList<>();


		//compras realizadas				
		FiltroCompraDTO f = new FiltroCompraDTO();
		f.setDni(identificadorCarrito.getDni());
		f.setFrom(fechaFinalizacionMin.getTime());
		f.setTo  (fechaFinalizacionMax.getTime());
		List<ComprasDTO> comprasRealizadas = comprasService.getComprasRealizadas(f);
		
		promocionesAplicadas.addAll(promocionService.esClienteVip(comprasRealizadas,2000D,500D));		
		promocionesAplicadas.addAll(promocionService.descuentoPorProductosIguales(carrito,4,3));
		promocionesAplicadas.addAll(promocionService.tieneAlMenosCantidadDeProductos(4,carrito.esEspecial(),carrito.getDetallesProductos().size()));
		
		//ahora estamos en condiciones de cerrar el carrito
		compra = comprasService.finalizarCompra(calendarFechaFinalizacion.getTime(),promocionesAplicadas, compra);
		
		return generarDTOCarrito(compra, carrito);
	}


}
