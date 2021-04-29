Feature: Plan Employees' Time Allocation
Description: Project Manager can plan Employees' Time Allocation
Actors: Project Manager

Background:
	Given a Project Manager with initials "AAAA" is assigned to a Project

Scenario: The Project Manager plans an Employee's Time Allocation succesfully
	Given there is an Employee with initials "BBBB"
	And a WorkActivity with name "Activity", start-week "9001" and end-week "9003" is assigned to the Project
	And the Employee is assigned to the Activity
	And the Employee has 0.0 total hours Planned Work for Week "9001"
	When the Project Manager allocates 10.0 hours for the Employee for the WorkActivity for Week "9001"
	Then the Employee has a total of 10.0 hours Planned Work for Week "9001"

Scenario: The Project Manager views an Employee's Time Allocation succesfully
	Given there is an Employee with initials "BBBB"
	And a WorkActivity with name "Activity", start-week "9001" and end-week "9002" is assigned to the Project
	And the Employee is assigned only to that Activity
	And the Employee has 10.0 total hours Planned Work for Week "9001"
	When the Project Manager views the Employees Time Allocation for week "9001"
	Then the Time Allocation for the Employee shows the Employee has 10.0 total hours Planned Work for Week "9001"
	
