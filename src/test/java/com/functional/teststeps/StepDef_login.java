package com.functional.teststeps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import junit.framework.Assert;


@SuppressWarnings("deprecation")
public class StepDef_login {

	private BaseSteps baseObj;
	private String appURL = null;
	
	public StepDef_login(BaseSteps baseObj)
	{
		this.setBaseObj(baseObj);	
	}

	public BaseSteps getBaseObj() {
		return baseObj;
	}

	public void setBaseObj(BaseSteps baseObj) {
		this.baseObj = baseObj;
	}
	

    @Given("^Launch URL \"([^\"]*)\"$")
    public void launch_url_something(String url) throws Throwable {
    	
    	appURL = url;
    }

	@When("^the user access the URL$")
	public void the_user_access_the_URL(){

		getBaseObj().getDriver().get(appURL);
	}
	
	@Then("^the user should be presented on webpage$")
	public void the_user_should_be_presented_on_webpage(){
		
		getBaseObj().getGmail_Login().verifyURL();
	}
	
	@Then("^the user should see Google logo$")
	public void the_user_should_see_Google_logo() throws InterruptedException{
		
		getBaseObj().getGmail_Login().verifyGoogleLogo();
	}

	@And("^the user enter invalid username and password$")
	public void the_user_enter_invalid_username_and_password() throws InterruptedException{

		// LoggingIn..
		getBaseObj().getGmail_Login().login();

	}

	@Then("^the user should be logged into application$")
	public void the_user_should_be_logged_into_application(){

		// Verify user should not loggedIn..
		boolean isNotLoggedIn = getBaseObj().getGmail_Login().isLoggedInOrNot();

		// Assert to validate not loggedIn..
		Assert.assertTrue("Expected user should not loggedIn", isNotLoggedIn);
	}

}