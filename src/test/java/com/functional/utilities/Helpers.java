package com.functional.utilities;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Helpers {

	private WebDriver driver;
	private DriverExtensions de;

	private Logger log = Logger.getLogger(Helpers.class.getName());

	public Helpers(WebDriver driver)
	{
		this.driver = driver;
		de = new DriverExtensions(driver);
	}

	// Method to return the web element based on a parent element and a child locator ..

	public WebElement findElement(By locator)
	{
		WebElement element = driver.findElement(locator);
		return element;
	}

	// Method to click the web element..
	public void click(By locator)
	{
		driver.findElement(locator).click();
	}

	// Method to set the text in the web element..
	public void setValue(By locator, String value)
	{
		driver.findElement(locator).sendKeys(value);
	}

	// Method to get the value from the web element based on locator..
	public String getText(By locator)
	{
		String value = driver.findElement(locator).getText();
		return value;
	}

	// Method to get the value from the web element based on web element..
	public String getText(WebElement element)
	{
		WebElement elem = element;
		String value = elem.getText();
		return value;
	}

	// Method to set the Time in milliseconds..
	public void waitForMilliSeconds(int milliSec) throws InterruptedException
	{
		Thread.sleep(milliSec);
	}

	// Method to set the Time in milliseconds..
	public void waitUntilElementVisible(By locator ,int sec) throws InterruptedException
	{
		//Thread.sleep(milliSec);
		WebDriverWait wait = new WebDriverWait(driver, sec);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	// Method to get the value from the web element input box based on locator..
	public String getTextValue(By locator)
	{
		String value = driver.findElement(locator).getAttribute("value");
		return value;
	}

	// Method to mouse over on the web element based on locator..
	public void mouseOver(By locator)
	{
		Actions actions = new Actions(driver);
		WebElement element = driver.findElement(locator);
		actions.moveToElement(element).build().perform();
	}

	public void mouseOverElement(By locator , int x , int y) {
		// TODO Auto-generated method stub
		Actions actions = new Actions(driver);
		WebElement element = driver.findElement(locator);
		actions.clickAndHold(element).moveByOffset(x,y).release().build().perform();
	}

	// Method to switch to iFrame web element based on locator..
	public void iFrame(By locator)
	{
		WebElement element =driver.findElement(locator);
		driver.switchTo().frame(element);
	}

	// Method to read the test data from the properties file..
	public Properties getTestData()
	{
		Properties prop = new Properties();

		try {	
			URL res = getClass().getClassLoader().getResource("functional/assets/testdata.properties");
			File f = new File(res.getFile());

			log.info("Loading Test Data file.........." + f.getAbsolutePath());
			prop.load(res.openStream());			
			log.info("Test Data file loaded..........");
		} catch (IOException e) {

			log.log(Level.SEVERE,"Error while loading the test data values into the test parameters!! " + e.getMessage());
			//	e.printStackTrace();
		}

		return prop;
	}

	// Method to check whether the we element is visible..
	public boolean isElementVisible(By locator)
	{
		boolean elemFound = false;
		WebElement elem = de.waitUntilVisible(locator);
		if(elem!=null)
			elemFound = true;

		return elemFound;
	}	

	// Method to navigate to the url in the web browser..
	public void openURL(String url)
	{		
		driver.navigate().to(url);
	}
	
	// Method to get the current URL of the web page..
	public String getCurrentURL()
	{
		String url = driver.getCurrentUrl();
		return url;
	}

	// Method to get the title of the current web page..
	public String getPageTitle()
	{
		String value = driver.getTitle();
		return value;
	}

	// Method to get the attribute 'class name' of the web element based on the locator..
	public String getClassName(By locator) {

		String value = driver.findElement(locator).getAttribute("class");
		return value;
	}

	// Method to get the attribute 'class name' of the web element based on the given web element..
	public String getClassName(WebElement element) {

		WebElement elem = element;
		String value = elem.getAttribute("class");
		return value;
	}

	// Method to get any attribute value of the web element based on the locator..
	public String getAttributeValue(By locator, String attr) {

		String value = driver.findElement(locator).getAttribute(attr);
		return value;
	}

	// Method to scroll down the web page..
	public void scrollDown() {

		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,250)", "");
	}

	// Method to get any attribute value of the web element based on the given web element..
	public String getAttributeValue(WebElement element, String attr) {

		WebElement elem = element;
		String value = elem.getAttribute(attr);
		return value;
	}

	// Method to get list of the web elements..
	public List<WebElement> findElements(By locator)
	{
		List<WebElement> elems = driver.findElements(locator);
		return elems;
	}

	// Method to set value by keyboard keys (Example: Hitting enter key).. 
	public void setValue(By locator, Keys key) {

		driver.findElement(locator).sendKeys(key);		
	}
}
