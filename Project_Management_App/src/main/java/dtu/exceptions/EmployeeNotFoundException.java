/*
	Made by Aryan Mirzazadeh - s204489
	This class throws exception when employee is not found
*/
package dtu.exceptions;

public class EmployeeNotFoundException extends Exception {
	private static final long serialVersionUID = -555636713019183032L;

	public EmployeeNotFoundException(String errorMessage) {
		super(errorMessage);
	}

}