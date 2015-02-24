package selenium_tumblr_test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * --------------------------
 * SimpleMain.java
 * --------------------------
 * (C)opyright 2015 by John Antony
 * 
 * SimpleMain is a basic class with a static "main" function. This is used
 * to check the development of the Tumblr *Page classes. 
 * --------------------------
 * Original Author: John Antony;
 * Changes:
 * --------
 * 24-Feb-2015 : Version 1 (JJA);
 */
public class SimpleMain {
	
	private static final String CONFIGURATION_FILE = "config.properties";
	private static Properties configProperties = null;
	
	public static void main(String[] args) {

		// first, get username & password from config file
		loadConfigurationProperties();
		String username = configProperties.getProperty("username");
		String password = configProperties.getProperty("password");
		
		// create webdriver and pages		
		WebDriver driver = new FirefoxDriver();		
		LoginPage loginPage = new LoginPage(driver, null, username, password);
		DashboardPage dashboardPage = new DashboardPage(driver, loginPage);
		dashboardPage.get();

		// adding new posts
		dashboardPage.addNewText("Some Title", "Some Text");
		dashboardPage.addNewQuote("Now is the time for all good men...", "anonymous");
		dashboardPage.addNewLink("http://www.google.com");		
	
		System.out.println("number of posts: " + dashboardPage.countPosts());
		
		// cleanup
		loginPage.logout();		
	}
	
	/**
	 * Utility function. Load the configuration file and store key/value 
	 * pairs in configProperties.
	 */
	public static void loadConfigurationProperties() {

		// if not loaded, load configuration properties
		if (null == configProperties)
		{
			configProperties = new Properties();
		    FileInputStream is;
			try {
				is = new FileInputStream(CONFIGURATION_FILE);
			    configProperties.load(is);
			    is.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}		
	}
}
