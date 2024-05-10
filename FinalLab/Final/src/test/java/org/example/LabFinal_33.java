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
public class LabFinal_33 {
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
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    private void login(){
        driver.get("https://thinking-tester-contact-list.herokuapp.com/");
        driver.manage().window().setSize(new Dimension(697, 692));
        driver.findElement(By.id("email")).click();
        driver.findElement(By.id("email")).sendKeys("nafisa_133@user.com");
        driver.findElement(By.id("password")).sendKeys("1234567");
        driver.findElement(By.id("submit")).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        wait.until(ExpectedConditions.urlMatches("/contactList"));
    }

    @Test
    public void SignUp1(){
        driver.get("https://thinking-tester-contact-list.herokuapp.com/addUser");
        driver.manage().window().setSize(new Dimension(697, 692));
        driver.findElement(By.id("firstName")).click();
        driver.findElement(By.id("firstName")).sendKeys("Nafisa");
        driver.findElement(By.id("lastName")).sendKeys("Maliyat");
        driver.findElement(By.id("email")).sendKeys("nafisa_133");
        driver.findElement(By.id("password")).sendKeys("1234567");
        driver.findElement(By.id("submit")).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error")));
        assertTrue(message.getText().contains("Email is invalid"));
    }

    @Test
    public void SignUp2(){
        driver.get("https://thinking-tester-contact-list.herokuapp.com/addUser");
        driver.manage().window().setSize(new Dimension(697, 692));
        driver.findElement(By.id("firstName")).click();
        driver.findElement(By.id("firstName")).sendKeys("Nafisa");
        driver.findElement(By.id("lastName")).sendKeys("Maliyat");
        driver.findElement(By.id("email")).sendKeys("nafisa_133@user.com");
        driver.findElement(By.id("password")).sendKeys("12345");
        driver.findElement(By.id("submit")).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error")));
        assertTrue(message.getText().contains("is shorter than the minimum allowed length (7)."));
    }

    @Test
    public void SignUp3(){
        driver.get("https://thinking-tester-contact-list.herokuapp.com/addUser");
        driver.manage().window().setSize(new Dimension(697, 692));
        driver.findElement(By.id("firstName")).click();
        driver.findElement(By.id("firstName")).sendKeys("Nafisa");
        driver.findElement(By.id("lastName")).sendKeys("Maliyat");
        driver.findElement(By.id("email")).sendKeys("nafisa_133@user.com");
        driver.findElement(By.id("password")).sendKeys("1234567");
        driver.findElement(By.id("submit")).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        wait.until(ExpectedConditions.urlMatches("/contactList"));

        String currentUrl = driver.getCurrentUrl();
        assertEquals("https://thinking-tester-contact-list.herokuapp.com/contactList", currentUrl);
    }

    @Test
    public void LogIn1(){
        driver.get("https://thinking-tester-contact-list.herokuapp.com/");
        driver.manage().window().setSize(new Dimension(697, 692));
        driver.findElement(By.id("email")).click();
        driver.findElement(By.id("email")).sendKeys("nafisa_133@user.com");
        driver.findElement(By.id("password")).sendKeys("12345");
        driver.findElement(By.id("submit")).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error")));
        assertTrue(message.getText().contains("Incorrect username or password"));

    }

    @Test
    public void LogIn2(){
        driver.get("https://thinking-tester-contact-list.herokuapp.com/");
        driver.manage().window().setSize(new Dimension(697, 692));
        driver.findElement(By.id("email")).click();
        driver.findElement(By.id("email")).sendKeys("nafisa_133@user.com");
        driver.findElement(By.id("password")).sendKeys("1234567");
        driver.findElement(By.id("submit")).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        wait.until(ExpectedConditions.urlMatches("/contactList"));

        String currentUrl = driver.getCurrentUrl();
        assertEquals("https://thinking-tester-contact-list.herokuapp.com/contactList", currentUrl);
    }

