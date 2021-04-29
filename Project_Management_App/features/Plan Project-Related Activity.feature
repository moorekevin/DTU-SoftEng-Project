Feature: Plan Project-Related Activites
Description: The Project Manager divides the Project into Activities
Actors: Project Manager

Scenario: Add Activity to Project succesfully
 	Given a Project Manager with initials "AAAA" is assigned to a Project
	When the Project Manager creates a WorkActivity with name "Activity", start-week "9001" and end-week "9002"
	And sets the expected hours to 10.0 for the WorkActivity
	Then the WorkActivity is assigned to the Project
	And the expected hours is 10.0 for the WorkActivity

Scenario: Add Activities to Project when not assigned to the Project
	Given there is a Project with id 211111
	And a Project Manager with initials "AAAA" is not assigned to the Project
	When the Project Manager creates a WorkActivity with name "Activity", start-week "9001" and end-week "9002"
	And sets the expected hours to 10.0 for the WorkActivity
	Then the WorkActivity is not assigned to the Project 	
	And the error is thrown "Project Manager must be assigned to the Project"
	
Scenario: Add Activities to Project with invalid start-YearWeek and end-YearWeek
	Given a Project Manager with initials "AAAA" is assigned to a Project
	When the Project Manager creates a WorkActivity with name "Activity", start-week "1001" and end-week "1002"
	And sets the expected hours to 10.0 for the WorkActivity
	Then the WorkActivity is not assigned to the Project
	And the error is thrown "Activity start/end-YearWeek is invalid"







