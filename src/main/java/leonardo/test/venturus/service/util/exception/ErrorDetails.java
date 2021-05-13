package leonardo.test.venturus.service.util.exception;

import java.util.Arrays;
import java.util.List;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorDetails {
	private HttpStatus status;
	private String message;
	private List<String> errors;

	public ErrorDetails(HttpStatus status, String message, List<String> errors) {
		super();
		this.status = status;
		this.message = message;
		this.errors = errors;
	}

	public ErrorDetails(HttpStatus status, String message, String error) {
		super();
		this.status = status;
		this.message = message;
		errors = Arrays.asList(error);
	}
}
