Feature: Register time in Activities 
Description: An Employee registers amount of time worked on an Activity
Actors: Employee

Scenario: The Employee registers time worked for an Activity
	Given there is a Project with id 211111
	And there is an Employee with initials "AAAA"
	And a WorkActivity with name "Activity" is assigned to the Project
	And the Employee is assigned to the Activity
	When the Employee registers 1.5 hours for the Activity
	Then time 1.5 hours is added to the WorkActivity for the Employee





