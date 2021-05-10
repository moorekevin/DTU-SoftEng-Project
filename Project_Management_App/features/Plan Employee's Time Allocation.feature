Feature: Plan Employees' Time Allocation
Description: Project Manager can plan Employees' Time Allocation
Actors: Project Manager

Background:
	Given a Project Manager with initials "AAAA" is assigned to a Project
	And a WorkActivity with name "Activity", start-week "9001" and end-week "9003" is assigned to the Project
	And there is an Employee with initials "BBBB" assigned to the WorkActivity

Scenario: The Project Manager plans an Employee's Time Allocation succesfully
	Given the Employee has 0.0 total hours Planned Work for Week "9001"
	When the Project Manager allocates 10.0 hours for the Employee for the WorkActivity for Week "9001"
	Then the Employee has a total of 10.0 hours Planned Work for Week "9001"

Scenario: The Project Manager plans an Employee's Time Allocation when the Employee has no hours available the week
	Given the Employee has 168.0 total hours Planned Work for Week "9001"
	When the Project Manager allocates 1.0 hours for the Employee for the WorkActivity for Week "9001"
	Then the Employee has a total of 168.0 hours Planned Work for Week "9001"
	And the error is thrown "Not enough available time for week"

Scenario: The Project Manager plans an Employee's Time Allocation for an Employee that is not working on same Project
	Given there is an Employee with initials "CCCC"
	And the Employee is not assigned to the Project
	And the Employee has 0 total hours Planned Work for Week "9002"
	When the Project Manager allocates 8.0 hours for the Employee for the WorkActivity for Week "9002"
	Then the Employee has a total of 0.0 hours Planned Work for Week "9002"
	And the error is thrown "Employee is not assigned to the Project"

Scenario: The Project Manager plans an Employee's Time Allocation for an Employee that is not assigned to the WorkActivity
	Given there is an Employee with initials "CCCC" assigned to the Project
	And the Employee is not assigned to the WorkActivity
	And the Employee has 0 total hours Planned Work for Week "9002"
	When the Project Manager allocates 8.0 hours for the Employee for the WorkActivity for Week "9002"
	Then the Employee has a total of 0.0 hours Planned Work for Week "9002"
	And the error is thrown "Employee is not assigned to the Activity"

Scenario: The Project Manager plans an Employee's Time Allocation for an Activity that has ended	
	When the Project Manager allocates 8.0 hours for the Employee for the WorkActivity for Week "9010"
	Then the error is thrown "Activity has not begun/ended for planned time"

Scenario: The Project Manager plans an Employee's Time Allocation for an Activity that has ended	
	Given the Employee has 0.0 total hours Planned Work for Week "9002"
	When the Project Manager allocates 8.0 hours for the Employee for the WorkActivity for Week "9002"
	And the Project Manager edits the WorkActivity to name "Coding", start-week "9003" and end-week "9005"
	Then the Employee has a total of 0.0 hours Planned Work for Week "9002"
	
	
