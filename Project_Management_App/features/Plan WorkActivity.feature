Feature: Plan WorkActivity
Description: The Project Manager divides the Project into Activities
Actors: Project Manager

Scenario: Add Activity to Project succesfully
 	Given a Project Manager with initials "AAAA" is assigned to a Project
	When the Project Manager creates a WorkActivity with name "Refractoring", start-week "9001" and end-week "9002" 
	And the Project Manager sets the expected hours to 10.0 for the WorkActivity
	Then the WorkActivity is assigned to the Project
	And the expected hours is 10.0 for the WorkActivity

Scenario: Add Activities to Project when not assigned to the Project
	Given there is a Project with id 211111
	And a Project Manager with initials "AAAA" is not assigned to the Project
	When the Project Manager creates a WorkActivity with name "Not Project Manager Activity", start-week "9001" and end-week "9002"
	Then the WorkActivity is not assigned to the Project 	
	And the error is thrown "Project Manager is not assigned to the Project"
	
Scenario: Add Activities to Project with invalid start-YearWeek and end-YearWeek
	Given a Project Manager with initials "AAAA" is assigned to a Project
	When the Project Manager creates a WorkActivity with name "Activity", start-week "0000" and end-week "0005"
	Then the WorkActivity is not assigned to the Project
	And the error is thrown "Week(s) are invalid"
	
	Scenario: Add Activities to Project with wrong format start-YearWeek and end-Yearweek
	Given a Project Manager with initials "AAAA" is assigned to a Project
	When the Project Manager creates a WorkActivity with name "Activity", start-week "today" and end-week "tomorrow"
	Then the WorkActivity is not assigned to the Project
	And the error is thrown "Week(s) are invalid"
	

Scenario: Edit Activity in a Project succesfully
	Given a Project Manager with initials "AAAA" is assigned to a Project
	And a WorkActivity with name "Activity", start-week "9001" and end-week "9002" is assigned to the Project
	And the WorkActivity's expected hours is set to 10.0 
	When the Project Manager edits the WorkActivity to name "Coding", start-week "9010" and end-week "9011"
	And the Project Manager sets the expected hours to 20.0 for the WorkActivity
	Then the WorkActivity has name "Coding", start-week "9010" and end-week "9011"
	And the expected hours is 20.0 for the WorkActivity

Scenario: Edit WorkActivity with invalid start-YearWeek interval
	Given a Project Manager with initials "AAAA" is assigned to a Project
	And a WorkActivity with name "Activity", start-week "9001" and end-week "9002" is assigned to the Project
	When the Project Manager edits the WorkActivity to name "Coding", start-week "9010" and end-week "9001"
	Then the WorkActivity has start-week "9001" and end-week "9002"
	And the error is thrown "Start week cannot be larger than end week"
	
Scenario: Duplicating a active WorkActivity in a Project
	Given a Project Manager with initials "AAAA" is assigned to a Project
	And a WorkActivity with name "UI Work" is assigned to the Project
	When the Project Manager creates a WorkActivity with name "UI Work", start-week "9001" and end-week "9002"
	Then the error is thrown "This Activity is already assigned to the Project"

Scenario: Set negative expected hours for a WorkActivity
 	Given a Project Manager with initials "AAAA" is assigned to a Project
 	And a WorkActivity with name "Activity", start-week "9001" and end-week "9002" is assigned to the Project
	And the WorkActivity's expected hours is set to 10.0 
	When the Project Manager sets the expected hours to -10.0 for the WorkActivity
	Then the error is thrown "Hours cannot be negative"
	And the expected hours is 10.0 for the WorkActivity




