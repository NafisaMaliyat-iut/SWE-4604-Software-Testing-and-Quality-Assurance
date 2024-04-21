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

public class ContactFormTest {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;
    private WebDriverWait wait;
    @Before
    public void setUp() {
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
        driver.get("https://practicesoftwaretesting.com/#/contact");
        driver.manage().window().setSize(new Dimension(1382, 754));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    @After
    public void tearDown() {
        driver.quit();
    }
    @Test
    public void allFieldsEmptyShowsWarningMessage() {
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("*[data-test=\"contact-submit\"]")));
        submitButton.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement firstNameAlert = driver.findElement(By.cssSelector("#first_name_alert > div"));
        assertNotNull(firstNameAlert);

        WebElement lastNameAlert = driver.findElement(By.cssSelector("#last_name_alert > div"));
        assertNotNull(lastNameAlert);

        WebElement emailAlert = driver.findElement(By.cssSelector("#email_alert > div"));
        assertNotNull(emailAlert);

        WebElement subjectAlert = driver.findElement(By.cssSelector("#subject_alert > div"));
        assertNotNull(subjectAlert);

        WebElement messageAlert = driver.findElement(By.cssSelector("#message_alert > div"));
        assertNotNull(messageAlert);
    }

    @Test
    public void validSubmissionWithoutFileMessageShows(){
        driver.findElement(By.cssSelector("*[data-test=\"first-name\"]")).sendKeys("Nafisa");
        driver.findElement(By.cssSelector("*[data-test=\"last-name\"]")).sendKeys("Maliyat");
        driver.findElement(By.cssSelector("*[data-test=\"email\"]")).sendKeys("nafisamaliyat@iut-dhaka.edu");
        {
            WebElement dropdown = driver.findElement(By.cssSelector("*[data-test=\"subject\"]"));
            dropdown.findElement(By.xpath("//option[. = 'Webmaster']")).click();
        }
        driver.findElement(By.cssSelector("*[data-test=\"message\"]")).click();
        driver.findElement(By.cssSelector("*[data-test=\"message\"]")).sendKeys("asdsadsadsadadasdsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
        driver.findElement(By.cssSelector("*[data-test=\"contact-submit\"]")).click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String message = driver.findElement(By.cssSelector(".alert")).getText();
        assertEquals("Thanks for your message! We will contact you shortly.",message);
    }

    @Test
    public void invalidFileSizeWarningMessageShows(){
        driver.findElement(By.cssSelector("*[data-test=\"first-name\"]")).sendKeys("Nafisa");
        driver.findElement(By.cssSelector("*[data-test=\"last-name\"]")).sendKeys("Maliyat");
        driver.findElement(By.cssSelector("*[data-test=\"email\"]")).sendKeys("nafisamaliyat@iut-dhaka.edu");
        {
            WebElement dropdown = driver.findElement(By.cssSelector("*[data-test=\"subject\"]"));
            dropdown.findElement(By.xpath("//option[. = 'Webmaster']")).click();
        }
        driver.findElement(By.cssSelector("*[data-test=\"message\"]")).click();
        driver.findElement(By.cssSelector("*[data-test=\"message\"]")).sendKeys("asdsadsadsadadasdsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
        driver.findElement(By.cssSelector("*[data-test=\"attachment\"]")).sendKeys(System.getProperty("user.dir") + "\\20042133_lab7.java");
        driver.findElement(By.cssSelector("*[data-test=\"contact-submit\"]")).click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String message = driver.findElement(By.cssSelector("#attachment_alert > div")).getText();
        assertEquals("File should be empty.",message);
    }

    @Test
    public void invalidFileExtensionWarningMessageShows(){
        driver.findElement(By.cssSelector("*[data-test=\"first-name\"]")).sendKeys("Nafisa");
        driver.findElement(By.cssSelector("*[data-test=\"last-name\"]")).sendKeys("Maliyat");
        driver.findElement(By.cssSelector("*[data-test=\"email\"]")).sendKeys("nafisamaliyat@iut-dhaka.edu");
        {
            WebElement dropdown = driver.findElement(By.cssSelector("*[data-test=\"subject\"]"));
            dropdown.findElement(By.xpath("//option[. = 'Webmaster']")).click();
        }
        driver.findElement(By.cssSelector("*[data-test=\"message\"]")).click();
        driver.findElement(By.cssSelector("*[data-test=\"message\"]")).sendKeys("asdsadsadsadadasdsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
        driver.findElement(By.cssSelector("*[data-test=\"attachment\"]")).sendKeys(System.getProperty("user.dir") + "\\20042133_lab7.java");
        driver.findElement(By.cssSelector("*[data-test=\"contact-submit\"]")).click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String message = driver.findElement(By.cssSelector("#attachment_alert > div")).getText();
        assertEquals("File should be empty.",message);
    }
    @Test
    public void validSubmissionWithFileMessageShows(){
        driver.findElement(By.cssSelector("*[data-test=\"first-name\"]")).sendKeys("Nafisa");
        driver.findElement(By.cssSelector("*[data-test=\"last-name\"]")).sendKeys("Maliyat");
        driver.findElement(By.cssSelector("*[data-test=\"email\"]")).sendKeys("nafisamaliyat@iut-dhaka.edu");
        {
            WebElement dropdown = driver.findElement(By.cssSelector("*[data-test=\"subject\"]"));
            dropdown.findElement(By.xpath("//option[. = 'Webmaster']")).click();
        }
        driver.findElement(By.cssSelector("*[data-test=\"message\"]")).click();
        driver.findElement(By.cssSelector("*[data-test=\"message\"]")).sendKeys("asdsadsadsadadasdsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
        driver.findElement(By.cssSelector("*[data-test=\"attachment\"]")).sendKeys(System.getProperty("user.dir") + "\\a.txt");
        driver.findElement(By.cssSelector("*[data-test=\"contact-submit\"]")).click();

        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String message = driver.findElement(By.cssSelector(".alert")).getText();
        assertEquals("Thanks for your message! We will contact you shortly.",message);
    }
    @Test
    public void messageLengthUnder50WarningMessageShows(){
        driver.findElement(By.cssSelector("*[data-test=\"first-name\"]")).sendKeys("Nafisa");
        driver.findElement(By.cssSelector("*[data-test=\"last-name\"]")).sendKeys("Maliyat");
        driver.findElement(By.cssSelector("*[data-test=\"email\"]")).sendKeys("nafisamaliyat@iut-dhaka.edu");
        {
            WebElement dropdown = driver.findElement(By.cssSelector("*[data-test=\"subject\"]"));
            dropdown.findElement(By.xpath("//option[. = 'Webmaster']")).click();
        }
        driver.findElement(By.cssSelector("*[data-test=\"message\"]")).click();
        driver.findElement(By.cssSelector("*[data-test=\"message\"]")).sendKeys("asdasdsadsafafd");
        driver.findElement(By.cssSelector("*[data-test=\"contact-submit\"]")).click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String message = driver.findElement(By.cssSelector("#message_alert > div")).getText();
        assertEquals("Message must be minimal 50 characters",message);
    }

    @Test
    public void incorrectEmailFormatShowsWarning() {
        driver.findElement(By.cssSelector("*[data-test=\"first-name\"]")).sendKeys("Nafisa");
        driver.findElement(By.cssSelector("*[data-test=\"last-name\"]")).sendKeys("Maliyat");
        driver.findElement(By.cssSelector("*[data-test=\"email\"]")).sendKeys("invalid");
        {
            WebElement dropdown = driver.findElement(By.cssSelector("*[data-test=\"subject\"]"));
            dropdown.findElement(By.xpath("//option[. = 'Webmaster']")).click();
        }
        driver.findElement(By.cssSelector("*[data-test=\"message\"]")).click();
        driver.findElement(By.cssSelector("*[data-test=\"message\"]")).sendKeys("asdsadsadsadadasdsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
        driver.findElement(By.cssSelector("*[data-test=\"contact-submit\"]")).click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String message = driver.findElement(By.cssSelector("#email_alert")).getText();
        assertEquals("Email format is invalid",message);
    }
}
