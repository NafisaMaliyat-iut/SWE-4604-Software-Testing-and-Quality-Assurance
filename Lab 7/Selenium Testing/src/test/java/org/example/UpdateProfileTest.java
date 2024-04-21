package org.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
public class UpdateProfileTest {
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
        redirectToProfilePage();
    }

    @After
    public void tearDown() {
        redirectToProfilePage();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"first-name\"]"))).clear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"first-name\"]"))).sendKeys("Jane");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"last-name\"]"))).clear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"last-name\"]"))).sendKeys("Doe");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"phone\"]"))).clear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"address\"]"))).clear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"address\"]"))).sendKeys("Test street 98");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"postcode\"]"))).clear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"city\"]"))).clear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"state\"]"))).clear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"country\"]"))).clear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"country\"]"))).sendKeys("Austria");
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("*[data-test=\"update-profile-submit\"]"))).click();

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

    private void redirectToProfilePage(){
        driver.get("https://practicesoftwaretesting.com/#/account/profile");
        wait.until(ExpectedConditions.urlMatches(".*/#/account/profile"));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void showsDetailsCorrectly(){
        assertEquals("Jane",wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"first-name\"]"))).getAttribute("value"));
        assertEquals("Jane",wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"last-name\"]"))).getAttribute("value"));
        assertEquals("Jane",wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"email\"]"))).getAttribute("value"));
        assertEquals("Jane",wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"phone\"]"))).getAttribute("value"));
        assertEquals("Jane",wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"address\"]"))).getAttribute("value"));
        assertEquals("Jane",wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"postcode\"]"))).getAttribute("value"));
        assertEquals("Jane",wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"city\"]"))).getAttribute("value"));
        assertEquals("Jane",wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"state\"]"))).getAttribute("value"));
        assertEquals("Jane",wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"country\"]"))).getAttribute("value"));
    }

    @Test
    public void detailsCanBeUpdatedSuccessfully(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"first-name\"]"))).sendKeys("First");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"last-name\"]"))).sendKeys("Last");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"phone\"]"))).sendKeys("1234567");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"address\"]"))).sendKeys("Test Street 100");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"postcode\"]"))).sendKeys("1234");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"city\"]"))).sendKeys("Test City");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"state\"]"))).sendKeys("Test State");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"country\"]"))).sendKeys("Test Country");
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("*[data-test=\"update-profile-submit\"]"))).click();
        assertEquals("Your profile is successfully updated!",wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert"))).getText());
    }

    @Test
    public void emailCannotBeChanged(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"email\"]"))).sendKeys("test");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String emailValue = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"email\"]"))).getAttribute("value");
        assertEquals("customer@practicesoftwaretesting.com",emailValue);
    }


}
