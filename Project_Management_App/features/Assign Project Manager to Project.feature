Feature: Assign Project Manager to Project
Description: An Employee assigns a Project Manager to it
Actors: Employee

Scenario: Employee assigns themselves as Project Manager to a Project that does not have Project Manager
	Given there is a Project with id 211111
	And the Project does not have a Project Manager
	And there is an Employee with initials "AAAA"
	When the Employee assigns the Employee as the Project Manager to the Project
	Then the Project has the Project Manager with initials "AAAA"

Scenario: Remove Project Manager from a Project
	Given a Project Manager with initials "AAAA" is assigned to a Project
	When the Project Manager is removed from the Project
	Then the Project does not have a Project Manager

Scenario: Assign Project Manager to Project that already has Project Manager
	Given a Project Manager with initials "AAAA" is assigned to a Project
	And there is an Employee with initials "BBBB"
	When the Employee assigns the Employee as the Project Manager to the Project
	Then the error is thrown "Project already has a Project Manager"