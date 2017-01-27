package com.functional.teststeps;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features="resources/com/functional/features/Login.feature",
		plugin = {"pretty", "html:target/site/functional-html-report"},
		glue = "com.functional.teststeps")
		//tags = {"@smoke-functional"})

public class RunnerTest {}
