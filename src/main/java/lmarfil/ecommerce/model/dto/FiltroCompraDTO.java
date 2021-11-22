package lmarfil.ecommerce.model.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FiltroCompraDTO {
	
	@NotNull
	Long dni;
	
	@NotNull
	@DateTimeFormat(iso = ISO.DATE_TIME)
	Date from;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	Date to;
	
	@Pattern(regexp = "fecha|monto", flags = Pattern.Flag.CASE_INSENSITIVE)	
	String orden = "fecha";

	
}
