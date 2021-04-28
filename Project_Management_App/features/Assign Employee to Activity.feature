Feature: Assign Employee to Activity
Description: The Project Manager assigns Employees to an Activity assigned to the Project
Actors: Project Manager

Scenario: Assign an Employee to a Activity
	Given a Project Manager with initials "AAAA" is assigned to a Project
	And a WorkActivity with name "Coding" is assigned to the Project
	And there is an Employee with initials "BBBB" assigned to the project
	When the Project Manager assigns the Employee to the Activity
	Then the Employee is assigned to the Activity

Scenario: Assign a non-existing Employee to an Activity
	Given a Project Manager with initials "AAAA" is assigned to a Project
	And a WorkActivity with name "Coding" is assigned to the Project
	And there is not an Employee with initials "BBBB" 
	When the Project Manager assigns the Employee to the Activity
	Then the error is thrown "Employee(s) do not exist"
	And the Employee with initials "BBBB" is not assigned to the Activity

Scenario: Assign an Employee to an Activity when the Employee is not assgned to the Project
	Given a Project Manager with initials "AAAA" is assigned to a Project
	And a WorkActivity with name "UI Fix" is assigned to the Project
	And there is an Employee with initials "BBBB"
	And the Employee is not assigned to the Project
	When the Project Manager assigns the Employee to the Activity
	Then the error is thrown "Employee is not assigned to the Project"
	And the Employee with initials "BBBB" is not assigned to the Activity

Scenario: Assign an Employee to an Activity when Project Manager is not assigned to the Project
	Given there is a Project with id 211111
	And a WorkActivity with name "Refractoring" is assigned to the Project
	And a Project Manager with initials "AAAA" is not assigned to the Project
	And there is an Employee with initials "BBBB" 
	When the Project Manager assigns the Employee to the Activity
	Then the error is thrown "Project Manager not assigned to Project"
	And the Employee with initials "BBBB" is not assigned to the Activity
	
