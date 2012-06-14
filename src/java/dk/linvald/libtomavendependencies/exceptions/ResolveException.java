package dk.linvald.libtomavendependencies.exceptions;

/**
 * @author Jesper Linvald (jesper@linvald.net)
 *
 */
public class ResolveException extends Exception {
	/**
	 * 
	 */
	public ResolveException() {
		super();
	}
	/**
	 * @param message
	 */
	public ResolveException(String message) {
		super(message);
	}
	/**
	 * @param message
	 * @param cause
	 */
	public ResolveException(String message, Throwable cause) {
		super(message, cause);
	}
	/**
	 * @param cause
	 */
	public ResolveException(Throwable cause) {
		super(cause);
	}
}
