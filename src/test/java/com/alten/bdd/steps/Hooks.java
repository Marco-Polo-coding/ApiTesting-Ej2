package com.alten.bdd.steps;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.alten.bdd.utils.RestUtils;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.ExtentReports;
import io.cucumber.java.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.response.Response;


public class Hooks {

	private static final Logger LOGGER = LogManager.getLogger(Hooks.class);
	private static Response lastResponse = null;
	private static ExtentReports extent;
    private static ExtentTest test;

    // Configuración del reporte
    static {
    	ExtentSparkReporter htmlReporter = new ExtentSparkReporter("target/extentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

	@Before
    public void setup(Scenario scenario) throws Exception{
		LOGGER.info("setup");
        RestUtils.setup();
        // Inicia el reporte para cada escenario
        test = extent.createTest(scenario.getName());
		Hooks.lastResponse = null;
    } 
	
    
    @After
    public void tearDown(Scenario scenario) throws IOException {
    	    		
		LOGGER.info("tearDown");
		if( lastResponse !=null){
			LOGGER.info("headers="+lastResponse.getHeaders());
			LOGGER.info("status code="+lastResponse.getStatusCode());
			LOGGER.info("body="+lastResponse.asPrettyString());
		}else{
			LOGGER.info("lastResponse is null - skip test");
			
		}
		//Reporter.addStepLog("headers="+Hooks.lastResponse.getHeaders());
		//Reporter.addStepLog("status code="+Hooks.lastResponse.getStatusCode());
		//Reporter.addStepLog("body="+Hooks.lastResponse.asPrettyString());
		if (lastResponse != null) {
			// Imprime detalles de la respuesta en los logs del reporte
			test.info("Response Headers: " + lastResponse.getHeaders());
			test.info("Status Code: " + lastResponse.getStatusCode());
			test.info("Response Body: " + lastResponse.asPrettyString());
	    }

		// Si el escenario falla, lo marca como un fallo en ExtentReports
		if (scenario.isFailed()) {
			test.fail("Test Failed");
		} else if ( lastResponse !=null) {
			test.pass("Test Passed");
		}else{
			LOGGER.info("lastResponse is null - skip test");
			test.skip("Test Failed (last response is Null)");
		}

		// Guarda el reporte
		extent.flush();  // Se guarda el reporte después de cada escenario

    }

	public static void setLastResponse(Response lastResponse) {
		Hooks.lastResponse = lastResponse;
	}


}
