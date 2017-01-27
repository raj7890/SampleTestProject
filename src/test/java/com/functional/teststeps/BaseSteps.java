package com.functional.teststeps;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.functional.pageobjects.Gmail_Login;
import com.functional.utilities.DriverSettings;
import com.functional.utilities.Helpers;
import cucumber.api.Scenario;

public class BaseSteps extends DriverSettings{

	private WebDriver driver;
	private Helpers helper;
	private Gmail_Login gmaillogin;

	private Logger log = Logger.getLogger(BaseSteps.class.getName());

	@cucumber.api.java.Before
	public void setUp(Scenario s) throws IOException
	{	
		readConfig();
		initializeDriver();		
		driver = getDriver();
		log.info("Execution trigerred on the test scenario: " + s.getName());
	}

	// Method to instantiate the object for Helpers class..
	public Helpers getHelper()
	{
		helper = new Helpers(driver);
		return helper;
	}

	// Method to instantiate the object for gmail login page..
	public Gmail_Login getGmail_Login()
	{
		gmaillogin = new Gmail_Login(driver);
		return gmaillogin;
	}

	@cucumber.api.java.After
	public void tearDown(Scenario s)
	{
		if(s.isFailed()) {
			File screenshot = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
			try {
				log.info("-------------------------------------------------------------------");
				System.out.println("");
				System.out.println("********************** Attempting taking of screenshot **********************");
				byte[] srcFile = ((RemoteWebDriver)driver).getScreenshotAs(OutputType.BYTES);
				s.embed(srcFile, "image/png");
				FileUtils.copyFile(screenshot,new File(System.getProperty("user.dir") + File.separator + "target" + File.separator + "site" + File.separator + "test_failure_" + s.getName() + ".png" ));
				System.out.println("");
				System.out.println("********************* Finished embedding of screenshot *********************");
				System.out.println("");
				log.log(Level.SEVERE, "Test Failure for the scenario: " + s.getName());
				log.info("-------------------------------------------------------------------");

			} catch (IOException e) {
				log.log(Level.SEVERE, e.getMessage());
			}
		} else {
			log.info("-------------------------------------------------------------------");
			log.info("Test Passed for the scenario: " + s.getName());
			log.info("-------------------------------------------------------------------");
		}
		try {	

			Thread.sleep(500);
		} catch(InterruptedException e) {
			log.log(Level.SEVERE,"Error occured while stopping the BrowserMob server:");
		}

		try {			
			closeDriver();
			Thread.sleep(500);
		} catch(InterruptedException e) {
			log.log(Level.SEVERE,"Error occured while closing the Browser: " + getBrowserValue());
		}
		finally
		{			
			// To do any final steps at tear down..
		}
	}
}
