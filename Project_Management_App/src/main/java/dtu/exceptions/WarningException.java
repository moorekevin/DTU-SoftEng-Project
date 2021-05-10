
/*
	Made by Aryan Mirzazadeh - s204489
	This class i used for WarningExceptions, which we interpret as soft exceptions that are not necessarily illegal.
*/
package dtu.exceptions;

public class WarningException extends Exception {
	private static final long serialVersionUID = -9036143203412286502L;

	public WarningException(String errorMessage) {
		super(errorMessage);
	}

}