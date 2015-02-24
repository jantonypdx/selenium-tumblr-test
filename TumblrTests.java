package selenium_tumblr_test;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * --------------------------
 * TumblrTests.java
 * --------------------------
 * (C)opyright 2015 by John Antony
 * 
 * TumblrTests is a set of JUnit tests using Selenium to test Tumblr.
 * Tumblr functionality is modeled using Login and Dashboard page objects
 * which are derived from a LoadableComponent extended base page.
 *  
 * It logs into Tumblr, adds new posts, and counts posts.
 * --------------------------
 * Original Author: John Antony;
 * Changes:
 * --------
 * 24-Feb-2015 : Version 1 (JJA);
 */
public class TumblrTests {
	// file used to store Tumblr username and password information
	private static final String CONFIGURATION_FILE = "config.properties";
	private static Properties configProperties = null;
	
	private static WebDriver driver;
	private static LoginPage loginPage;
	private static DashboardPage dashboardPage;
	
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
	
	/*
	 * 	BROWSER SETUP & LOGIN
	 */	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		// first, get username & password from config file
		loadConfigurationProperties();
		String username = configProperties.getProperty("username");
		String password = configProperties.getProperty("password");

		// create webdriver and pages
		driver = new FirefoxDriver();		
		loginPage = new LoginPage(driver, null, username, password);
		dashboardPage = new DashboardPage(driver, loginPage);
		dashboardPage.get();
	}	

	@AfterClass
	public static void tearDownAfterClass() throws Exception {		
		loginPage.logout();
		driver.quit();
	}

	@Before
	public void setUp() throws Exception {
		// move setup here if you want new browser for each test
	}

	@After
	public void tearDown() throws Exception {
		// move teardown code here if you want new browser for each test
	}
	
	/*
	 * 	TESTS
	 */
	@Test
	// add a new text entry to Tumblr
	public void testAddText() {				
		int beforeCount = dashboardPage.countPosts();
		
		dashboardPage.addNewText("Test Title", "test text");
		int afterCount = dashboardPage.countPosts();
		
		assertEquals(beforeCount+1, afterCount);				
	}

	@Test
	// add a new quote entry to Tumblr
	public void testAddQuote() {		
		int beforeCount = dashboardPage.countPosts();
		
		dashboardPage.addNewQuote("Now is the time for all good men...", "anonymous");
		int afterCount = dashboardPage.countPosts();
		
		assertEquals(beforeCount+1, afterCount);				
	}

	@Test
	// add a new link entry to Tumblr	
	public void testAddLink() {
		int beforeCount = dashboardPage.countPosts();
		
		dashboardPage.addNewLink("http://www.google.com");
		int afterCount = dashboardPage.countPosts();
		
		assertEquals(beforeCount+1, afterCount);				
	}
	
	@Test
	// count the number of posts on Tumblr	
	public void testCountPosts() {		
		assertTrue( dashboardPage.countPosts() > 0 );		
	}
	
	// other new post types left as an exercise for the reader
	// e.g. photo, chat, audio, video, etc.	
}
