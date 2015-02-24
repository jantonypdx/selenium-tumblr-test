package selenium_tumblr_test;

import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.google.common.base.Predicate;

/**
 * --------------------------
 * BasePage.java
 * --------------------------
 * (C)opyright 2015 by John Antony
 * 
 * This is a base class for Selenium web pages that both extends LoadableComponent 
 * and handles repetitive functions. This BasePage class has page URL and page title
 * properties that simplify any derived class load() and isLoaded() functions.
 *  
 * Other  variables and functions handle text input and button clicking that
 * need time to dynamically update the page. Alerts pop ups are handled, too.
 * -------------------------- 
 * Original Author: John Antony;
 * Changes:
 * --------
 * 19-Feb-2015 : Version 1 (JJA);
 */
public class BasePage<T extends BasePage<T>>  extends LoadableComponent<T> {

	private String pageURL;
	private String pageTitle;
	private int defaultClickTimeout = 15;
	private int defaultPageReloadTimeout = 15;
	private int pollInterval = 2;
	
	protected WebDriver driver = null;
	protected LoadableComponent<?> parent = null;
	
	// Overridden LoadableComponent function. Make sure that we're on the 
	// page that we expect to be on.
	@Override
	protected void isLoaded() throws Error {
		try{
            Assert.assertTrue("Not on correct page", getPageTitle().equals(driver.getTitle()));
        }catch(NoSuchElementException ex){
            throw new AssertionError();
        }		
	}

	// Overridden LoadableComponent function. First, tell the parent object
	// to load its page, then load our own page.
	@Override
	protected void load() {
		// if parent present, load it first
		if (parent != null)
			parent.get();
		
		// then load page URL
		driver.get( getPageURL() );
	}
	
	/*
	 * Getter/setter functions
	 */
	
	// pageURL property getter
	public String getPageURL() {
		return pageURL;
	}

	// pageURL property setter	
	public void setPageURL(String pageURL) {
		this.pageURL = pageURL;
	}

	// pageTitle property getter	
	public String getPageTitle() {
		return pageTitle;
	}

	// pageTitle property setter	
	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	// defaultSecondsToWait property getter 
	public int getDefaultClickTimeout() {
		return defaultClickTimeout;
	}
	
	// defaultSecondsToWait property setter
	public void setDefaultClickTimeout(int defaultSecondsToWait) {
		this.defaultClickTimeout = defaultSecondsToWait;
	}

	// defaultPageReloadTimeout property getter	
	public int getDefaultPageReloadTimeout() {
		return defaultPageReloadTimeout;
	}

	// defaultPageReloadTimeout property setter	
	public void setDefaultPageReloadTimeout(int defaultPageReloadTimeout) {
		this.defaultPageReloadTimeout = defaultPageReloadTimeout;
	}
	

	/*
	 * Text and Button utility functions
	 */
	
	/*
	 * Tumblr pages have dynamic elements that disappear after they are clicked.
	 * This function clicks the button, then waits for the element to disappear.
	 * It also handles any "are you sure?" type alert popup dialogs that appear.
	 */
	public void clickThenWaitForElementToDisappear(By elementBy) {
		clickThenWaitForElementToDisappear(elementBy, getDefaultClickTimeout());
	}

	// same as above only timeout seconds can be specifically set
	public void clickThenWaitForElementToDisappear(By elementBy, int seconds) {
		try {
			// find element & click it
			WebElement element = driver.findElement(elementBy);
			element.click();
			
			// now wait until element is no longer present or timeout
			try
	        {
				(new FluentWait(driver))
					.withTimeout(seconds, TimeUnit.SECONDS)
					.pollingEvery(pollInterval, TimeUnit.SECONDS)			
					.until(ExpectedConditions.not(ExpectedConditions
							.presenceOfElementLocated(elementBy)));
	        }
			catch (NoSuchElementException noSuchElementException) {

			}			
			catch (TimeoutException timeoutException) {
				
			}			
			
		// handle any alerts if they pop up
		} catch (org.openqa.selenium.UnhandledAlertException unhandledAlertException) {
			try {
				// try to switch to the alert dialog and accept it
				Alert alert = driver.switchTo().alert();
				alert.accept();
			} catch (org.openqa.selenium.NoAlertPresentException noAlertPresentException)
			{
				// alert may have gone away. if so, then ignore
			}
		}		
	}
	
