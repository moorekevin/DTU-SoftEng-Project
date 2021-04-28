package dtu.testSteps;

import dtu.projectManagementApp.App;
import dtu.projectManagementApp.Employee;
import dtu.projectManagementApp.Project;

public class EmployeeHelper {
    private App app;
    private Employee employee;
    private Employee additionalEmployee;

    public EmployeeHelper(App app) {
        this.app = app;
    }

    public Employee getEmployee() throws Exception {
        if (employee == null) {
            employee = createEmployee("AAAA");
        }
        return employee;
    }
    
    public Employee getAdditionalEmployee() throws Exception {
        if (additionalEmployee == null) {
            additionalEmployee = createAdditionalEmployee("AAAA");
        }
        return additionalEmployee;
    }

	public Employee createEmployee(String name) throws Exception {
		app.addEmployee(name);
		employee = app.findEmployee(name);
        return employee;
	}
	
	//Nødt til at skelne fordi skal bruge begge seperate i senere test
	public Employee createAdditionalEmployee(String name) throws Exception {
		app.addEmployee(name);
		additionalEmployee = app.findEmployee(name);
        return additionalEmployee;
	}
	
	//Tilføjet fordi "given there is not an employee" - kaldte på createEmployee som tilføjer den til repository
	public Employee createNonExistingEmployee(String name) {
		employee = new Employee(name);
		return employee;
	}

    public void makeEmployeeProjectManager(Employee em) {
        em.setProjectManager(new Project("AAAA", 9));
    }
    
    public void setEmployee(Employee em) {
    	employee = em;
    }
}
