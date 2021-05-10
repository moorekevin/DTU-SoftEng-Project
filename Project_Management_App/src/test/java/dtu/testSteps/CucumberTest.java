/*
	Made by Kevin Moore s204462
	This class is needed to operate the cucumber tests.
*/
package dtu.testSteps;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(plugin="summary"
		 ,features={"features"}
		 ,publish= false
		 )
public class CucumberTest {

}
