package testLogin;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TestCase {

    public static void main(String[] args) throws Exception {

        // Setting an executable route
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\chromedriver-win64\\chromedriver.exe");

        // Create a new instance of the Chrome driver
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");
        WebDriver driver = new ChromeDriver(options);

        // Launch the Online Store Website
        driver.get("https://mail.google.com");

        // Print a log message to the screen
        System.out.println("Successfully opened the website mail.google.com");

        // Maximize window
        driver.manage().window().maximize();

        // Create an instance of WebDriverWait with a 20-second timeout
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Enter Username
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#identifierId")));
        emailField.sendKeys("ece00688@gmail.com");

        // Click on Next
        WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#identifierNext > span")));
        nextButton.click();

        // Enter Password
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
        passwordField.sendKeys("monicageller");

        // Click on 'Sign In' button
        WebElement signInButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#passwordNext > span > span")));
        signInButton.click();

        // Wait for login to complete and user icon to appear
        WebElement userIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href*='SignOutOptions'] > img")));
        userIcon.click();

        // Click logout button
        WebElement logoutButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gb_71")));
        logoutButton.click();

        // Close the driver
        driver.quit();
    }
}
