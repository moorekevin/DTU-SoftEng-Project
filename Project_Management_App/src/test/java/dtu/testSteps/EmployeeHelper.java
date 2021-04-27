package dtu.testSteps;

import dtu.projectManagementApp.App;
import dtu.projectManagementApp.Employee;
import dtu.projectManagementApp.Project;

public class EmployeeHelper {
    private App app;
    private Employee employee;

    public EmployeeHelper(App app) {
        this.app = app;
    }

    public Employee getEmployee() throws Exception {
        if (employee == null) {
            employee = createEmployee("AAAA");
        }
        return employee;
    }

	public Employee createEmployee(String name) throws Exception {
		app.addEmployee(name);
		employee = app.findEmployee(name);
        return employee;
	}

    public void makeEmployeeProjectManager() {
        employee.setProjectManager(new Project("AAAA", 9));
    }
    
    public void setEmployee(Employee em) {
    	employee = em;
    }
}
