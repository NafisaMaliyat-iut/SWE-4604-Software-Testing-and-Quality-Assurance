package org.example;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;

import java.util.*;

public class SearchTest {
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
        driver.findElement(By.cssSelector("*[data-test=\"search-query\"]")).click();
        driver.findElement(By.cssSelector("*[data-test=\"search-query\"]")).sendKeys("hammer");
        driver.findElement(By.cssSelector("*[data-test=\"search-submit\"]")).click();
    }
    @After
    public void tearDown() {
        driver.quit();
    }
    @Test
    public void showsWhatWasSearched() {
        //wait for sorting to finish
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement searchedTextElement = driver.findElement(By.xpath("//h3[contains(text(), 'Searched for: hammer')]"));
        assertNotNull(searchedTextElement);
    }

    @Test
    public void searchResultsAreAccurate() {
        //wait for sorting to finish
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<WebElement> productElements = driver.findElements(By.cssSelector("*[data-test=\"product-name\"]"));
        List<String> productNames = productElements.stream().map(WebElement::getText).toList();

//        System.out.println(productNames.size());
        for (String productName : productNames) {
            assertTrue(productName.toLowerCase().contains("hammer".toLowerCase()));
        }
    }
}
