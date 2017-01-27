package com.functional.identifiers;

import org.openqa.selenium.By;

public final class Locators {
	/*
	 * This is a final class and all the declarations of web element locators will be of final..	 * 
	 * Comment should be somehow this way - // WebElement Locators on the Page1..
	 * Example below for declaration -
	 * public final By ELEMENTTYPE_ELEMENTNAME = By.id("locator_value"); 
	 */
	
	// WebElement Locators on the Gmail page..
	public final By USERNAME = By.id("Email");
	public final By PASSWORD = By.id("Passwd");
	public final By LOGO = By.xpath("//h1");
	public final By BUTTON_NEXT = By.id("next");
	public final By BUTTON_SignIn = By.id("signIn");
	public final By ERROR_MESSAGE = By.id("errormsg_0_Passwd");
}