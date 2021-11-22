package lmarfil.ecommerce.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lmarfil.ecommerce.model.dto.ComprasDTO;
import lmarfil.ecommerce.model.dto.FiltroCompraDTO;
import lmarfil.ecommerce.service.ComprasService;
	


@RestController
@RequestMapping("/compras")
public class ComprasController {

	@Autowired
	ComprasService comprasService;
	
	@Operation(summary = "Compras realizadas")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Retorna las compras realizadas por el dni", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ComprasDTO.class)) }),
		@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,value= "/realizadas"
				)
	public List<ComprasDTO> getComprasRealizadas(@Valid FiltroCompraDTO filtro){
		return comprasService.getComprasRealizadas(filtro);
	}
}
