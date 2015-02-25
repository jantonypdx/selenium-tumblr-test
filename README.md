# selenium-tumblr-test 

##Description

Selenium Tumblr Tests is a set of java classes to test Tumblr functionality. Tumblr functionality is implemented with Selenium PageObjects classes. Each PageObject class represents functionality of a particular Tumblr page (e.g. Login/Logout, Dashboard, etc.) 

PageObject classes are derived from a BasePage class. The BasePage gives all derived classes access to utility functions useful for Tumblr. Additionally, the BasePage is derived from LoadableComponent which allows simple navigation between pages (i.e. via LoadableComponent object nesting).

Files:
```
TumblrTests.java:   set of top-level JUnit tests. Instantiates Login & Dashboard pages, 
                    performs Tumblr operations
SimpleMain.java:    a basic class with a static main function. Used to test DashboardPage, 
                    LoginPage, BasePage development
config.properties:  configuration properties file. stores Tumblr username and password.

BasePage.java:      Base Selenium class. Extends LoadableComponent and implements Tumblr 
                    utility functions
LoginPage.java:     Selenium PageObject representing Tumblr login/logout functionality. 
                    Extends BasePage.
DashboardPage.java: Selenium PageObject representing Tumblr dashboard functionality. 
                    Extends BasePage.

README.md:          this file
```

## Motivation

The reason for developing this was to learn more about using Selenium to test a real-world use case. Tumblr is a simple, but dynamically updated website. Not only do you have to account for the actions of buttons and fields, but also dynamic information that is updated on the page.

In doing so, I learned more about the design advantages of:

BaseObject: http://elementalselenium.com/tips/9-use-a-base-page-object<br/>
The BaseObject encapsulates much of the functionality and utility functions needed to interact with the website. The subsequent derived object, LoginPage and DashboardPage, are then fairly trivial to implement. Beyond the constructor, they only contain fields and function to perform operations.

PageObjects: https://code.google.com/p/selenium/wiki/PageObjects<br/>
PageObjects are a way to model how you interact with a website. By encapsulationg functionality into objects, you contain all interaction to one location. If functionality changes, you need only update it in one place.

LoadableComponent: https://code.google.com/p/selenium/wiki/LoadableComponent<br/>
LoadComponent is a class ensuring that each page object is loaded in a similar way. By extending this in your page objects, you ensure similar functionality, plus the ability to chain navigation between pages together. The results in a great reduction of code in both your page objects and your tests.

## Code Example

Given that pages are ultimately derived from LoadableComponent, page objects can be chained together by passing them into the constructor of the next page object. All page navigation can then be trigged by calling a simple "get()" call on the last object as follows. Additionally, cleanup is a simple call to the login page.
```
// create webdriver and pages		
WebDriver driver = new FirefoxDriver();		
LoginPage loginPage = new LoginPage(driver, null, username, password);
DashboardPage dashboardPage = new DashboardPage(driver, loginPage);
dashboardPage.get();

// call any dashboard functions here
dashboardPage.addNewText("Some Title", "Some Text");

// cleanup
loginPage.logout();
```



## Installation

TO-DO:
Provide code examples and explanations of how to get the project.

## API Reference

TO-DO:
Depending on the size of the project, if it is small and simple enough the reference docs can be added to the README. For medium size to larger projects it is important to at least provide a link to where the API reference docs live.

## Tests

TO-DO:
Describe and show how to run the tests with code examples.

## Contributors

TO-DO:
Let people know how they can dive into the project, include important links to things like issue trackers, irc, twitter accounts if applicable.

## License

TO-DO:
A short snippet describing the license (MIT, Apache, etc.)
