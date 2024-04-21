package org.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
public class ProductDetailsTest {
    private WebDriver driver;
    private Map<String, Object> vars;
    private WebDriverWait wait;
    JavascriptExecutor js;
    @Before
    public void setUp() {
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://practicesoftwaretesting.com/#/product/01HVVZ3PV3CZTFTZPWG5X1HR0E");
        driver.manage().window().setSize(new Dimension(1382, 754));


    }
    @After
    public void tearDown() {
        driver.quit();
    }
    @Test
    public void amountIsIncrementedProperlyAfterOneClick() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("*[data-test=\"quantity\"]")));
        element.click();
        driver.findElement(By.cssSelector(".fa-plus")).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        WebElement inputElement = driver.findElement(By.cssSelector("input[data-test='quantity']"));
        String inputValue = inputElement.getAttribute("value");
        assertEquals("2",inputValue);
    }

    @Test
    public void amountDecrementsProperlyWithOneClick() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("*[data-test=\"quantity\"]")));
        element.click();
        for(int i=0;i<3; i++)
            driver.findElement(By.cssSelector(".fa-plus")).click();
        driver.findElement(By.cssSelector(".fa-minus")).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement inputElement = driver.findElement(By.cssSelector("input[data-test='quantity']"));
        String inputValue = inputElement.getAttribute("value");
        assertEquals("3",inputValue);
    }

    @Test
    public void amountDoesNotDropBelowOne() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("*[data-test=\"quantity\"]")));
        element.click();
        for(int i=0;i<3; i++)
            driver.findElement(By.cssSelector(".fa-minus")).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement inputElement = driver.findElement(By.cssSelector("input[data-test='quantity']"));
        String inputValue = inputElement.getAttribute("value");
        assertEquals("1",inputValue);
    }

    @Test
    public void amountDecrementsProperlyWithMultipleClicks() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("*[data-test=\"quantity\"]")));
        element.click();
        for(int i=0;i<6; i++)
            driver.findElement(By.cssSelector(".fa-plus")).click(); //7
        for(int i=0;i<2; i++)
            driver.findElement(By.cssSelector(".fa-minus")).click(); //5
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement inputElement = driver.findElement(By.cssSelector("input[data-test='quantity']"));
        String inputValue = inputElement.getAttribute("value");
        assertEquals("5",inputValue);
    }

    @Test
    public void productAddedToCartDisplaysMessage() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable((By.cssSelector("*[data-test=\"add-to-cart\"]"))));
        element.click();

        WebElement toastMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".toast-body")));
       assertEquals("Product added to shopping cart.",toastMessage.getText());
    }

    @Test
    public void productAddedToCartDisplaysCartIconWithCorrectQuantity() {
        WebElement incrementButton = wait.until(ExpectedConditions.elementToBeClickable((By.cssSelector(".fa-plus"))));
        for(int i=0;i<6; i++)
            incrementButton.click(); //7

        WebElement element = wait.until(ExpectedConditions.elementToBeClickable((By.cssSelector("*[data-test=\"add-to-cart\"]"))));
        element.click();

        WebElement cartIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("i.fa.fa-shopping-cart.px-1")));
        assertTrue(cartIcon.isDisplayed());
        WebElement cartQuantity = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"cart-quantity\"]")));
        assertEquals("7", cartQuantity.getText());
    }

    @Test
    public void cartQuantityUpdatedWhenMoreProductsAdded() {
        WebElement incrementButton = wait.until(ExpectedConditions.elementToBeClickable((By.cssSelector(".fa-plus"))));
        for(int i=0;i<6; i++)
            incrementButton.click(); //7

        WebElement element = wait.until(ExpectedConditions.elementToBeClickable((By.cssSelector("*[data-test=\"add-to-cart\"]"))));
        element.click();
        element.click();

        WebElement toastMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".toast-body")));
        assertEquals("Product added to shopping cart.",toastMessage.getText());
        WebElement cartIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("i.fa.fa-shopping-cart.px-1")));
        assertTrue(cartIcon.isDisplayed());
        WebElement cartQuantity = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"cart-quantity\"]")));
        assertEquals("14", cartQuantity.getText());
    }


}
