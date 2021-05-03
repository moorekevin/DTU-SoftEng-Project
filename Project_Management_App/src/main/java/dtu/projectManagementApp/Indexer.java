package dtu.projectManagementApp;

import java.util.List;
import java.util.ArrayList;
import dtu.exceptions.EmployeeNotFoundException;

public class Indexer {
    private List<Employee> employeeRepository;
	private List<Project> projectRepository;
	private List<String> weekRepository;

    public Indexer() {
        employeeRepository = new ArrayList<>();
		projectRepository = new ArrayList<>();
		weekRepository = new ArrayList<>();
    }
    
    public List<Project> getProjects() {
		return projectRepository;
	}
	public List<Employee> getEmployees() {
		return employeeRepository;
	}
	public List<String> getWeeks() {
		return weekRepository;
	}
	
    // Index methods
    public void addEmployee(String initials) throws Exception {
        try {
			findEmployee(initials);
			throw new Exception("Employee already exists");
		} catch (EmployeeNotFoundException e) {
			employeeRepository.add(new Employee(initials));
		}
    }

    public void removeEmployee(Employee em) throws Exception {
        employeeRepository.remove(findEmployee(em.getInitials()));
    }

    public Project createProject(String name) {
		Project projectCreated = new Project(name, Project.makeProjectId());
		projectRepository.add(projectCreated);
		return projectCreated;
	}

	public void deleteProject(Project project) throws Exception {
		projectRepository.remove(findProject(project.getId()));
	}
	
	 public String createWeek(String yearWeek) throws Exception {
			validateYearWeek(yearWeek);
			if(findWeek(yearWeek)==null) {
				weekRepository.add(yearWeek);
			}
			return yearWeek;
		}
    
    // Validations
	public void validateProjectManager(Employee employeeToCheck) throws Exception {
		if (!employeeToCheck.isProjectManger()) {
			throw new Exception("Employee is not project manager");
		}
	}

	public void validateProjectManager(Employee employeeToCheck, Project projectToCheck) throws Exception {
		validateProjectManager(employeeToCheck);
		if (projectToCheck.getProjectManager() != employeeToCheck) {
			throw new Exception("Project Manager is not assigned to the Project");
		}
	}
	public void validateEmployeeAssigned(Employee employeeToCheck, Project projectToCheck) throws Exception {
		System.out.println("1");
		if (!projectToCheck.getAssignedEmployees().contains(employeeToCheck)) {
			System.out.println("2");
			throw new Exception("Employee is not assigned to project");
		}
	}
    public void validateYearWeek(String yearWeek) throws Exception {
		DateServer date = new DateServer();
		int actualYear = date.getYear() % 100;
		int actualWeek = date.getWeek();

		// first two digits
		try {
			int year = Integer.parseInt((yearWeek).substring(0, 2));

			// last two digits
			char temp = yearWeek.charAt(0);
			int week;
			week = temp == 0 ? Integer.parseInt((yearWeek).substring(3, 4))
					: Integer.parseInt((yearWeek).substring(2, 4));

			if (year < actualYear || year == actualYear && week < actualWeek || week < 1 || week > 52) {
				throw new Exception("Activity start/end-YearWeek is invalid");
			}
		} catch (NumberFormatException e) {
			throw new Exception("Week should be numeric");
		} catch (StringIndexOutOfBoundsException e) {
			throw new Exception("Week should be on form yyww");
		}

	}
    
    public void validateWeekInterval(String start, String end) throws Exception {
		validateYearWeek(start);
		validateYearWeek(end);
		if (Integer.parseInt(start) > Integer.parseInt(end))
			throw new Exception("Start week cannot be larger than end week");
	}

	// Find methods
	public Project findProject(int id) throws Exception {
		for (Project p : projectRepository) {
			if (id == p.getId()) {
				return p;
			}
		}
		throw new Exception("Project does not exist");
	}
	public Employee findEmployee(String initials) throws Exception {
		for (Employee employee : employeeRepository) {
			if (employee.getInitials().equals(initials.toUpperCase())) {
				return employee;
			}
		}
		throw new EmployeeNotFoundException("Employee does not exist");
	}
	public WorkActivity findActivity(Project project, String name) throws Exception {
		for (WorkActivity activity : project.getActivities()) {
			if (activity.getName().equals(name)) {
				return activity;
			}
		}
		throw new Exception("Activity is not assigned to the project");
	}
//	public Activity findActivity(String name, List<Activity> activities) {
//		for(Activity activity: activities) {
//			if(activity.getName().equals(name)) {
//				return activity;
//			}
//		}
//		return null;
//	}

	
	public String findWeek(String yearWeek) throws Exception {
		for (String week : weekRepository) {
			if (week.equals(yearWeek)) {
				return week;
			}
		}
		return null;
	}
	
	public PlannedWeek findPlannedWeek(Employee em, String week) throws Exception {
		validateYearWeek(week);
		for (PlannedWeek plannedWeek : em.getPlannedWeeks()) {
			if (plannedWeek.getYearWeek().equals(week)) {
				return plannedWeek;
			}
		}
		return null;
	}
}