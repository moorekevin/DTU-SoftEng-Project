Feature: Delete Project

Scenario: Delete Project as Employee
	Given there is an Employee with initials "AAAA"
	And there is a Project with id 211111
  	When the Employee deletes the Project	
	Then a Project with id 211111 is not in the list of Projects

Scenario: Delete non-existing Project as Employee
	Given there is an Employee with initials "AAAA"
	And  there is a Project with id 111111
	And  there is not a Project with id 222222 in the list of Projects
	When the Employee deletes the Project
	Then a Project with id 211111 is not in the list of Projects
	And  the error is thrown "ERROR: Project does not exist"
