package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeTest {
    public static void main(String[] args) {
        // Setup ChromeDriver path, this line is not needed if you have added ChromeDriver to your PATH
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\chrome-win64");

        // Create a new instance of the ChromeDriver
        WebDriver driver = new ChromeDriver();

        // Use the browser to visit a website
        driver.get("https://www.google.com");

        // Print the title of the page
        System.out.println("Page title is: " + driver.getTitle());

        // Close the browser
        driver.quit();
    }
}
