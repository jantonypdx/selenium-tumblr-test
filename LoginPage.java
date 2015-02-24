package selenium_tumblr_test;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

/**
 * --------------------------
 * LoginPage.java
 * --------------------------
 * (C)opyright 2015 by John Antony
 * 
 * LoginPage handles Tumblr's login and logout functionality.
 * -------------------------- 
 * Original Author: John Antony;
 * Changes:
 * --------
 * 24-Feb-2015 : Version 1 (JJA);
 */
public class LoginPage extends BasePage<LoginPage> {
	
	private static final String LOGIN_PAGE_URL = "https://www.tumblr.com/login";
	private static final String LOGIN_PAGE_TITLE = "Tumblr";
	
	private static final String EXIT_URL = "https://www.tumblr.com/logout";

	private String username;
    private String password;
	
	/**
	 * login page text fields and buttons 
	 */
	@FindBy(id="signup_email")
	@CacheLookup
	private WebElement emailInput;
	private By byEmail = By.id("signup_email");
	
	@FindBy(id="signup_password")
	@CacheLookup
	private WebElement passwordInput;
	private By byPassword = By.id("signup_password");
	
	@FindBy(className="login_btn")
	@CacheLookup
	private WebElement loginButton;
	private By byLogin = By.className("login_btn");
	
	
	public LoginPage(WebDriver driver, LoadableComponent<?> parent, String username, String password) {

		// first, make sure you save the base class driver and parent values
		this.driver = driver;
		this.parent = parent;

		// set page specific URL and title
		setPageURL(LOGIN_PAGE_URL);
		setPageTitle(LOGIN_PAGE_TITLE);
		
		// save your other variables
		this.username = username;
		this.password = password;
		
		// lastly, initialize your FindBy WebElement variables		
		PageFactory.initElements(driver, this);			
	}
	
/*
 *  We don't need to override LoadableComponent.isLoaded() because it's already 
 *  implemented the way we want it in the BasePage class.	
	@Override
    protected void isLoaded() throws Error { }
*/
	
	// We do want to override LoadableComponent.load() because we want the page to log 
	// the user in after it loads. Note the call super.load(). This calls the 
	// BasePage.load() function first.
    @Override
    protected void load() {
    	super.load(); // call BasePage.load();
        
		emailInput.click();
		emailInput.sendKeys(username);
		//sendKeysAndWait(emailInput, username);
		
		passwordInput.click();
		passwordInput.sendKeys(password);
		//sendKeysAndWait(passwordInput, password);

		clickThenWaitForElementToDisappear(byLogin);
    }	

	public void logout() {
		
		// goto logout page
		driver.get(EXIT_URL);

		// try 3 times to close the page. auto-close any unhandled alerts. 
		boolean bExceptionCaught = false;
		
		for (int i = 0; i<4; i++)
		{
			try {
				
				bExceptionCaught = false;
				driver.close();
				
				} catch (org.openqa.selenium.UnhandledAlertException unhandledAlertException) {
				
					bExceptionCaught = true;
					//System.out.println("caught UAE: " + uaException.getMessage());
					
					try {
						// try to switch to the alert dialog and accept it
						Alert alert = driver.switchTo().alert();
						alert.accept();
					} catch (org.openqa.selenium.NoAlertPresentException noAlertPresentException)
					{
						// alert may have gone away. if so, then ignore
					}
				}

			// if no exception caught, then driver closed cleanly. if so, then quit loop
			if (!bExceptionCaught)
				break;			
		}
		
/*		
 * 		Instead of jumping to EXIT_URL in first step, you could click account logout buttons.
 
		// click account button
		System.out.println("click user settings");
		WebElement accountButton = driver.findElement(By.id("account_button"));
		accountButton.click();
		
		// click logout
		System.out.println("click logout");
		WebElement logoutButton = driver.findElement(By.id("logout_button"));
		logoutButton.getText();
		logoutButton.click();
		
		// click OK
		//WebElement okButton = driver.findElement(By.cssSelector(".//*[@id='dialog_0']/div/div/div[2]/button"));
		WebElement okButton = driver.findElement(By.name("OK"));
		okButton.getText();
		okButton.click();
*/		
	}
}