/*
	Made by Aryan Mirzazadeh - s204489
	This class throws an exception for an illegal operation
*/
package dtu.exceptions;

public class OperationNotAllowedException extends Exception {
	private static final long serialVersionUID = 7414554606263978157L;

	public OperationNotAllowedException(String errorMessage) {
		super(errorMessage);
	}

}
