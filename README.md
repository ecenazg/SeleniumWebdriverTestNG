# Selenium WebDriver TestNG Project

This repository showcases a simple Selenium WebDriver framework utilizing TestNG to perform automated web testing. The project includes examples of both functional testing with a login scenario and data-driven testing with a price comparison across multiple e-commerce websites.

## Technologies & Methods

- Selenium WebDriver: Used for browser automation to interact with web elements.
- TestNG: Serves as the test framework providing the test structure (including setups, teardowns, and test tasks) and managing test suites defined in testng.xml.
- WebDriverManager: Facilitates the management of browser driver binaries, simplifying the initial setup by handling driver requirements automatically.
- Java: The primary programming language used for writing test scripts.

## Test Scenarios
1. Login Functionality Test
This scenario tests a simple login interface. It verifies that the web page works correctly with the correct user credentials and fails otherwise.

2. Price Comparison Test
This test performs a price comparison for a specific product across three different e-commerce websites. It demonstrates how to extract data from web pages and compare numerical values to find the cheapest, most expensive, and average price.

## Running Tests
Run tests through Maven with:
```sh
mvn test
```
Alternatively, configure and run the testng.xml directly in your IDE.
