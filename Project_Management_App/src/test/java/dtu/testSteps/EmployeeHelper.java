package dtu.testSteps;

import dtu.projectManagementApp.App;
import dtu.dto.EmployeeInfo;
import dtu.projectManagementApp.Employee;

public class EmployeeHelper {
    private App app;
    private EmployeeInfo employee;

    public EmployeeHelper(App app) {
        this.app = app;
    }

    public EmployeeInfo getEmployee() {
        if (employee == null) {
            employee = exampleEmployee();
        }
        return employee;
    }

    public EmployeeInfo exampleEmployee() {
    	employee = new EmployeeInfo("AAAA");
    	return employee;
    }

    public void setEmployee(String initials) {
        employee = new EmployeeInfo(initials);
    }
}
