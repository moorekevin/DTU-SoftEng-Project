Feature: Assign Employee to WorkActivity
Description: The Project Manager assigns Employees to an Activity assigned to the Project
Actors: Project Manager

Background:
	Given a Project Manager with initials "AAAA" is assigned to a Project
	And a WorkActivity with name "Coding" is assigned to the Project
	And there is an Employee with initials "BBBB" assigned to the Project

Scenario: Assign an Employee to a WorkActivity
	When the Project Manager assigns the Employee to the WorkActivity
	Then the Employee is now assigned to the WorkActivity

Scenario: Assign a non-existing Employee to a WorkActivity
	Given there is not an Employee with initials "CCCC" 
	When the Project Manager assigns the Employee to the WorkActivity
	Then the error is thrown "Employee does not exist"
	And the Employee with initials "CCCC" is not assigned to the WorkActivity

Scenario: Assign an Employee to a WorkActivity that is not assigned to a Project
	Given there is a WorkActivity with name "UI Work" that is not assigned to the Project
	When the Project Manager assigns the Employee to the WorkActivity
	Then the error is thrown "Activity is not assigned to the project"
	And the Employee with initials "BBBB" is not assigned to the WorkActivity

Scenario: Assign an Employee to an WorkActivity when the Employee is not assigned to a Project
	Given there is an Employee with initials "CCCC"
	And the Employee is not assigned to the Project
	When the Project Manager assigns the Employee to the WorkActivity
	Then the error is thrown "Employee is not assigned to the Project"
	And the Employee with initials "CCCC" is not assigned to the WorkActivity

Scenario: Assign an Employee to an WorkActivity when Project Manager is not assigned to a Project
	Given a Project Manager with initials "DDDD" is not assigned to the Project
	When the Project Manager assigns the Employee to the WorkActivity
	Then the error is thrown "Project Manager is not assigned to the Project"
	And the Employee with initials "BBBB" is not assigned to the WorkActivity
	
Scenario: Remove an Employee from an WorkActivity
    Given the Employee is assigned to the WorkActivity
    When the Employee is removed from the WorkActivity
    Then the Employee is unassigned from the WorkActivity
    