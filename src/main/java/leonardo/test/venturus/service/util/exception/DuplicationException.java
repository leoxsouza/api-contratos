package leonardo.test.venturus.service.util.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DuplicationException extends RuntimeException {
	private String message;
	private Throwable cause;

	protected DuplicationException() {}

	public DuplicationException(String message) {
		this.message = message;
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
