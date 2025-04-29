# IMDB PROJECT

IMDB Project Testing is a project to training on QA Automation using the following:
	Java
	JUnit
	RestAssured
	Cucumber
	Extent Report
_____________________________________


## Run All tests

`mvn clean test`


### Run Concrete error scenarios only

`mvn clean test -Dcucumber.options=' --tags @GET'`

##  Reporting

execution reports: target/cucumber-reports/report.html
