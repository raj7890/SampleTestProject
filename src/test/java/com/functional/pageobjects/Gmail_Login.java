package com.functional.pageobjects;

import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;

import com.functional.identifiers.Locators;
import com.functional.utilities.Helpers;

public class Gmail_Login {

	protected WebDriver driver;	
	private Helpers helper;
	private Locators locatorObj = new Locators();
	private Logger log = Logger.getLogger(Gmail_Login.class.getName());

	public Gmail_Login(WebDriver driver)
	{
		this.driver = driver;
		helper = new Helpers(driver);
	}

	// Method to access proper URL..
	public void verifyURL(){

		// Get current URL..
		String URL = helper.getCurrentURL();

		// Verify the URL
		if(URL.equals("https://accounts.google.com/ServiceLogin#identifier"))
			log.info("User accessed proper URL");
	}

	// Method to verify the Google Logo..
	public void verifyGoogleLogo() throws InterruptedException{

		helper.waitUntilElementVisible(locatorObj.LOGO, 15);
		
		boolean isElemExists = helper.isElementVisible(locatorObj.LOGO);
		if(isElemExists)
			log.info("Google Logo displayed");
		
	}

	// Login Method..
	public void login() throws InterruptedException {

		helper.waitUntilElementVisible(locatorObj.LOGO, 15);

		boolean isElemExists = helper.isElementVisible(locatorObj.LOGO);
		if(isElemExists){

			// Enter User Name..
			helper.setValue(locatorObj.USERNAME, "raj543.cse");

			// Click next button..
			helper.click(locatorObj.BUTTON_NEXT);

			// Enter Password..
			helper.setValue(locatorObj.PASSWORD, "password");

			// Click SignIn button..
			helper.click(locatorObj.BUTTON_SignIn);

		}

	}

	// Method to verify logged in or not..
	public boolean isLoggedInOrNot(){

		String text = helper.getText(locatorObj.ERROR_MESSAGE);

		boolean isErrorMessage = text.contains("LoggedIn Successfully");

		return isErrorMessage;
	}

}