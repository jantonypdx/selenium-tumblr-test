# selenium-tumblr-test 

## Table of Contents

- [Description](#description)
- [Motivation](#motivation)
- [Code Example](#code-example)
- [Versioning](#versioning)
- [Creator](#creator)
- [License](#license)

##Description

Selenium Tumblr Tests is a set of java classes to test Tumblr functionality. The Tumblr functionality is implemented with Selenium PageObjects classes. Each PageObject class represents functionality of a particular Tumblr page (e.g. Login/Logout page, Dashboard page, etc.) 

PageObject classes are derived from a BasePage class. The BasePage gives all derived classes access to utility functions useful for Tumblr. Additionally, the BasePage is derived from LoadableComponent which simplifies the derived classes and allows simple navigation between pages (i.e. via [LoadableComponent nested components](https://code.google.com/p/selenium/wiki/LoadableComponent#Advanced_Usage:_Nested_Components)).

Files:
```
TumblrTests.java:   set of top-level JUnit tests. Instantiates Login & Dashboard pages, 
                    runs JUnit tests on Tumblr operations
SimpleMain.java:    a basic class with a static main function. Used to test DashboardPage, 
                    LoginPage, BasePage development
config.properties:  configuration properties file. stores Tumblr username and password.

BasePage.java:      Base Selenium class. Extends LoadableComponent and implements Tumblr 
                    utility functions. Other pages are derived from this page.
LoginPage.java:     Selenium PageObject representing Tumblr login/logout functionality. 
                    Extends BasePage.
DashboardPage.java: Selenium PageObject representing Tumblr dashboard functionality. 
                    Extends BasePage.

README.md:          this file
```

## Motivation

The reason for developing this was to learn more about using Selenium to test a real-world use case. Tumblr is a simple, but dynamically updated website. Not only do you need to account for the actions of buttons and fields, but also information on the page that is dynamically updated. In doing so, I learned more about the design advantages of:

**BaseObject**:<br/>
See: <a href="http://elementalselenium.com/tips/9-use-a-base-page-object" title="Using a Base Page Object" target="_blank">Using a Base Page Object</a><br/>
The BaseObject encapsulates utility functions and functionality common to each page object needed to interact with the website. The subsequent derived objects, LoginPage and DashboardPage, are then fairly trivial to implement. Beyond the constructor, they only contain fields and function operations.

**PageObjects**:<br/>
See: <a href="https://code.google.com/p/selenium/wiki/PageObjects" title="Selenium PageObjects" target="_blank">Selenium PageObjects</a><br/>
PageObjects are a way to model how you interact with a website. By encapsulating functionality into objects, you contain all interaction to one location. If the website's functionality later changes, you then only need to update it in one place.

**LoadableComponent**:<br/>
See: <a href="https://code.google.com/p/selenium/wiki/LoadableComponent" title="Selenium Loadable Component" target="_blank">Selenium Loadable Component</a><br/>
LoadableComponent is a class ensuring that each page object is loaded in a similar way. By extending this in your page objects, you ensure similar functionality on each page. You also gain the ability to chain together navigation events between the pages. This results in a great reduction of code in both your page objects and your tests.

**Utilities**:<br/>
The BasePage contains utilities that wait for elements to be visible, elements to be clickable, elements to disappear, pages to be reloaded, and alert popups to be handled. These are specific to needs for Tumblr. They are implemented using Selenium's Wait interface, page source retrieval, and alert exception handling functionality.

## Code Example

Given that pages are ultimately derived from LoadableComponent, page objects can be chained together by passing them into the constructor of the next page object. All page navigation can then be trigged by calling a simple "get()" call to the final chained object (as shown below). Navigation exit is then handled with a simple call to the login page object.
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

## Creator

**John Antony**

- <https://twitter.com/jantony_pdx>
- <https://github.com/jantonypdx>

## License

Copyright (c) 2015 John Antony. Code released under [the MIT license](https://github.com/jantonypdx/selenium-tumblr-test/blob/master/license.txt).
