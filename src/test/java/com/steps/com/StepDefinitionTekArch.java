package com.steps.com;

import com.test.constants.Endpoints;
import com.test.models.AddUserPOJO;
import com.test.models.LoginObjectPOJO;
import com.test.models.UserPOJO;
import com.test.utils.Utils;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;

public class StepDefinitionTekArch{
	
	    String baseURI=Utils.getConfigProperty("baseUrl");
	    Response res;
	    static String  tokenvalue;
	    static String idvalue;
		 static String useridvalue;
	    static String username=Utils.getConfigProperty("username");
		 static String password =Utils.getConfigProperty("password");
		 static String token;
		 static Header apiheader;
		 static String accountno=Utils.getConfigProperty("accountno");
			static 	String deptno =Utils.getConfigProperty("deptno");
			static	String salary =Utils.getConfigProperty("salary");
			static String pincode =Utils.getConfigProperty("pincode");
			AddUserPOJO obj = new AddUserPOJO();
		
		// Scenario: Login to TekArch
		@Given("The endpoint")
		public void theEndpoint()
		{
			RestAssured.baseURI =baseURI;
			System.out.println("Got base URI");
		}

		 
		 @When("I send post login method") 
		 public void postLogin()
		 {
			 LoginObjectPOJO ob = new LoginObjectPOJO();
				ob.setUsername(username);
				ob.setPassword(password);
				res = RestAssured.given().contentType("application/json").body(ob).when().post(Endpoints.LOGIN);
				System.out.println("posted login method");
		 }
		 
		 @Then("validate the status code")
		 public void validateLogin()
		 {
			 res.then().assertThat().statusCode(201);
			 System.out.println("Verified status code");
		 }
		
		 @And("get token")
		 public void getToken()
		 {
			 tokenvalue= res.jsonPath().get("[0].token");
			System.out.println("extracted token="+tokenvalue);
		 }

		 
		// Scenario: Create user on TekArch
		    @Given("The token")
		   //setToken();
		    public void setToken()
		    {
		    	System.out.println("Token value setting "+tokenvalue);
		    	apiheader=new Header("token",tokenvalue);
		    	System.out.println("Set the token value");
		    }
		    
		    @And("I set the create user data")
		    public void createUserData()
		    {
		    	
				obj.setAccountno(accountno);
				obj.setDepartmentno(deptno);
				obj.setSalary(salary);
				obj.setPincode(pincode);
				System.out.println("Created user data");
		    }
		    
		    @When("I send post create user method")
		    public void postCreateUser()
		    {
		    	res=RestAssured.given().header(apiheader).contentType("application/json")
		    			.body(obj)
		    			.when()
		    			.post(Endpoints.ADD_DATA);
		    	System.out.println("posted create method");
		    }
		    
		//used the validation of previous validate status method
		    
		    @And("validate the content type")
		    public void validateContentType()
		    {
		    	res.then().assertThat().contentType(ContentType.JSON);
			  	System.out.println("Verified content type");
		    }
		    
		  //  Scenario: Get user from TekArch  
		    //No need to write the given step her, because it is taking from the previous scenario as the step repeating
		    
		    @When("I get the create user data by get method")
		    public void getMethod()
		    {
		    	res=RestAssured.given().header(apiheader)
						.when()
						.get(Endpoints.GET_DATA);
		    	System.out.println("get method data executed");
		    }
		    @Then("validate the status code for get user")
		    public void validateGetUser()
		    {
		    	res.then().assertThat().statusCode(200);
				System.out.println("Asserted get method");
		    }
		  //used the validation of previous validate content type method
		    @And("get user data from response")
		    public void getCreatedUserData()
		    {
		    	UserPOJO[] listOfUsers=res.as(UserPOJO[].class);//deserilization
		    	System.out.println("Total no of records "+listOfUsers.length);
		    	String accno = listOfUsers[0].getAccountno();
		    	System.out.println("First account no "+accno);
		    	String depno = listOfUsers[0].getDepartmentno();
		    	System.out.println("First dep value "+depno);
		    	String salary = listOfUsers[0].getSalary();
		    	System.out.println("First salary "+salary);
		    	idvalue=listOfUsers[0].getId();
		    	System.out.println("First id value "+idvalue);
		    	useridvalue=listOfUsers[0].getUserid();
		    	System.out.println("First id uservalue "+useridvalue);
		    }
		    
}
