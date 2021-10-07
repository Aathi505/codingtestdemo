package com.cucumber.runner;

import io.cucumber.testng.*;

@CucumberOptions(
		features= {"src/test/resources/features"},glue="tests",tags="")
public class CucumberTestRunner extends AbstractTestNGCucumberTests {

}