	/*
	 * Tumblr pages have elements that reload after a button is pressed.
	 * This function clicks a button, then waits for the page to be reloaded.
	 * Reloading is verified by looking for a timestamp value in the page's
	 * source code. As soon as it sees a different timestamp, it knows that
	 * the page has been reloaded. This function also handles any "are you 
	 * sure?" type alert popup dialogs that appear.
	 */
	public void clickThenWaitForPageReload(By elementBy) {
		clickThenWaitForPageReload(elementBy, getDefaultPageReloadTimeout());
	}
	
	// same as above only timeout seconds can be specifically set	
	public void clickThenWaitForPageReload(By elementBy, int seconds) {
		
		try {

			// first get page source timestamp
			long timeStampValue1 = getPageSourceTimestamp();
			
			// find element & click it
			WebElement element = driver.findElement(elementBy);
			element.click();
			
			// now wait until element is no longer present or timeout
			try
	        {				
				(new FluentWait(driver))
					.withTimeout(seconds, TimeUnit.SECONDS)
					.pollingEvery(pollInterval, TimeUnit.SECONDS)
					.ignoring(java.util.NoSuchElementException.class)
					.until(	new Predicate<WebDriver>() {
						@Override
						public boolean apply(WebDriver driver) {							
							long timeStampValue2 = getPageSourceTimestamp();
							return timeStampValue1 != timeStampValue2;
						}
					}
					);
	        }
			catch (TimeoutException timeoutException) {

			}			
			
			
		// handle any alerts if they pop up
		} catch (org.openqa.selenium.UnhandledAlertException unhandledAlertException) {
			try {
				// try to switch to the alert dialog and accept it
				Alert alert = driver.switchTo().alert();
				alert.accept();
			} catch (org.openqa.selenium.NoAlertPresentException noAlertPresentException)
			{
				// alert may have gone away. if so, then ignore
			}
		}		
		
	}

	/*
	 * Tumblr pages have dynamic elements that appear only after elements are clicked.
	 * This function waits until a specific element is visible.
	 */
	public void waitForElementToBeVisible(By elementBy) {
		waitForElementToBeVisible(elementBy, getDefaultClickTimeout());
	}
	
	// same as above only timeout seconds can be specifically set
	public void waitForElementToBeVisible(By elementBy, int seconds) {
		WebElement element = (new WebDriverWait(driver, seconds))
				.until(ExpectedConditions.visibilityOf(driver.findElement(elementBy)));
	}

	/*
	 * Tumblr pages have dynamic elements that are clickable only after elements are clicked.
	 * This function waits until a specific element is clickable.
	 */
	public void waitForElementToBeClickable(By elementBy) {
		waitForElementToBeClickable(elementBy, getDefaultClickTimeout());
	}
	
	// same as above only timeout seconds can be specifically set	
	public void waitForElementToBeClickable(By elementBy, int seconds) {
		WebElement element = (new WebDriverWait(driver, seconds))
				.until(ExpectedConditions.elementToBeClickable(elementBy));		
	}	
	
	/* 
	 * function to find "start_timestamp" in web page'ssource code, such as this:
	 *	
	 *   Thoth polling
	 *   Tumblr.Thoth = new Tumblr.Thoth({
	 *      start_timestamp: '1234567890',
	 *      primary_tumblelog: 'yourblogname',
	 *      check_posts: true,
	 *      check_inbox: false,
	 *      toast_mode: 1,
	 *      token: '12a34567890123b45c67d890def12345'            });
	*/
	public long getPageSourceTimestamp()
	{
		// get page source code
		String pageSource = driver.getPageSource();
		
		// strip out all but the start_timestamp line
		String strippedLines = pageSource.replaceAll("(?m)^((?!start_timestamp).)*", "");
		String strippedLines2 = strippedLines.replaceAll("(?m)^[ \t]*\r?\n", "");

		// tokenize the timestamp line, get the timestamp value as a long, return it
		StringTokenizer st = new StringTokenizer(strippedLines2, ": '',");		
		st.nextToken();		
		long timeStampValue = Long.parseLong(st.nextToken());		
		return timeStampValue;	
	}
}
