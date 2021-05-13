package leonardo.test.venturus.service.util.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionInterceptor extends ResponseEntityExceptionHandler {

	@ExceptionHandler(DuplicationException.class)
	public final ResponseEntity<Object> handleAllExceptions(DuplicationException ex) {
		DuplicationException exceptionResponse =
			new DuplicationException(ex.getMessage());
		return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> errorList = ex
			.getBindingResult()
			.getFieldErrors()
			.stream()
			.map(fieldError -> fieldError.getDefaultMessage())
			.collect(Collectors.toList());
		ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errorList);
		return handleExceptionInternal(ex, errorDetails, headers, errorDetails.getStatus(), request);
	}
}