Feature: Create Project

Scenario: Create Project as Employee
	When a Project with name "A Smart Project" is created
	Then the Project is assigned to the list of Projects
	And the Project's first two digits in id is the last two digits in this year
	And the Project has not a Project Manager

