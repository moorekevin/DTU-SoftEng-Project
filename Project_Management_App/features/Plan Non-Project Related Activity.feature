Feature: Plan Non-Project Related Activity
Description: The Employee plans Non-Project related Activities
Actors: Employee

Scenario: Employee takes time off succesfully
	Given there is an Employee with initials "AAAA"
	And the Employee has 30.0 total hours Planned Work for Week "9002"
	When the Employee assigns 1 days for the NonWorkActivity "Sickness" for Week "9002"
	Then the Employee has a total of 40.0 hours Planned Work for Week "9002"	

Scenario: Employee takes time off unsuccesfully
	Given there is an Employee with initials "AAAA"
	And the Employee has 40.0 total hours Planned Work for Week "9002"
	When the Employee assigns 3 days for the NonWorkActivity "Sickness" for Week "9002"
	Then the error is thrown "WARNING: The total planned work exceeds 50 hours for the week. Please contact a Project Manager"

Scenario: Employee takes more time off for multiple weeks succesfully
	Given there is an Employee with initials "AAAA"
	And the Employee has 0.0 total hours Planned Work for Week "9002"
	And the Employee has 0.0 total hours Planned Work for Week "9003"
	When the Employee assigns 5 days for the NonWorkActivity "Holiday" for Week "9002"
	And the Employee assigns 5 days for the NonWorkActivity "Holiday" for Week "9003"
	Then the Employee has a total of 50.0 hours Planned Work for Week "9002"
	And the Employee has a total of 50.0 hours Planned Work for Week "9003"	

Scenario: Employee takes more time off for multiple weeks unsuccesfully
	Given there is an Employee with initials "AAAA"
	And the Employee has 0.0 total hours Planned Work for Week "9002"
	And the Employee has 30.0 total hours Planned Work for Week "9003"
	When the Employee assigns 5 days for the NonWorkActivity "Holiday" for Week "9002"
	And the Employee assigns 5 days for the NonWorkActivity "Holiday" for Week "9003"
	Then the error is thrown "WARNING: The total planned work exceeds 50 hours for the week. Please contact a Project Manager"
	And the Employee has a total of 50.0 hours Planned Work for Week "9002"
	And the Employee has a total of 30.0 hours Planned Work for Week "9003"	
	
	

