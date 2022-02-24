package com.runner.com;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = {"src/test/resources/TekArchAPI.feature"},
glue= {"com.steps.com"})

public class RunnerTekArch extends AbstractTestNGCucumberTests{

	
}
