package leonardo.test.venturus.service.dto;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContratoListDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private LocalDate vigencia;
	private String nomeCliente;
	private String nomeServico;
}
