package com.functional.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.logging.Level;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;




public class DriverSettings {

	// Web driver instance..
	private WebDriver driver;
	private DesiredCapabilities capability;	

	// Variables that maintains the test environment..
	private String browserValue, appURL, chromeDriverPath, ieDriverPath, nodeURL, platform;

	private Logger log = Logger.getLogger(DriverSettings.class.getName());

	// Method returns the actual driver instance..
	public WebDriver getDriver() {
		return driver;
	}
	// Method reading the configuration values from the properties.
	public Properties readConfig()
	{	
		log.info("Reading Configuration file..........");
		Properties prop = new Properties();

		try {		
			FileInputStream  input = new FileInputStream("D:\\SampleTestProject\\SampleTestProject\\resources\\com\\functional\\assets\\config.properties");
		
			prop.load(input);
			log.info("Properties file loaded..........");
		} catch (IOException e) {

			log.log(Level.SEVERE, "Error while loading the configuration values into the test environment parameters!! " + e.getMessage());
			//	e.printStackTrace();
		}
		catch (NullPointerException e) {

			log.log(Level.SEVERE,"Error while finding the configuration file!! " + e.getMessage());
			//	e.printStackTrace();
		}

		try {			

			String runRemotely = prop.getProperty("RunOnGrid");
			
			if(runRemotely.toUpperCase().equals("TRUE"))
				setNodeURL(prop.getProperty("NodeURL"));
			else
				setNodeURL(null);

			if (System.getProperty("test_env") != null) 	{
				log.log(Level.INFO,"TEST ENV Value: " + System.getProperty("test_env"));
				setAppURL(System.getProperty("test_env"));
			}
			else {				
				setAppURL(prop.getProperty("TestURL"));
			}			

			if (System.getProperty("test_browser") != null)	{
				log.log(Level.INFO,"TEST Browser Value: " + System.getProperty("test_browser"));
				setBrowserValue(System.getProperty("test_browser"));
			}
			else {
				setBrowserValue(prop.getProperty("TestBrowserName"));
			}			

			if (System.getProperty("test_platform") != null)	{
				log.log(Level.INFO,"test_platform Value: " + System.getProperty("test_platform"));
				setPlatform(System.getProperty("test_platform"));
			}
			else {
				setPlatform(prop.getProperty("TestPlatform"));
			}

		} catch (IOException e) {

			log.log(Level.SEVERE,"Error while setting the configuration values into the test environment parameters!! " + e.getMessage());
			// e.printStackTrace();
		}

		return prop;
	}
	
	
	// Method to initialize or set the capabilities..
	public void setCapabilities()
	{
		log.log(Level.INFO, "Browser Value while initiations" + getBrowserValue());
		

		// set the capability to the desired browser value..
		if(getBrowserValue().toUpperCase().equals("CHROME"))
		{
			try{

				// set the capability to chrome..
				capability = DesiredCapabilities.chrome();
				capability.setBrowserName(DesiredCapabilities.chrome().getBrowserName());
			}
			catch(WebDriverException e) {
				log.log(Level.SEVERE,
						"Tests not executed because of the incorrect 'Chrome' driver settings or browser unavailability. Please check before run - " 
								+ e.getMessage());
				throw new WebDriverException();
			}			
		}	

		else if(getBrowserValue().toUpperCase().equals("FIREFOX"))
		{
			try	{
				// set the capability to firefox..
				capability = DesiredCapabilities.firefox();
				capability.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
			}
			catch(WebDriverException e){
				log.log(Level.SEVERE,
						"Tests not executed because of the incorrect 'Firefox' driver settings or browser unavailability. Please check before run - " 
								+ e.getMessage());
				throw new WebDriverException();
			}			
		}			

		else if(getBrowserValue().toUpperCase().equals("INTERNETEXPLORER"))
		{
			try	{
				// set the capability to IE..
				capability = DesiredCapabilities.internetExplorer();
				capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				capability.setBrowserName(DesiredCapabilities.internetExplorer().getBrowserName());
			}
			catch(WebDriverException e){
				log.log(Level.SEVERE,
						"Tests not executed because of the incorrect 'IE' driver settings or browser unavailability. Please check before run - " 
								+ e.getMessage());
				throw new WebDriverException();
			}			
		}	

		else
		{	
			if(getBrowserValue().equals("")) {			
				try {			

					log.log(Level.WARNING,"No Browser specified. Default browser got invoked for the tests run - Firefox!!");
					setBrowserValue("firefox");

					// set the capability to the default browser - firefox..
					capability = DesiredCapabilities.firefox();
					capability.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
				} catch (WebDriverException e) {

					log.log(Level.SEVERE,
							"Tests not executed because of the incorrect 'Firefox' driver settings or browser unavailability. Please check before run - " 
									+ e.getMessage());
					throw new WebDriverException();
					//e.printStackTrace();
				}	
			}
			else	{
				log.log(Level.SEVERE,
						"Tests not executed because of the incorrect browser value given. Please provide a valid one!!");
				throw new WebDriverException();
			}

		}
		log.info("Desired Capability instance set to the browser value!");

		// Set capability to the desired platform..
		if(getPlatform().toUpperCase().startsWith("WIN"))		{
			// Set the platform to windows..
			capability.setPlatform(Platform.WINDOWS);
		}

		else if(getPlatform().toUpperCase().startsWith("MAC"))	{
			// Set the platform to windows..
			capability.setPlatform(Platform.MAC);
		}

		else if(getPlatform().toUpperCase().startsWith("LINUX"))	{
			// Set the platform to windows..
			capability.setPlatform(Platform.LINUX);
		}

		else	{
			// Set the platform..
			capability.setPlatform(Platform.ANY);
		}

		log.info("Desired Capability instance set to the platform value!");

		// Set the capability instance..
		setCapability(capability);
		log.info("Desired Capabilities are Set!!");
	}

