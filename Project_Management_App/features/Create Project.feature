Feature: Create Project

Scenario: Create Project as Employee
	Given there is an Employee with initials "AAAA"
	When the Employee creates a Project with name "A Smart Project"
	Then the Project is assigned to the list of Projects
	And the Project's first two digits in id is the last two digits in this year
	And the Project has not a Project Manager
	
Scenario: Create a Project with an ID that already exists
	Given there is an Employee with initials "AAAA"
	And there is a Project with id 211111
	When the Employee creates a new Project with id 211111
	Then the error is thrown "Project already exists"