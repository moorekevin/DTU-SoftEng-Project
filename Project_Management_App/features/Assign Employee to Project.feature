Feature: Assign Employee to Project
Description: The Project Manager assigns Employees to his/her own Project
Actors: Project Manager

Scenario: Assign an Employee to a Project
	Given a Project Manager with initials "AAAA" is assigned to a Project
	And there is an Employee with initials "BBBB"
	When the Project Manager assigns the Employee to the Project
	Then the Employee is assigned to the Project

Scenario: Assign a non-existing Employee to a Project
	Given a Project Manager with initials "AAAA" is assigned to a Project
	And there is not an Employee with initials "BBBB"
	When the Project Manager assigns the Employee to the Project
	Then the error is thrown "Employee does not exist"

Scenario: Assign an Employee to a Project when Project Manager is not assigned to the Project
	Given there is a Project with id 211111
	And a Project Manager with initials "AAAA" is not assigned to the Project
	And there is an Employee with initials "BBBB"
	When the Project Manager assigns the Employee to the Project
	Then the error is thrown "Project Manager is not assigned to the Project"
	And the Employee with initials "BBBB" is not assigned to the Project