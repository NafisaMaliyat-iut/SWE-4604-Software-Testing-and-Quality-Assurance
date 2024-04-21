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

public class AddToFavoritesTest {
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
    }
    @Test
    public void cannotAddFavoritesWhenLoggedOut() {
        driver.get("https://practicesoftwaretesting.com/#/product/01HVVZ3PV3CZTFTZPWG5X1HR0E");
        driver.manage().window().setSize(new Dimension(1382, 754));
        WebElement addToFavBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"add-to-favorites\"]")));
        addToFavBtn.click();

        WebElement toastMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".toast-body")));
        assertEquals("Unauthorized, can not add product to your favorite list.",toastMessage.getText());
    }

    @Test
    public void addFavoritesWhileLoggedIn(){
        login();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.get("https://practicesoftwaretesting.com/#/product/01HVW2HJR8X0X86R4QYBAGSR21");
        driver.manage().window().setSize(new Dimension(1382, 754));
        WebElement addToFavBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"add-to-favorites\"]")));
        addToFavBtn.click();

        WebElement toastMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".toast-body")));
        assertEquals("Product added to your favorites list.",toastMessage.getText());

    }

    @Test
    public void tryingToAddAlreadyAddedFavShowsWarningMessage(){
        login();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.get("https://practicesoftwaretesting.com/#/product/01HVW2HJR8X0X86R4QYBAGSR21");
        driver.manage().window().setSize(new Dimension(1382, 754));
        WebElement addToFavBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"add-to-favorites\"]")));
        addToFavBtn.click();

        WebElement toastMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".toast-body")));
        assertEquals("Product already in your favorites list.",toastMessage.getText());

    }
}
