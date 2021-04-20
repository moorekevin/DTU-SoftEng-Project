package dtu.testSteps;

import dtu.projectManagementApp.App;
import dtu.projectManagementApp.Employee;

public class EmployeeHelper {
    private App app;
    private Employee employee;

    public EmployeeHelper(App app) {
        this.app = app;
    }

    public Employee getEmployee() {
        if (employee == null) {
            employee = exampleEmployee();
        }
        return employee;
    }

    public Employee exampleEmployee() {
    	employee = new Employee("AAAA");
    	return employee;
    }

    public void setEmployee(String initials) {
        employee = new Employee(initials);
    }
}
