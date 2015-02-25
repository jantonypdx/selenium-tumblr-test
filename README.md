# selenium-tumblr-test 

## Table of contents

- [Description](#description)
- [Motivation](#motivation)
- [Code Example](#code-example)
- [Versioning](#versioning)
- [Creator](#creator)
- [License](#license)

##Description

Selenium Tumblr Tests is a set of java classes to test Tumblr functionality. The Tumblr functionality is implemented with Selenium PageObjects classes. Each PageObject class represents functionality of a particular Tumblr page (e.g. Login/Logout, Dashboard, etc.) 

PageObject classes are derived from a BasePage class. The BasePage gives all derived classes access to utility functions useful for Tumblr. Additionally, the BasePage is derived from LoadableComponent which simplifies the derived classes and allows simple navigation between pages (i.e. via LoadableComponent object nesting).

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

The reason for developing this was to learn more about using Selenium to test a real-world use case. Tumblr is a simple, but dynamically updated website. Not only do you have to account for the actions of buttons and fields, but also dynamic information that is updated on the page. In doing so, I learned more about the design advantages of:

BaseObject: http://elementalselenium.com/tips/9-use-a-base-page-object<br/>
The BaseObject encapsulates utility functions and functionality common to each page object needed to interact with the website. The subsequent derived objects, LoginPage and DashboardPage, are then fairly trivial to implement. Beyond the constructor, they only contain fields and function operations.

PageObjects: https://code.google.com/p/selenium/wiki/PageObjects<br/>
PageObjects are a way to model how you interact with a website. By encapsulating functionality into objects, you contain all interaction to one location. If the website's functionality later changes, you then only need to update it in one place.

LoadableComponent: https://code.google.com/p/selenium/wiki/LoadableComponent<br/>
LoadableComponent is a class ensuring that each page object is loaded in a similar way. By extending this in your page objects, you ensure similar functionality on each page. You also gain the ability to chain together navigation events between the pages. This results in a great reduction of code in both your page objects and your tests.

Utilities<br/>
The BasePage contains utilities that wait for elements to be visible, elements to be clickable, elements to disappear, pages to be reloaded, and alert popups to be handled. These are specific to needs for Tumblr. They are implemented using Selenium's Wait interface, page source retrieval, and alert exception handling functionality.

## Code Example

Given that pages are ultimately derived from LoadableComponent, page objects can be chained together by passing them into the constructor of the next page object. All page navigation can then be trigged by calling a simple "get()" call to the last object (as shown below). Navigation exit is then a simple call to the login page object.
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

The MIT License (MIT)

Copyright (c) 2015 John Antony

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
