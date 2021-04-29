Feature: Assign Employee to Activity
Description: The Project Manager assigns Employees to an Activity assigned to the Project
Actors: Project Manager

Background:
	Given a Project Manager with initials "AAAA" is assigned to a Project
	And a WorkActivity with name "Coding" is assigned to the Project

Scenario: Assign an Employee to a Activity
	Given there is an Employee with initials "BBBB" assigned to the project
	When the Project Manager assigns the Employee to the Activity
	Then the Employee is assigned to the Activity

Scenario: Assign a non-existing Employee to an Activity
	Given there is not an Employee with initials "BBBB" 
	When the Project Manager assigns the Employee to the Activity
	Then the error is thrown "ERROR: Employee(s) do not exist"
	And the Employee with initials "BBBB" is not assigned to the Activity

Scenario: Assign an Employee to an Activity when the Employee is not assigned to a Project
	Given there is an Employee with initials "BBBB"
	And the Employee is not assigned to the Project
	When the Project Manager assigns the Employee to the Activity
	Then the error is thrown "ERROR: Employee is not assigned to the Project"
	And the Employee with initials "BBBB" is not assigned to the Activity

Scenario: Assign an Employee to an Activity when Project Manager is not assigned to a Project
	Given a Project Manager with initials "BBBB" is not assigned to the Project
	And there is an Employee with initials "CCCC" 
	When the Project Manager assigns the Employee to the Activity
	Then the error is thrown "ERROR: Project Manager is not assigned to the Project"
	And the Employee with initials "CCCC" is not assigned to the Activity
	
