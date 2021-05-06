Feature: Plan NonWorkActivity
Description: The Employee plans NonWorkActivities
Actors: Employee

Scenario: Employee takes time off succesfully
	Given there is an Employee with initials "AAAA"
	And the Employee has 30.0 total hours Planned Work for Week "9002"
	When the Employee assigns 1 days for the NonWorkActivity "Sickness" for Week "9002"
	Then the Employee has a total of 40.0 hours Planned Work for Week "9002"	

Scenario: Employee takes time off with warning
	Given there is an Employee with initials "AAAA"
	And the Employee has 40.0 total hours Planned Work for Week "9002"
	When the Employee assigns 3 days for the NonWorkActivity "Sickness" for Week "9002"
	Then the error is thrown "The total planned work exceeds allowed hours for the week. Time is allocated but please contact a Project Manager"

Scenario: Employee takes time off unsuccesfully
	Given there is an Employee with initials "AAAA"
	And the Employee has 0.0 total hours Planned Work for Week "9002"
	When the Employee assigns 8 days for the NonWorkActivity "Sickness" for Week "9002"
	Then the error is thrown "Invalid amount of days"

Scenario: Employee takes more time off for multiple weeks succesfully
	Given there is an Employee with initials "AAAA"
	And the Employee has 0.0 total hours Planned Work for Week "9002"
	And the Employee has 0.0 total hours Planned Work for Week "9003"
	When the Employee assigns 5 days for the NonWorkActivity "Holiday" for Week "9002"
	And the Employee assigns 5 days for the NonWorkActivity "Holiday" for Week "9003"
	Then the Employee has a total of 50.0 hours Planned Work for Week "9002"
	And the Employee has a total of 50.0 hours Planned Work for Week "9003"	

Scenario: Employee takes more time off for multiple weeks with warning
	Given there is an Employee with initials "AAAA"
	And the Employee has 0.0 total hours Planned Work for Week "9002"
	And the Employee has 30.0 total hours Planned Work for Week "9003"
	When the Employee assigns 5 days for the NonWorkActivity "Holiday" for Week "9002"
	And the Employee assigns 5 days for the NonWorkActivity "Holiday" for Week "9003"
	Then the error is thrown "The total planned work exceeds allowed hours for the week. Time is allocated but please contact a Project Manager"
	And the Employee has a total of 50.0 hours Planned Work for Week "9002"
	And the Employee has a total of 80.0 hours Planned Work for Week "9003"	
	
Scenario: Employee takes time off from a non existing NonWorkActivity
	Given there is an Employee with initials "AAAA"
	And the Employee has 0.0 total hours Planned Work for Week "9002"
	When the Employee assigns 1 days for the NonWorkActivity "Coding" for Week "9002"
	Then the error is thrown "Non-work Activity is not found in Planned Week"
	And the Employee has a total of 0.0 hours Planned Work for Week "9002"	
	
	
	

