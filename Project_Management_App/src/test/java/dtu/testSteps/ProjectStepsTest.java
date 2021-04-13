package dtu.testSteps;


import org.junit.runner.RunWith;

import dtu.projectManagementApp.App;
import dtu.projectManagementApp.Employee;
import dtu.projectManagementApp.EmployeeRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

/* Important: 
for Cucumber tests to be recognized by Maven, the class name has to have
either the word Test in the beginning or at the end. 
For example, the class name CucumberTests (Test with an s) will be ignored by Maven.
*/

@RunWith(Cucumber.class)
@CucumberOptions(plugin="summary"
		 ,features={"features"}
		 ,publish= false
		 )
public class ProjectSteps {
	App projectApp;
	EmployeeRepository employeeRepository;
	EmployeeHelper employeeHelper;

	public ProjectSteps(App projectApp, EmployeeRepository employeeRepository, EmployeeHelper employeeHelper) {
		this.projectApp = projectApp;
		this.employeeRepository = employeeRepository;
		this.employeeHelper = employeeHelper;
	}
	
	@Given("there is an Employee with initials {string}")
	public void there_is_an_employee_with_initials(String string) {
	 // employee = new Employee(string);
	}
		  
	@When("the Employee creates a Project with name {string}")
	public void the_employee_creates_a_project_with_name(String string) {
		
	}

	@Then("the Project is assigned to the list of Projects")
	public void the_project_is_assigned_to_the_list_of_projects() {
	  
	}

	@Then("the Project's first two digits in id is the last two digits in this year")
	public void the_project_s_first_two_digits_in_id_is_the_last_two_digits_in_this_year() {
	
	}

	@Then("the Project has not a Project Manager")
	public void the_project_has_not_a_project_manager() {
	   
	}

	@Given("there is a Project with id {int}")
	public void there_is_a_project_with_id(Integer int1) {
	   
	}

	@When("the Employee creates a new Project with id {int} 	Then the error is thrown {string}")
	public void the_employee_creates_a_new_project_with_id_then_the_error_is_thrown(Integer int1, String string) {
	  
	}
	
	
}
