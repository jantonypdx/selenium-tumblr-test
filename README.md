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
```
BaseObject:        http://elementalselenium.com/tips/9-use-a-base-page-object
PageObjects:       https://code.google.com/p/selenium/wiki/PageObjects
LoadableComponent: https://code.google.com/p/selenium/wiki/LoadableComponent

```

## Code Example

TO-DO:
Show what the library does as concisely as possible, developers should be able to figure out **how** your project solves their problem by looking at the code example. Make sure the API you are showing off is obvious, and that your code is short and concise.

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