    @Test
    public void ContactForm1(){
        login();
        driver.get("https://thinking-tester-contact-list.herokuapp.com/contactList");
        driver.manage().window().setSize(new Dimension(697, 714));
        driver.findElement(By.id("add-contact")).click();
        driver.findElement(By.id("firstName")).sendKeys("");
        driver.findElement(By.id("lastName")).sendKeys("Maliyat");
        driver.findElement(By.id("phone")).sendKeys("231231231");
        driver.findElement(By.id("street1")).sendKeys("Shahjadpur, Gulshan, Dhaka");
        driver.findElement(By.id("city")).sendKeys("Dhaka");
        driver.findElement(By.id("postalCode")).sendKeys("1212");
        driver.findElement(By.id("birthdate")).sendKeys("2001-04-06");
        driver.findElement(By.id("country")).sendKeys("Bangladesh");
        driver.findElement(By.cssSelector("#add-contact > p:nth-child(1)")).click();
        driver.findElement(By.id("submit")).click();
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error")));
        assertTrue(message.getText().contains("Contact validation failed: firstName: Path `firstName` is required"));
    }
    @Test
    public void ContactForm2(){
        login();
        driver.get("https://thinking-tester-contact-list.herokuapp.com/contactList");
        driver.manage().window().setSize(new Dimension(697, 714));
        driver.findElement(By.id("add-contact")).click();
        driver.findElement(By.id("firstName")).sendKeys("Nafisa");
        driver.findElement(By.id("lastName")).sendKeys("Maliyat");
        driver.findElement(By.id("phone")).sendKeys("231231231");
        driver.findElement(By.id("street1")).sendKeys("Shahjadpur, Gulshan, Dhaka");
        driver.findElement(By.id("city")).sendKeys("Dhaka");
        driver.findElement(By.id("postalCode")).sendKeys("1212");
        driver.findElement(By.id("country")).sendKeys("Bangladesh");
        driver.findElement(By.id("birthdate")).sendKeys("04-06-2001");
        driver.findElement(By.cssSelector("#add-contact > p:nth-child(1)")).click();
        driver.findElement(By.id("submit")).click();
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error")));
        assertTrue(message.getText().contains("Contact validation failed: birthdate: Birthdate is invalid"));
    }
    @Test
    public void ContactForm3(){
        login();
        driver.get("https://thinking-tester-contact-list.herokuapp.com/contactList");
        driver.manage().window().setSize(new Dimension(697, 714));
        driver.findElement(By.id("add-contact")).click();
        driver.findElement(By.id("firstName")).sendKeys("Nafisa");
        driver.findElement(By.id("lastName")).sendKeys("Maliyat");
        driver.findElement(By.id("phone")).sendKeys("231231231");
        driver.findElement(By.id("street1")).sendKeys("Shahjadpur, Gulshan, Dhaka");
        driver.findElement(By.id("city")).sendKeys("Dhaka");
        driver.findElement(By.id("postalCode")).sendKeys("1212");
        driver.findElement(By.id("country")).sendKeys("Bangladesh");
        driver.findElement(By.id("birthdate")).sendKeys("2001-06-04");
        driver.findElement(By.cssSelector("#add-contact > p:nth-child(1)")).click();
        driver.findElement(By.id("submit")).click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement content = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".main-content")));
        String expected = "First Name: Nafisa\n" +
                "\n" +
                "Last Name: Maliyat\n" +
                "\n" +
                "Date of Birth: 2001-04-06\n" +
                "\n" +
                "Email:\n" +
                "\n" +
                "Phone: 231231231\n" +
                "\n" +
                "Street Address 1: Shahjadpur, Gulshan, Dhaka\n" +
                "\n" +
                "Street Address 2:\n" +
                "\n" +
                "City: Dhaka\n" +
                "\n" +
                "State or Province:\n" +
                "\n" +
                "Postal Code: 1212\n" +
                "\n" +
                "Country: Bangladesh";
        assertTrue(content.getText().contains(expected));
    }

    @Test
    public void LogOut(){
        login();
        driver.get("https://thinking-tester-contact-list.herokuapp.com/contactList");
        driver.manage().window().setSize(new Dimension(697, 714));
        driver.findElement(By.id("logout")).click();
        wait.until(ExpectedConditions.urlMatches("/"));

        String currentUrl = driver.getCurrentUrl();
        assertEquals("https://thinking-tester-contact-list.herokuapp.com/", currentUrl);

    }

}
