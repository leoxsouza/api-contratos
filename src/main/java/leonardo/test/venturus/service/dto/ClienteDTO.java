package leonardo.test.venturus.service.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	@CNPJ
	private String cnpj;
	private String nome;
}
