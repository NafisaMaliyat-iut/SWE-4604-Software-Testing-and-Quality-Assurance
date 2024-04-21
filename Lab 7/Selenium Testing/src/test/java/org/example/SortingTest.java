package org.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class SortingTest {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;
    @Before
    public void setUp() {
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
        driver.get("https://practicesoftwaretesting.com/");
        driver.manage().window().setSize(new Dimension(1382, 754));
        driver.findElement(By.cssSelector("*[data-test=\"sort\"]")).click();
    }
    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void sortOption1() {
        {
            WebElement dropdown = driver.findElement(By.cssSelector("*[data-test=\"sort\"]"));
            dropdown.findElement(By.xpath("//option[. = 'Name (A - Z)']")).click();
        }

        //wait for sorting to finish
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<WebElement> productElements = driver.findElements(By.cssSelector("*[data-test=\"product-name\"]"));
        List<String> productNames = productElements.stream().map(WebElement::getText).toList();

        boolean isSorted = true;
        for (int i = 0; i < productNames.size() - 1; i++) {
            String currentName = productNames.get(i);
            String nextName = productNames.get(i + 1);
            if (currentName.compareToIgnoreCase(nextName) > 0) {
                isSorted = false;
                break;
            }
        }

        assertTrue(isSorted);
    }

    @Test
    public void sortOption2() {
        {
            WebElement dropdown = driver.findElement(By.cssSelector("*[data-test=\"sort\"]"));
            dropdown.findElement(By.xpath("//option[. = 'Name (Z - A)']")).click();
        }

        //wait for sorting to finish
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<WebElement> productElements = driver.findElements(By.cssSelector("*[data-test=\"product-name\"]"));
        List<String> productNames = productElements.stream().map(WebElement::getText).toList();


        boolean isSorted = true;
        for (int i = 0; i < productNames.size() - 1; i++) {
            String currentName = productNames.get(i);
            String nextName = productNames.get(i + 1);
            if (currentName.compareToIgnoreCase(nextName) < 0) {
                isSorted = false;
                break;
            }
        }

        assertTrue(isSorted);
    }

    @Test
    public void sortOption3() {
        {
            WebElement dropdown = driver.findElement(By.cssSelector("*[data-test=\"sort\"]"));
            dropdown.findElement(By.xpath("//option[. = 'Price (High - Low)']")).click();
        }

        //wait for sorting to finish
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<WebElement> productElements = driver.findElements(By.cssSelector("*[data-test=\"product-price\"]"));
        List<Double> productPrices = productElements.stream()
                .map(WebElement::getText)
                .map(price -> Double.parseDouble(price.replaceAll("[^0-9.]", "")))
                .toList();

        boolean isSorted = true;
        for (int i = 0; i < productPrices.size() - 1; i++) {
            double currentPrice = productPrices.get(i);
            double nextPrice = productPrices.get(i + 1);
            if (currentPrice < nextPrice) {
                isSorted = false;
                break;
            }
        }

        assertTrue(isSorted);
    }

    @Test
    public void sortOption4() {
        {
            WebElement dropdown = driver.findElement(By.cssSelector("*[data-test=\"sort\"]"));
            dropdown.findElement(By.xpath("//option[. = 'Price (Low - High)']")).click();
        }

        //wait for sorting to finish
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<WebElement> productElements = driver.findElements(By.cssSelector("*[data-test=\"product-price\"]"));
        List<Double> productPrices = productElements.stream()
                .map(WebElement::getText)
                .map(price -> Double.parseDouble(price.replaceAll("[^0-9.]", "")))
                .toList();

        boolean isSorted = true;
        for (int i = 0; i < productPrices.size() - 1; i++) {
            double currentPrice = productPrices.get(i);
            double nextPrice = productPrices.get(i + 1);
            if (currentPrice > nextPrice) {
                isSorted = false;
                break;
            }
        }

        assertTrue(isSorted);
    }
}
