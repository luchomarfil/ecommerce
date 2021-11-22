package lmarfil.ecommerce.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import lmarfil.ecommerce.model.Compra;
import lmarfil.ecommerce.model.PromocionAplicada;
import lmarfil.ecommerce.model.dto.ComprasDTO;
import lmarfil.ecommerce.model.dto.FiltroCompraDTO;

public interface ComprasService {

	List<ComprasDTO> getComprasRealizadas(@Valid FiltroCompraDTO filtro);

	Compra save(Compra compra);

	Optional<Compra> findByClienteDniAndCarritoDeCompraId(Long dni, Long idCarrito);

	Compra finalizarCompra(Date time, List<PromocionAplicada> promocionesAplicadas, Compra compra);

}
