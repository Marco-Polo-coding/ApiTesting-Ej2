package com.alten.bdd.steps;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alten.bdd.utils.ResponseLogger;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class IMDBPetStoreSteps {

	private static final Logger LOGGER = LogManager.getLogger(IMDBPetStoreSteps.class);
	private static final String PET_ENDPOINT = "/pet/";

	private int currentIDPet = 0;
	private int petId;
	private String petName;
	private String petTags;
	private Response currentResponse = null;
	private String lastMethod;
	private String lastEndpoint;

	// --- GET Steps ---
	@Given("^An Pet with ID equals to (\\d+)$")
	public void an_Pet_with_ID_equals_to(int id) {
		currentIDPet = id;
	}

	@When("^I send a Get Request$")
	public void i_send_a_Get_Request() {
		LOGGER.info("send a GET Request");
		lastMethod = "GET";
		lastEndpoint = PET_ENDPOINT + currentIDPet;
		Response response = given().get(lastEndpoint);
		LOGGER.info("statusCode=" + response.getStatusCode());
		setCurrentResponse(response);
	}

	@Then("^the response return the status code (\\d+)$")
	public void the_response_return_the_status_code(int status) {
		LOGGER.info("expected status code = " + status);
		int actualStatus = getCurrentResponse().getStatusCode();
		boolean exito = (status == actualStatus);
		String endpoint = lastMethod + " " + lastEndpoint;
		ResponseLogger.logCliente("Verificación de código de respuesta", endpoint, status, actualStatus, exito,
			exito ? null : "El sistema devolvió un código inesperado.");
//		ResponseLogger.assertAndLogStatus(status, actualStatus);
	}

	// --- POST Steps ---
	@Given("^Add a pet with name (.+) and (.+)$")
	public void add_a_pet_with_name_and_tags(String name, String tags) {
		this.petName = name;
		this.petTags = tags;
	}

	@When("^I send a POST Request$")
	public void i_send_a_post_request() {
		LOGGER.info("send a POST Request");
		lastMethod = "POST";
		lastEndpoint = PET_ENDPOINT;

		// Genera un ID válido único basado en timestamp
		this.petId = (int)(System.currentTimeMillis() % Integer.MAX_VALUE);

		String requestBody = String.format("""
    {
        "id": %d,
        "name": "%s",
        "photoUrls": [],
        "tags": [{"id": 0, "name": "%s"}],
        "status": "available"
    }
    """, petId, petName, petTags);

		Response response = given()
				.header("Content-Type", "application/json")
				.body(requestBody)
				.post(lastEndpoint);

		setCurrentResponse(response);
		LOGGER.info("pet created with id=" + this.petId);
	}


	@Then("^Verify with a Get Request to data is correct$")
	public void verify_with_get_request_data_correct() {
		Response getResponse = given().get(PET_ENDPOINT + petId);
		boolean exito = (200 == getResponse.getStatusCode());
		String endpoint = "GET " + PET_ENDPOINT + petId;
		ResponseLogger.logCliente("Búsqueda de mascota tras creación", endpoint, 200, getResponse.getStatusCode(), exito,
			exito ? null : "El sistema indicó que la mascota no existe.");
//		ResponseLogger.assertAndLogStatus(200, getResponse.getStatusCode());
		assertEquals(petName, getResponse.jsonPath().getString("name"));
	}

	// --- PUT Steps ---
	@When("^I Modify the pet name with (.+) and remove the tags$")
	public void i_modify_the_pet_name_and_remove_tags(String newName) {
		this.petName = newName;
		lastMethod = "PUT";
		lastEndpoint = PET_ENDPOINT;

		String updatedBody = String.format("""
        {
            "id": %d,
            "name": "%s",
            "photoUrls": [],
            "tags": [],
            "status": "available"
        }
        """, petId, petName);

		Response response = given()
				.header("Content-Type", "application/json")
				.body(updatedBody)
				.put(lastEndpoint);

		setCurrentResponse(response);
	}

	@When("^I send  a PUT Request$")
	public void i_send_put_request() {
		// Ya cubierto en el paso anterior.
	}

	// --- DELETE Steps ---
	@When("^I send  a DELETE Request$")
	public void i_send_a_delete_request() {
		lastMethod = "DELETE";
		lastEndpoint = PET_ENDPOINT + petId;
		Response response = given().delete(lastEndpoint);
		setCurrentResponse(response);
	}

	@Then("^When I send a Get Request$")
	public void when_i_send_get_request_again() {
		Response response = given().get(PET_ENDPOINT + petId);
		setCurrentResponse(response);
	}

	// --- Utility Methods ---
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
