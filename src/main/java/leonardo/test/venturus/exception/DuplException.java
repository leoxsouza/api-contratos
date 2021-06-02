package leonardo.test.venturus.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplException extends Exception {

	public DuplException(String message) {
		super(String.format("Cadastro %s duplicado", message));
	}
}
