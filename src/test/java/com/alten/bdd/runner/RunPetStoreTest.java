package com.alten.bdd.runner;

import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
		  features = "classpath:cucumber/pet_store.feature" ,
		  glue = {"com.alten.bdd.steps", "com.alten.bdd.utils"},
		  monochrome = true,
          plugin = {
        		  		"json:target/cucumber-json-report.json",
        		  		"html:target/cucumber-html-report"
        		  	}
)
public class RunPetStoreTest {
}
