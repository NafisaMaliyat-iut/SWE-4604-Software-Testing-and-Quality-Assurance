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
public class ChangePasswordTest {
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
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.get("https://practicesoftwaretesting.com/#/account/profile");
        wait.until(ExpectedConditions.urlMatches(".*/#/account/profile"));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void cleanUp(){
        login();
        redirectToProfilePage();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"current-password\"]"))).sendKeys("WelcomeCustomer_0987");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"new-password\"]"))).sendKeys("welcome01");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"new-password-confirm\"]"))).sendKeys("welcome01");
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("*[data-test=\"change-password-submit\"]"))).click();
    }

    @Test
    public void passwordChangeGivesSuccessMessage(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"current-password\"]"))).sendKeys("welcome01");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"new-password\"]"))).sendKeys("WelcomeCustomer_0987");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"new-password-confirm\"]"))).sendKeys("WelcomeCustomer_0987");
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("*[data-test=\"change-password-submit\"]"))).click();
        assertEquals("Your password is successfully updated!",wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert"))).getText());
        cleanUp();
    }

    @Test
    public void wrongCurrentPasswordGivesWarning(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"current-password\"]"))).sendKeys("123");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"new-password\"]"))).sendKeys("Welcome_22");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"new-password-confirm\"]"))).sendKeys("Welcome_22");
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("*[data-test=\"change-password-submit\"]"))).click();
        assertEquals("Your current password does not matches with the password.",wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert"))).getText());

    }

    @Test
    public void nonMatchingNewPasswordsGiveWarning(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"current-password\"]"))).sendKeys("welcome01");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"new-password\"]"))).sendKeys("Welcome_0987");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"new-password-confirm\"]"))).sendKeys("Welcome_0987");
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("*[data-test=\"change-password-submit\"]"))).click();
        assertEquals("The new password field confirmation does not match.",wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert"))).getText());
    }

    @Test
    public void wrongPasswordLengthGivesWarning(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"current-password\"]"))).sendKeys("welcome01");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"new-password\"]"))).sendKeys("123");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"new-password-confirm\"]"))).sendKeys("123");
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("*[data-test=\"change-password-submit\"]"))).click();
        assertEquals("The new password field must be at least 8 characters. (and 2 more errors)",wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert"))).getText());
    }

    @Test
    public void wrongPasswordFormatWithOnlyLowercaseGivesWarning(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"current-password\"]"))).sendKeys("welcome01");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"new-password\"]"))).sendKeys("welcomee");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"new-password-confirm\"]"))).sendKeys("welcomee");
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("*[data-test=\"change-password-submit\"]"))).click();
        assertEquals("The new password field must contain at least one uppercase and one lowercase letter. (and 2 more errors)",wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert"))).getText());
    }
    @Test
    public void wrongPasswordFormatWithOnlyUppercaseAndLowercaseGivesWarning(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"current-password\"]"))).sendKeys("welcome01");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"new-password\"]"))).sendKeys("Welcomee");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"new-password-confirm\"]"))).sendKeys("Welcomee");
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("*[data-test=\"change-password-submit\"]"))).click();
        assertEquals("The new password field must contain at least one symbol. (and 1 more error)",wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert"))).getText());
    }

    @Test
    public void wrongPasswordFormatWithWithoutNumberAndSymbolGivesWarning(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"current-password\"]"))).sendKeys("welcome01");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"new-password\"]"))).sendKeys("Welcomee");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"new-password-confirm\"]"))).sendKeys("Welcomee");
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("*[data-test=\"change-password-submit\"]"))).click();
        assertEquals("The new password field must contain at least one symbol. (and 1 more error)",wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert"))).getText());
    }

    @Test
    public void wrongPasswordFormatWithWithoutNumberGivesWarning(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"current-password\"]"))).sendKeys("welcome");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"new-password\"]"))).sendKeys("Welcomee_");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"new-password-confirm\"]"))).sendKeys("Welcomee_");
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("*[data-test=\"change-password-submit\"]"))).click();
        assertEquals("The new password field must contain at least one number.",wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert"))).getText());
    }

    @Test
    public void wrongPasswordFormatWithUppercaseOnlyGivesWarning(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"current-password\"]"))).sendKeys("welcome01");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"new-password\"]"))).sendKeys("WELCOME_01");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"new-password-confirm\"]"))).sendKeys("WELCOME_01");
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("*[data-test=\"change-password-submit\"]"))).click();
        assertEquals("TThe new password field must contain at least one uppercase and one lowercase letter.",wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert"))).getText());
    }
}
