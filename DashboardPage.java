package selenium_tumblr_test;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

/**
 * --------------------------
 * DashboardPage.java
 * --------------------------
 * Copyright (c) 2015 John Antony
 * This code is licensed under the terms of the MIT License
 * see https://github.com/jantonypdx/selenium-tumblr-test/blob/master/license.txt
 * 
 * DashboardPage handles Tumblr's dashboard functionality. This includes
 * adding new posts of different types and counting the total number of posts.
 * --------------------------
 * Original Author: John Antony;
 * Changes:
 * --------
 * 24-Feb-2015 : Version 1 (JJA);
 */
public class DashboardPage extends BasePage<DashboardPage>{

	private static final String DASHBOARD_PAGE_URL = "https://www.tumblr.com/dashboard";
	private static final String DASHBOARD_PAGE_TITLE = "Tumblr";
	
	/**
	 * button fields for different post types
	 */	
	@FindBy(id="new_post_label_text")
	private WebElement newTextButton;
	private By byNewText = By.id("new_post_label_text");

	@FindBy(id="new_post_label_link")
	private WebElement newLinkButton;
	private By byNewLink = By.id("new_post_label_link");
	
	@FindBy(id="new_post_label_quote")
	private WebElement newQuoteButton;
	private By byNewQuote = By.id("new_post_label_quote");
	
	/**
	 * title, body, and post fields
	 */
	@FindBy(css="div.editor.editor-plaintext")
	private WebElement titleInput;

	private By byLinkURL = By.className("ace_content");
	
	@FindBy(className="ace_text-input")
	private WebElement bodyInput;

	@FindBy(xpath=".//*[@id='posts_control']/a/span")
	private WebElement postCountLabel;
	private By byPostCountLabel = By.xpath(".//*[@id='posts_control']/a/span");
	
	@FindBy(xpath="//button[text()='Post']")
	private WebElement postButton;
	private By byPost = By.xpath("//button[text()='Post']");
	
	public DashboardPage(WebDriver driver, LoadableComponent<?> parent) {

		// first, make sure you store the base class driver and parent values
		this.driver = driver;
		this.parent = parent;
		
		// set page specific URL and title		
		setPageURL(DASHBOARD_PAGE_URL);
		setPageTitle(DASHBOARD_PAGE_TITLE);

		// lastly, initialize your FindBy WebElement variables
		PageFactory.initElements(driver, this);		
	}

/*	
 *  Don't need to override LoadableComponent.isLoaded() here. It's already 
 *  implemented the way we want it in the BasePage class.
	@Override
    protected void isLoaded() throws Error { }
*/
 
/*	
 *  Don't need to override LoadableComponent.load() here. It's already 
 *  implemented the way we want it in the BasePage class.	
    @Override
    protected void load() { }
*/

	/*
	 * Tumblr dashboard functions
	 */
	
	/*
	 * count the number of posts
	 */
	public int countPosts() {
		
		// as you add new entries, the post count doesn't always
		// update immediately, so...
		// click the post count. this will reload the page. wait for
		// the page reload.
		clickThenWaitForPageReload(byPostCountLabel);
		
		// now count the number of posts.
		return Integer.parseInt(postCountLabel.getText());		
	}
	
	/*
	 * add a new Text entry
	 */	
	public void addNewText(String title, String body) {

		// going to this URL would have the same effect
		//driver.get("https://www.tumblr.com/blog/runspotgo/new/text");
		
		// click new text
		newTextButton.click();

		// add title
		titleInput.click();
		titleInput.sendKeys(title);

		// add body
		bodyInput.click();
		bodyInput.sendKeys(body);

		// post & wait
		clickThenWaitForElementToDisappear(byPost);
	}
	
	/*
	 * add a new Link entry
	 */	
	public void addNewLink(String url) {
		
		// going to this URL would have the same effect		
		//driver.get("https://www.tumblr.com/blog/runspotgo/new/quote");

		// click new link			
		newLinkButton.click();
		
		// fill in link URL
		WebElement textUrl = driver.switchTo().activeElement();
		textUrl.sendKeys(url + Keys.RETURN);
		
		// wait until text element visible
		waitForElementToBeVisible(byLinkURL);
		
		// post & wait
		clickThenWaitForElementToDisappear(byPost);			
	}
	
	/*
	 * add a new Quote entry
	 */	
	public void addNewQuote(String quote, String author) {
		
		// going to this URL would have the same effect
		//driver.get("https://www.tumblr.com/blog/runspotgo/new/quote");
		
		// click new quote
		newQuoteButton.click();

		// add title
		titleInput.click();
		titleInput.sendKeys(quote);

		// add body
		bodyInput.click();
		bodyInput.sendKeys(author);

		// post & wait
		clickThenWaitForElementToDisappear(byPost);
	}
	
	// other new post types left as an exercise for the reader
	// e.g. photo, chat, audio, video, etc.
}