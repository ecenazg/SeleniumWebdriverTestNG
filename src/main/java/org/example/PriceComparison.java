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

public class PriceComparison {
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
                "https://www.amazon.com.tr/Apple-iPhone-Pro-Max-256/dp/B0CHXNZ6JP/ref=sr_1_5?crid=2UFDGGEZ85B7V&dib=eyJ2IjoiMSJ9.n3BrJmBGew293415cjxh-LsJ-9u5ZYZqYGC8iP_TBeb0QiuOcC6MJ7K1Afh6pYrl8ZEORYVkUYfXAY7wwqV_sgTx7P4prKyFXaERS8IuY3yC40IoFbjY_xyynVD5tDDmgoUJbQB8Qp90x8pfH4YZu_9n9p7CkhmEFWxbbM02JiEKd0M37_QT8tkdvQrQ5lU1dPHJUgVRcLdskrLBAra915doNSONCB7w_ZHUMLwKLle5yt5G0d6ihJJqP4QihljhbM6IC2s97dYcUbmNRYG_w3as8zSa7-0Ug02h8KylnVI.TpK2u-BtwKMbYOvkUNwikb9vRnFqRc_bINZqlJjH7Y4&dib_tag=se&keywords=iphone+15+pro+max+256+gb&qid=1715617798&sprefix=iphone+15+pro+max%2Caps%2C123&sr=8-5",
                "https://www.akakce.com/cep-telefonu/en-ucuz-iphone-15-pro-max-fiyati,1745758201.html",
                "https://www.pttavm.com/iphone-15-pro-max-256-gb-naturel-titanyum-ithalatci-garantili-p-736735070?utm_source=akakce.com&utm_medium=fiyat-kiyaslama&utm_campaign=akakce&v=1.69.2"
        };
        String[] priceSelectors = {
                "#corePrice_feature_div > div > div > span.a-price.aok-align-center > span:nth-child(2) > span.a-price-whole", // First website CSS selector
                "#pd_v8 > div.bb_w > span.pb_v8 > span", // Second website CSS selector
                "#main > div.relative > div > div > div.container > div.flex.flex-col > span > div.flex.flex-col.md\\:flex-row > div.px-4.md\\:pr-0.md\\:pl-5.mt-4.md\\:mt-0.flex.flex-col.flex-1 > div.flex.flex-col.xl\\:flex-row.justify-between.flex-1 > div.md\\:pr-6.flex.flex-col.flex-1.xl\\:w-auto.md\\:mb-3.lg\\:mb-0 > div.flex.leading-none.flex-col.mt-2.md\\:mt-6.mb-2.md\\:mb-0 > div.text-eGreen-700.font-semibold.text-3xl" // Third website CSS selector
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
