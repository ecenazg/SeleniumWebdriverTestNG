package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PriceComparison {
    WebDriver driver;

    @BeforeTest
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\chromedriver-win64\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        WebDriver driver=new ChromeDriver(options);
        driver.get("https://google.com");

    }

    @Test
    public void comparePrices() {
        // URLs of the product on different e-commerce websites
        String[] urls = {
                "https://example.com/product1", // Replace with actual URL
                "https://example2.com/product1", // Replace with actual URL
                "https://example3.com/product1"  // Replace with actual URL
        };

        // CSS selectors or XPaths to find the price element on the webpage
        String[] priceSelectors = {
                "#price", // Replace with actual CSS selector or XPath
                ".product-price", // Replace with actual CSS selector or XPath
                "xpath"///*[@id='price']" // Replace with actual CSS selector or XPath
        };

        List<Double> prices = new ArrayList<>();
        try {
            for (int i = 0; i < urls.length; i++) {
                driver.get(urls[i]);
                String priceText = driver.findElement(By.cssSelector(priceSelectors[i])).getText();
                double price = parsePrice(priceText);
                prices.add(price);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Failed to extract prices due to an exception.");
        }

        // Calculate and print out the price comparison
        if (!prices.isEmpty()) {
            double cheapest = Collections.min(prices);
            double expensive = Collections.max(prices);
            double average = prices.stream().mapToDouble(a -> a).average().orElse(0.0);

            System.out.println("Cheapest Price: $" + cheapest);
            System.out.println("Most Expensive Price: $" + expensive);
            System.out.println("Average Price: $" + average);
        }
    }

    @AfterTest
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Helper method to parse price text to double value.
     */
    private double parsePrice(String priceText) {
        priceText = priceText.replaceAll("[^\\d.]", ""); // Remove non-numeric characters
        return Double.parseDouble(priceText);
    }
}
