package org.example.Homepage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class PriceRangeTest {
    private WebDriver driver;
    private Map<String, Object> vars;
    private WebElement minSlider;
    private  WebElement maxSlider;
    private Actions builder;
    JavascriptExecutor js;
    @Before
    public void setUp() {
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
        driver.get("https://practicesoftwaretesting.com/");
        driver.manage().window().setSize(new Dimension(1382, 754));

         minSlider = driver.findElement(By.cssSelector(".ngx-slider-pointer-min"));
         maxSlider = driver.findElement(By.cssSelector(".ngx-slider-pointer-max"));
         builder = new Actions(driver);
    }
    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testWithMaxRangeOnly() {

        builder.moveToElement(minSlider).clickAndHold().moveByOffset(0, 0).release().perform();
        builder.moveToElement(maxSlider).clickAndHold().moveByOffset(49, 0).release().perform();

        //wait for sorting to finish
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<WebElement> productElements = driver.findElements(By.cssSelector("*[data-test=\"product-price\"]"));

        boolean allPricesWithinRange = productElements.stream()
                .map(WebElement::getText)
                .map(price -> Double.parseDouble(price.replaceAll("[^0-9.]", "")))
                .allMatch(price -> price >= 0 && price <= 143);

        assertTrue(allPricesWithinRange);
    }

    @Test
    public void testWithMinRangeOnly() {
        builder.moveToElement(minSlider).clickAndHold().moveByOffset(95, 0).release().perform();
        builder.moveToElement(maxSlider).clickAndHold().moveByOffset(0, 0).release().perform();

        //wait for sorting to finish
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<WebElement> productElements = driver.findElements(By.cssSelector("*[data-test=\"product-price\"]"));

        boolean allPricesWithinRange = productElements.stream()
                .map(WebElement::getText)
                .map(price -> Double.parseDouble(price.replaceAll("[^0-9.]", "")))
                .allMatch(price -> price >= 83 && price <= 100);

        assertTrue(allPricesWithinRange);
    }

    @Test
    public void testWithBothRange() {
        builder.moveToElement(minSlider).clickAndHold().moveByOffset(95, 0).release().perform();
        builder.moveToElement(maxSlider).clickAndHold().moveByOffset(78, 0).release().perform();

        //wait for sorting to finish
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<WebElement> productElements = driver.findElements(By.cssSelector("*[data-test=\"product-price\"]"));

        boolean allPricesWithinRange = productElements.stream()
                .map(WebElement::getText)
                .map(price -> Double.parseDouble(price.replaceAll("[^0-9.]", "")))
                .allMatch(price -> price >= 83 && price <= 168);

        assertTrue(allPricesWithinRange);
    }
}
