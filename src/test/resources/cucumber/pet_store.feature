@PETREST
Feature: PET REST PROJECT

@GET
Scenario Outline: Get a PET
	Given An Pet with ID equals to <id>
	When I send a Get Request
	Then the response return the status code <status>
	Examples:
	
    | 	id 		| status	| 
    | 	0 	| 404 		|
    
@POST
Scenario Outline: Add a PET
	Given Add a pet with name <name> and <tags>
	When I send a POST Request
	Then the response return the status code <status>
	And Verify with a Get Request to data is correct
	Examples:
	
    | 	name 					| tags		| status	| 
    | 	gatopardo 		| gato		| 200 		|
    | 	tigre	 				| salvaje | 200 		|   
    
@PUT
Scenario Outline: update a PET
	Given Add a pet with name <name> and <tags>
	When I send a POST Request
	Then the response return the status code <status>
	And Verify with a Get Request to data is correct
	When I Modify the pet name with <updateName> and remove the tags
	When I send  a PUT Request
	Then the response return the status code <status>
	And Verify with a Get Request to data is correct
	Examples:
	
    | 	name 					| tags		| status	| updateName |
    | 	gatopardo 		| gato		| 200 		| gatito		 |
    | 	tigre	 				| salvaje | 200 		| tigreton	 |
    
@DELETE
Scenario Outline: Delete a PET
	Given Add a pet with name <name> and <tags>
	When I send a POST Request
	Then the response return the status code <status>
	And Verify with a Get Request to data is correct
	When I send  a DELETE Request
	Then the response return the status code <status>
	When I send a Get Request
	Then the response return the status code <status2>
	Examples:
	
    | 	name 					| tags		| status	| status2 |
    | 	gatopardo 		| gato		| 200 		| 404			|
    | 	tigre	 				| salvaje | 200 		| 404			|