	// Method for web driver instance..
	public void setWebDriver()
	{
		
		
		if(getBrowserValue().toUpperCase().equals("CHROME"))
		{
			try{
				setChromeDriverPath();
				System.setProperty("webdriver.chrome.driver", getChromeDriverPath());
//				driver = new ChromeDriver(capability);
				driver = new ChromeDriver();
			}
			catch(Exception e){
				log.info(
						"Tests not executed because of the incorrect 'Chrome' driver settings. Please check before run - " 
								+ e.getMessage());
			}			
		}		

		else if(getBrowserValue().toUpperCase().equals("FIREFOX"))
		{
			try{
				System.out.println("browser intialize");
				driver = new FirefoxDriver();
			}
			catch(Exception e){
				log.info(
						"Tests not executed because of the incorrect 'firefox' driver settings. Please check before run - " 
								+ e.getMessage());
			}			
		}			

		else if(getBrowserValue().toUpperCase().equals("INTERNETEXPLORER"))
		{
			try	{
				setIEDriverPath();
				System.setProperty("webdriver.ie.driver", getIEDriverPath());
				driver = new InternetExplorerDriver();
			}
			catch(Exception e){
				log.info(
						"Tests not executed because of the incorrect 'IE' driver settings. Please check before run - " 
								+ e.getMessage());
			}			
		}	

		else
		{			
			if(getBrowserValue().equals("")) {			
				try {			

					log.log(Level.WARNING,"No Browser specified. Default browser got invoked for the tests run - Firefox!!");
					setBrowserValue("firefox");

					// Initialize the default browser - firefox..
					driver = new FirefoxDriver();
				} catch (WebDriverException e) {

					log.log(Level.SEVERE,
							"Tests not executed because of the incorrect 'Firefox' driver settings or browser unavailability. Please check before run - " 
									+ e.getMessage());
					throw new WebDriverException();
					//e.printStackTrace();
				}	
			}
			else	{
				log.log(Level.SEVERE,
						"Tests not executed because of the incorrect browser value given. Please provide a valid one!!");
				throw new WebDriverException();
			}			
		}

		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);		
	}

	// Method to instantiate the driver instance..
	public void initializeDriver() throws MalformedURLException
	{	
		if(getNodeURL() == null)	{
			setWebDriver();
			log.info("WebDriver instance initialized!!");
		}	
		else	{
			setCapabilities();
			
			URL hubUrl = new URL(getNodeURL());
			driver = new RemoteWebDriver(hubUrl, getCapability());
			log.info("Remote WebDriver instance initialized!!");
		}
	}

	// Method to get the Test Platform value..
	public String getPlatform() {
		return platform;
	}

	// Method to set the Test Environment Browser..
	private void setPlatform(String platform) throws IOException {

		this.platform = platform;
	}

	// Method to get the Test URL..
	public String getAppURL() {
		return appURL;
	}

	// Method to set the Test Environment Browser..
	private void setAppURL(String appURL) throws IOException {

		this.appURL = appURL;
	}

	// Method to get the Node URL..
	public String getNodeURL() {
		return nodeURL;
	}	

	// Method to set the Test Environment Browser..
	private void setNodeURL(String nodeURL) throws IOException {

		this.nodeURL = nodeURL;
	}

	// Method to get the Chrome Driver Path..
	public String getChromeDriverPath() {
		return chromeDriverPath;
	}

	// Method to set the Chrome Driver path..
	private void setChromeDriverPath() throws IOException {

		File currDir = new File(".");
		String dirPath = currDir.getAbsolutePath();
		log.info("Dir Path1: " + dirPath);

		dirPath = dirPath.replace(".", "");
		log.info("Dir Path2: " + dirPath);

		if(System.getProperty("os.name").toUpperCase().contains("WIN"))
			chromeDriverPath = dirPath + "drivers" + File.separator + "chromedriver.exe";
		else
			chromeDriverPath = dirPath + "drivers" + File.separator + "chromedriver";
			
		log.info("chromeDriverPath: " + chromeDriverPath);
	}

	// Method to get the IE Driver Path..
	public String getIEDriverPath() {
		return ieDriverPath;
	}

	// Method to set the IE Driver path..
	private void setIEDriverPath() throws IOException {

		if(System.getProperty("os.name").toUpperCase().contains("WIN"))
		{
			File currDir = new File(".");
			String dirPath = currDir.getAbsolutePath();
			log.info("Dir Path1: " + dirPath);

			dirPath = dirPath.replace(".", "");
			log.info("Dir Path2: " + dirPath);

			ieDriverPath = dirPath + "drivers" + File.separator + "IEDriverServer.exe";	    
		}

		log.info("IEDriverPath: " + ieDriverPath);
	}

	// Method to get the Test Browser value..
	public String getBrowserValue() {
		return browserValue;
	}

	// Method to set the Test Browser value..
	public void setBrowserValue(String browserValue) {
		this.browserValue = browserValue;
	}

	public DesiredCapabilities getCapability() {
		return capability;
	}

	public void setCapability(DesiredCapabilities capability) {
		this.capability = capability;
	}

	// Method to close the browser driver instance..
	public void closeDriver()
	{		
		driver.close();
		driver.quit();
	}
}