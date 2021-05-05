Feature: Employee

Scenario: Add Employee to App succesfully
	Given there is not an Employee with initials "AAAA"
	When there is added an Employee with initials "AAAA"
	Then there is 1 Employee(s) with initials "AAAA" in the Employee repository
	
Scenario: Add Employee to App unsuccesfully
	Given there is an Employee with initials "AAAA"
	When there is added an Employee with initials "aaaa"
	Then the error is thrown "Employee already exists"
	And there is 1 Employee(s) with initials "AAAA" in the Employee repository

Scenario: Remove Employee from App succesfully
	Given there is an Employee with initials "AAAA"
	When an Employee with initials "AAAA" is removed from the app
	And there is 0 Employee(s) with initials "AAAA" in the Employee repository

Scenario: Remove Employee from App unsuccesfully
	Given there is not an Employee with initials "AAAA"
	When an Employee with initials "AAAA" is removed from the app
	Then the error is thrown "Employee does not exist"
	Then there is 0 Employee(s) with initials "AAAA" in the Employee repository