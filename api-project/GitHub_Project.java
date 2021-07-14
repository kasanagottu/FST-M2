package gitHub_RestAssured_Project;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

public class GitHub_Project {
	// Declare request specification variable
	RequestSpecification requestSpec;

	String ssjKey;
	int sshKeyId;

	@BeforeClass
	public void setup() {
		requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON)
				.addHeader("Authorization", "token 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")
				.setBaseUri("https://api.github.com").build();

	}

	@Test(priority = 1)
	public void POST_Method() { // Change ssh key every time : https://8gwifi.org/sshfunctions.jsp

		String reqbody = "{\"title\":\"TestAPIKey\",\"key\":\"ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQDQNUs2Ybg/3kJJRzKQV9mhVC15hfXb1evKfieDZIU6WHpcoTVwjp2yP5irwZ7BJdChdFq5W8pBXiydMrwDfbiIBIDyTP3q+XfQ2cIjXeX/DAImmvW2x1/R/hVQu9U/SHq5AFnqAiyY9nYLuJhsN2ilRPWYJGw0+gmCGiKr8gDZ74Lvgh20YHFBDUvb/s9C7Wy4FrLIhXwnljzi2T7pHBzJvkblpckFh+dJHSMOH7QGk8fRl2x3cYpFhaoayk8EJBNIUWNblMLnTOOCjXhHZ2pv5jIOzqGOAuZ/jhbiDFhoP6abu2Aab7Uu8KLcpnMZJKw4xLpOKHek5GnTnxGhhoTR\"}";

		Response response = given().spec(requestSpec).body(reqbody).when().post("/user/keys");

		System.out.println(response.getBody().asPrettyString());
		// Assertions
		response.then().statusCode(201);
		sshKeyId=response.then().extract().path("id");

	}

	@Test(priority = 2)
	public void Get_Method()

	{
		Response response = given().spec(requestSpec) // Set headers
				.when().get("/user/keys");

		System.out.println(response.getBody().asPrettyString());
		response.then().statusCode(200);
	}

	@Test(priority=3)
 public void Delete_Method()
  
  {
  Response response = given().spec(requestSpec) // Set headers
          .when()
       .pathParam("keyId",sshKeyId) // Set path parameter
          .delete("/user/keys/{keyId}"); 
  
  System.out.println(response.getBody().asPrettyString());
  	response.then().statusCode(204);
  }

}
