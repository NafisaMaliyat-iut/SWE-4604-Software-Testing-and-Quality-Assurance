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

public class LoginFormTest {
    private WebDriver driver;
    private WebDriverWait wait;
    JavascriptExecutor js;
    @Before
    public void setUp() {
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        Map<String, Object> vars = new HashMap<String, Object>();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void loginWithValidCredentials() {
        driver.get("https://practicesoftwaretesting.com/#/auth/login");
        driver.manage().window().setSize(new Dimension(697, 723));
        driver.findElement(By.cssSelector("*[data-test=\"email\"]")).sendKeys("customer@practicesoftwaretesting.com");
        driver.findElement(By.cssSelector("*[data-test=\"password\"]")).sendKeys("welcome01");
        driver.findElement(By.cssSelector("*[data-test=\"login-submit\"]")).click();

        wait.until(ExpectedConditions.urlMatches(".*/#/account"));

        String currentUrl = driver.getCurrentUrl();
        assertEquals("https://practicesoftwaretesting.com/#/account", currentUrl);
    }


    @Test
    public void loginWithWrongPassword() {
        driver.get("https://practicesoftwaretesting.com/#/auth/login");
        driver.manage().window().setSize(new Dimension(697, 723));
        driver.findElement(By.cssSelector("*[data-test=\"email\"]")).sendKeys("customer@practicesoftwaretesting.com");
        driver.findElement(By.cssSelector("*[data-test=\"password\"]")).sendKeys("1234");
        driver.findElement(By.cssSelector("*[data-test=\"login-submit\"]")).click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        js.executeScript("window.scrollTo(0,100)");
        String message = driver.findElement(By.cssSelector(".help-block")).getText();
        assertEquals("Invalid email or password",message);
    }

    @Test
    public void loginWithWrongEmail() {
        driver.get("https://practicesoftwaretesting.com/#/auth/login");
        driver.manage().window().setSize(new Dimension(697, 723));
        driver.findElement(By.cssSelector("*[data-test=\"email\"]")).sendKeys("customer@wrong.com");
        driver.findElement(By.cssSelector("*[data-test=\"password\"]")).sendKeys("welcome01");
        driver.findElement(By.cssSelector("*[data-test=\"login-submit\"]")).click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        js.executeScript("window.scrollTo(0,100)");
        String message = driver.findElement(By.cssSelector(".help-block")).getText();
        assertEquals("Invalid email or password",message);
    }

    @Test
    public void loginWithWrongEmailFormat() {
        driver.get("https://practicesoftwaretesting.com/#/auth/login");
        driver.manage().window().setSize(new Dimension(697, 723));
        driver.findElement(By.cssSelector("*[data-test=\"email\"]")).sendKeys("customer");
        driver.findElement(By.cssSelector("*[data-test=\"password\"]")).sendKeys("welcome01");
        driver.findElement(By.cssSelector("*[data-test=\"login-submit\"]")).click();

        js.executeScript("window.scrollTo(0,100)");
        String message = driver.findElement(By.cssSelector(".alert > div")).getText();
        assertEquals("E-mail format is invalid.",message);
    }

    @Test
    public void loginWithEmptyFormShowsErrorForEachField() {
        driver.get("https://practicesoftwaretesting.com/#/auth/login");
        driver.manage().window().setSize(new Dimension(697, 723));
        driver.findElement(By.cssSelector("*[data-test=\"login-submit\"]")).click();

        js.executeScript("window.scrollTo(0,100)");
        String emailWarning = driver.findElement(By.cssSelector("*[data-test=\"email-error\"]")).getText();
        assertEquals("E-mail is required.",emailWarning);

        String passwordWarning = driver.findElement(By.cssSelector("*[data-test=\"password-error\"]")).getText();
        assertEquals("Password is required.",passwordWarning);
    }
}
