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

import static org.junit.Assert.*;

public class AddToCartAfterLoginTest {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;
    private WebDriverWait wait;
    @Before
    public void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(18));
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
        login();
    }
    @After
    public void tearDown() {
        driver.quit();
    }

    private void login(){
        driver.get("https://practicesoftwaretesting.com/#/auth/login");
        driver.manage().window().setSize(new Dimension(697, 723));
        driver.findElement(By.cssSelector("*[data-test=\"email\"]")).sendKeys("customer@practicesoftwaretesting.com");
        driver.findElement(By.cssSelector("*[data-test=\"password\"]")).sendKeys("welcome01");
        driver.findElement(By.cssSelector("*[data-test=\"login-submit\"]")).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void addToCart(){
        driver.get("https://practicesoftwaretesting.com/#/product/01HW050CAZR24AX3TPKZFTZ01Y");
        wait.until(ExpectedConditions.urlMatches(".*/#/product/01HW050CAZR24AX3TPKZFTZ01Y"));
        driver.manage().window().setSize(new Dimension(1382, 754));
        WebElement plusButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".fa-plus")));
        for(int i=0;i<3; i++)
            plusButton.click();

        WebElement element = wait.until(ExpectedConditions.elementToBeClickable((By.cssSelector("*[data-test=\"add-to-cart\"]"))));
        element.click();
    }

    private void redirectToCheckout(){
        WebElement cartIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"nav-cart\"]")));
        cartIcon.click();
        wait.until(ExpectedConditions.urlMatches(".*/#/checkout"));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void noCartItemsAreDisplayedIfNotAdded() {
        driver.get("https://practicesoftwaretesting.com/#/checkout");
        wait.until(ExpectedConditions.urlMatches(".*/#/checkout"));

        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("app-cart > p")));
        assertEquals("The cart is empty. Nothing to display.", element.getText());
    }

    @Test
    public void AddToCartAfterLoginDisplaysMessage() {
        addToCart();
        WebElement toastMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".toast-body")));
        assertEquals("Product added to shopping cart.",toastMessage.getText());
    }
    @Test
    public void correctCartQuantityIsDisplayed() {
        addToCart();
        WebElement cartIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("i.fa.fa-shopping-cart.px-1")));
        assertTrue(cartIcon.isDisplayed());
        WebElement cartQuantity = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"cart-quantity\"]")));
        assertEquals("4", cartQuantity.getText());
    }


    @Test
    public void redirectedToCheckoutPageCorrectly() {
        addToCart();
        redirectToCheckout();
        assertEquals("https://practicesoftwaretesting.com/#/checkout",driver.getCurrentUrl());
    }

    @Test
    public void correctStepIsHighlightedAfterAdding() {
        addToCart();
        redirectToCheckout();
        WebElement cartElement = wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//li[@class='current']"))));
        WebElement aElement = cartElement.findElement(By.tagName("a"));
        assertTrue(aElement.getText().toLowerCase().contains("cart"));
    }

    @Test
    public void correctItemDetailsIsDisplayed(){
        addToCart();
        redirectToCheckout();
        WebElement productTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".product-title")));
        assertEquals("Cordless Drill 12V ",productTitle.getText());
        WebElement productQuantity = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".form-control.quantity")));
        assertEquals("4",productQuantity.getAttribute("value"));
        WebElement productPrice = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".col-md-2:nth-child(4) > span")));
        assertEquals("$46.50",productPrice.getText());
        WebElement productTotalPrice = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".col-md-2:nth-child(5) > span")));
        assertEquals("$186.00", productTotalPrice.getText());
        WebElement totalPrice = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("tfoot .col-md-2:nth-child(5)")));
        assertEquals("$186.00", totalPrice.getText());
    }

    @Test
    public void priceAndIconUpdatesIfQuantityChanged(){
        addToCart();
        redirectToCheckout();
        WebElement productQuantity = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".form-control.quantity")));
        productQuantity.sendKeys("5" + Keys.ENTER);
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement productTotalPrice = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".col-md-2:nth-child(5) > span")));
        assertEquals("$2,092.50",productTotalPrice.getText());
        WebElement totalPrice = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("tfoot .col-md-2:nth-child(5)")));
        assertEquals("$2,092.50",totalPrice.getText());
        WebElement cartQuantity = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"cart-quantity\"]")));
        assertEquals("45",cartQuantity.getText());

    }

    @Test
    public void priceAndIconUpdatedOnProductDelete(){
        addToCart();
        redirectToCheckout();

        WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".fa-remove")));
        deleteButton.click();
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement totalPrice = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("tfoot .col-md-2:nth-child(5)")));
        assertEquals("$0.00",totalPrice.getText());
        Boolean cartIconAfterDelete = wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("i.fa.fa-shopping-cart.px-1")));
        assertTrue(cartIconAfterDelete);
    }

}

