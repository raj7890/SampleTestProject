package com.functional.utilities;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DriverExtensions {

	private WebDriver instance; 
	private Logger log = Logger.getLogger(DriverExtensions.class.getName());

	private final long timeOutInSeconds = 40;

	public DriverExtensions(WebDriver instance)
	{
		this.instance = instance;
	}

	public WebElement waitUntilVisible(By locator)
	{
		try
		{
			WebDriverWait wait = new WebDriverWait(instance, timeOutInSeconds);
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

			return instance.findElement(locator);
		}
		catch(TimeoutException e)
		{
			throw new IllegalStateException("Element not found|visibe and Time out error is thrown: " + e.getMessage());
		}
		catch(WebDriverException e)
		{
			log.log(Level.SEVERE, "Element not found|visibe and Time out in locating the element: " + e.getMessage());
		}

		return null;
	}

	public WebElement waitUntilPresence(By locator)
	{
		try
		{
			WebDriverWait wait = new WebDriverWait(instance, timeOutInSeconds);
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			return instance.findElement(locator);
		}
		catch(TimeoutException e)
		{
			throw new IllegalStateException("Element not found|present and Time out error is thrown: " + e.getMessage());
		}
		catch(WebDriverException e)
		{
			log.log(Level.SEVERE, "Element not found|visibe and Time out in locating the element: " + e.getMessage());
		}
		return null;
	}

	public boolean waitUntilPageTitleFound(String title)
	{
		WebDriverWait wait = new WebDriverWait(instance, timeOutInSeconds);
		wait.until(ExpectedConditions.titleIs(title));
		return true;
	}

	public boolean waitUntilPageLoaded(String url)
	{
		try
		{
			WebDriverWait wait = new WebDriverWait(instance, timeOutInSeconds);
			wait.until(ExpectedConditions.urlToBe(url));
			return true;
		}
		catch(TimeoutException e)
		{
			throw new IllegalStateException("Page not found|loaded and Time out error is thrown: " + e.getMessage());
		}
		catch(WebDriverException e)
		{
			log.log(Level.SEVERE, "Page not found|loaded and exception thrown: " + e.getMessage());
		}
		
		return false;
	}
}