package org.example;

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PriceComparisonArezo {
    WebDriver driver;

    @BeforeTest
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\chromedriver-win64\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);  // Fix here: use the instance variable directly
        driver.get("https://google.com");
    }


    @Test
    public void comparePrices() {
        String[] urls = {
                "https://www.hepsiburada.com/la-roche-posay-anthelios-uvmune-400-spf-50-invisible-fluid-50-ml-pm-HB00000IWKZ8",
                "https://www.amazon.com.tr/Roche-Anthelios-UVmune-Tinted-Renkli/dp/B09SLGZDXH",
                "https://www.trendyol.com/la-roche-posay/anthelios-uvmune400-invisible-fluid-spf50-tum-cilt-tipleri-icin-yuz-gunes-kremi-50-ml-p-5770567"
        };
        String[] priceSelectors = {
                "#offering-price > span:nth-child(1)", // First website CSS selector
                "#corePrice_feature_div > div > div > span.a-price.aok-align-center > span:nth-child(2) > span.a-price-whole", // Second website CSS selector
                "#product-detail-app > div > div.flex-container > div > div:nth-child(2) > div:nth-child(2) > div > div.product-detail-wrapper > div.pr-in-w > div > div > div.product-price-container > div > div > span" // Third website CSS selector
        };
        List<Double> prices = new ArrayList<>();
        try {
            for (int i = 0; i < urls.length; i++) {
                driver.get(urls[i]);
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                WebElement priceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(priceSelectors[i])));
                String priceText = priceElement.getText();
                double price = parsePrice(priceText);
                prices.add(price);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Failed to extract prices due to an exception: " + e.getMessage());
        }
        if (!prices.isEmpty()) {
            Collections.sort(prices);
            double cheapest = prices.get(0);
            double expensive = prices.get(prices.size() - 1);
            double average = prices.stream().mapToDouble(a -> a).average().orElse(0.0);

            System.out.println("Cheapest Price: " + cheapest + " TL");
            System.out.println("Most Expensive Price: " + expensive + " TL");
            System.out.println("Average Price: " + average + " TL");
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
