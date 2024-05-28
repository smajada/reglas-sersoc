package org.arteco.sersoc.dto;

import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arteco.sersoc.model.entities.NoutTipprs;
import org.springframework.format.annotation.DateTimeFormat;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReglaDTO {

	private Long con;

	@NotNull(message = "El campo 'dec' no puede estar vacío")
	@Size(min = 1, message = "El campo 'dec' debe tener al menos un carácter")
	private String dec;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "El campo 'datIni' no puede estar vacío")
	private Date datIni;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "El campo 'datFin' no puede estar vacío")
	private Date datFin;

	@NotNull(message = "El campo 'script' no puede estar vacío")
	@Size(min = 1, message = "El campo 'script' debe tener al menos un carácter")
	private String script;

	private List<NoutTipprs> allTipoPrestacion;

	private List<String> tipoPrestacionSelected;

}
