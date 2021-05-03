package dtu.exceptions;

public class WarningException extends Exception {
	private static final long serialVersionUID = -9036143203412286502L;

	public WarningException(String errorMessage) {
		super(errorMessage);
	}

}