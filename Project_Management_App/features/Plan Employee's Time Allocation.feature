Feature: Plan Employees' Time Allocation
Description: Project Manager can plan Employees' Time Allocation
Actors: Project Manager

Background:
	Given a Project Manager with initials "AAAA" is assigned to a Project

Scenario: The Project Manager plans an Employee's Time Allocation succesfully
	Given a WorkActivity with name "Activity", start-week "9001" and end-week "9003" is assigned to the Project
	And there is an Employee with initials "BBBB" assigned to the WorkActivity
	And the Employee has 0.0 total hours Planned Work for Week "9001"
	When the Project Manager allocates 10.0 hours for the Employee for the WorkActivity for Week "9001"
	Then the Employee has a total of 10.0 hours Planned Work for Week "9001"

