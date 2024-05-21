package testLogin;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    private String username;
    private String password;
    private boolean isSuccess;

    public LoginTest(String username, String password, boolean isSuccess) {
        this.username = username;
        this.password = password;
        this.isSuccess = isSuccess;
    }

    @Test
    public void testLogin() {
        driver.get("file:///C:/Users/ecena/OneDrive/Belgeler/SeleniumWebdriverTestNG/src/main/static/login.html");  // Adjust the path to your actual login page

        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.tagName("button")).click();

        if (isSuccess) {
            String expectedUrl = "file:///C:/Users/ecena/OneDrive/Belgeler/SeleniumWebdriverTestNG/src/main/static/success.html";
            String actualUrl = driver.getCurrentUrl().split("\\?")[0];  // Remove query parameters
            Assert.assertEquals(actualUrl, expectedUrl);
        } else {
            String alertText = driver.switchTo().alert().getText();
            Assert.assertTrue(alertText.contains("Invalid Credentials"));
            driver.switchTo().alert().accept();
        }
    }
}
