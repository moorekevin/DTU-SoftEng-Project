Feature: Assign Employee to Project
Description: The Project Manager assigns Employees to his/her own Project
Actors: Project Manager

Scenario: Assign an Employee to a Project
	Given a Project Manager with initials "AAAA" is Project Manager to a Project
	And there is an Employee with initials "BBBB"
	When the Project Manager assigns the Employee to the Project
	Then the Employee is assigned to the Project

Scenario: Assign a non-existing Employee to a Project
	Given a Project Manager with initials "AAAA" is Project Manager to a Project
	And there is not an Employee with initials "BBBB"
	When the Project Manager assigns the Employee to the Project
	Then the error is thrown "Employee(s) do not exist"

Scenario: Assign an Employee to a Project when Employee is not a Project Manager
	Given there is a Project with id 211111
	And there is an Employee with initials "AAAA"
	And the Employee is not a Project Manager
	When the Employee assigns the Employee to the Project
	Then the error is thrown "Employee is not project manager"
	And the Employee is not assigned to the Project
	