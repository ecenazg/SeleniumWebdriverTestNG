package testLogin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    @Parameters("browser")
    public void setup(String browser) {
        if ("chrome".equals(browser)) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if ("firefox".equals(browser)) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }
        wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        driver.get("http://localhost:8000/login.html");
    }

    @Test
    public void testLoginFunctionality() {
        driver.findElement(By.id("username")).sendKeys("correctUser");
        driver.findElement(By.id("password")).sendKeys("correctPassword");
        driver.findElement(By.tagName("button")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));
        WebElement header = driver.findElement(By.tagName("h1"));
        assert "Login Successful!".equals(header.getText());
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
