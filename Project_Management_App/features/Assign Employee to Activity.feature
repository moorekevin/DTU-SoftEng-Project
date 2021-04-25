Feature: Assign Employee to Activity
Description: The Project Manager assigns Employees to an Activity assigned to the Project
Actors: Project Manager

Scenario: Assign an Employee to a Activity
	Given a Project Manager with initials "AAAA" is assigned to a Project
	And an WorkActivity with name "Coding" is assigned to the Project
	And there is an Employee with initials "BBBB" assigned to the project
	When the Project Manager assigns the Employee to the Activity
	Then the Employee is assigned to the Activity
	
