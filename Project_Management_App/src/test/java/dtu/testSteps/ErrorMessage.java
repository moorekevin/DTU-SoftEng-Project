/*
	Made by Jakob Jacobsen s204502
	This class is responsible for holding an error message 
	and used in the assertion to determine if the right error message has been thrown.
*/
package dtu.testSteps;

public class ErrorMessage {
	
	private String errorMessage = "";

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
