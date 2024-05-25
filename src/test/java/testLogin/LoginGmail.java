package testLogin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginGmail {
    WebDriver driver;

    @BeforeTest
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\chromedriver-win64\\chromedriver.exe");

        // Create a new instance of the Chrome driver
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void testGmailLogin() {
        driver.get("https://mail.google.com/");

        // Enter the email address or phone number
        WebElement emailField = driver.findElement(By.id("identifierId"));
        emailField.sendKeys("ece00688@gmail.com");
        driver.findElement(By.id("identifierNext")).click();

        // Wait for the password field to be visible and enter the password
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        try {
            WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
            passwordField.sendKeys("monicageller");
            driver.findElement(By.id("passwordNext")).click();

            // Wait for inbox to load and verify login by checking for inbox presence
            WebElement inbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'Inbox')]")));
            Assert.assertTrue(inbox.isDisplayed(), "Inbox is not displayed, login might have failed.");
            System.out.println("Successfully logged in to Gmail.");
        } catch (Exception e) {
            // Check for other possible elements indicating intermediate pages
            if (driver.findElements(By.xpath("//div[contains(text(),'Security check')]")).size() > 0) {
                System.out.println("Security check encountered. Handle it accordingly.");
                Assert.fail("Security check encountered.");
            } else {
                System.out.println("Error: " + e.getMessage());
                Assert.fail("Failed to login to Gmail");
            }
        }
    }

    @AfterTest
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
