package com.alten.bdd.steps;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class IMDBPetStoreSteps {

	
	private static final Logger LOGGER 					= LogManager.getLogger(IMDBPetStoreSteps.class);
	
	private static String PET_GET = "/pet/";
	
	
	private int currentIDPet = 0;
	private Response currentResponse = null;

	
	@Given("^An Pet with ID equals to (\\d+)$")
	public void an_Pet_with_ID_equals_to(int id) throws Throwable {
	   currentIDPet = id;
	}

	@When("^I send a Get Request$")
	public void i_send_a_Get_Request() throws Throwable {
	    LOGGER.info("send a GET Request");
		Response response = given().get(PET_GET+this.getCurrentIDPet());
		LOGGER.info("statusCode="+response.getStatusCode());
		this.setCurrentResponse(response);

		
	}

	@Then("^the response return the status code (\\d+)$")
	public void the_response_return_the_status_code(int status) throws Throwable {
		LOGGER.info("the_response_return_the_status_code="+status);
		assertEquals(status, this.getCurrentResponse().getStatusCode());
	}
	
	//currentResponse

	public int getCurrentIDPet() {
		return currentIDPet;
	}

	public void setCurrentIDPet(int currentIDPet) {
		this.currentIDPet = currentIDPet;
	}

	public Response getCurrentResponse() {
		return currentResponse;
	}

	public void setCurrentResponse(Response currentResponse) {
		this.currentResponse = currentResponse;
		Hooks.setLastResponse(currentResponse);
	}
	
}
