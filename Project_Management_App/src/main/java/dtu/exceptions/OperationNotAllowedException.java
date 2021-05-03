package dtu.exceptions;

public class OperationNotAllowedException extends Exception {
	private static final long serialVersionUID = 7414554606263978157L;

	public OperationNotAllowedException(String errorMessage) {
		super(errorMessage);
	}

}
