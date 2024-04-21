package org.example;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;

import static org.junit.Assert.*;

public class CheckoutTest {
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
        driver.get("https://practicesoftwaretesting.com/#/product/01HW0JQVH7RDM7Y1RFHVJ1SZ0P");
        wait.until(ExpectedConditions.urlMatches(".*/#/product/01HW0JQVH7RDM7Y1RFHVJ1SZ0P"));
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

    private void proceedToSecondStep(){
        WebElement proceedBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("*[data-test=\"proceed-1\"]")));
        proceedBtn.click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void proceedToThirdStep(){
        WebElement proceedBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("*[data-test=\"proceed-2\"]")));
        proceedBtn.click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void proceedToFourthStep(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test='state']"))).sendKeys("test");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test='postcode']"))).sendKeys("test");
        WebElement proceedBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("*[data-test=\"proceed-3\"]")));
        proceedBtn.click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void cannotCheckoutWithoutLogin(){
        addToCart();
        redirectToCheckout();
        proceedToSecondStep();
        WebElement loginFormHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".col-md-6 > div > h3")));
        assertEquals("Customer login",loginFormHeader.getText());
    }

    @Test
    public void checksOutWhenLoggedIn(){
        login();
        addToCart();
        redirectToCheckout();
        proceedToSecondStep();
        WebElement loggedInMessageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".col-md-6 > p")));
        assertEquals("Hello Jane Doe, you are already logged in. You can proceed to checkout.",loggedInMessageElement.getText());
    }

    @Test
    public void showsBillingFormOnProceeding(){
        login();
        addToCart();
        redirectToCheckout();
        proceedToSecondStep();
        proceedToThirdStep();
        WebElement billingFormHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".login-container h3")));
        assertEquals("Billing Address",billingFormHeader.getText());
    }

    @Test
    public void showsCorrectDetailsOnBillingForm(){
        login();
        addToCart();
        redirectToCheckout();
        proceedToSecondStep();
        proceedToThirdStep();
        WebElement addressField = driver.findElement(By.cssSelector("*[data-test='address']"));
        WebElement cityField = driver.findElement(By.cssSelector("*[data-test='city']"));
        WebElement stateField = driver.findElement(By.cssSelector("*[data-test='state']"));
        WebElement countryField = driver.findElement(By.cssSelector("*[data-test='country']"));
        WebElement postcodeField = driver.findElement(By.cssSelector("*[data-test='postcode']"));
        assertEquals("Test street 98",addressField.getAttribute("value"));
        assertEquals("Vienna",cityField.getAttribute("value"));
        assertEquals("",stateField.getAttribute("value"));
        assertEquals("Austria", countryField.getAttribute("value"));
        assertEquals("",postcodeField.getAttribute("value"));
    }


    @Test
    public void warningForEmptyAddress(){
        login();
        addToCart();
        redirectToCheckout();
        proceedToSecondStep();
        proceedToThirdStep();
        driver.findElement(By.cssSelector("*[data-test='address']")).clear();
        driver.findElement(By.cssSelector("*[data-test='address']")).sendKeys("a" + Keys.BACK_SPACE);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert.alert-danger > div")));
        assertEquals("Address is required.", alert.getText());
    }

    @Test
    public void warningForEmptyCity(){
        login();
        addToCart();
        redirectToCheckout();
        proceedToSecondStep();
        proceedToThirdStep();
        driver.findElement(By.cssSelector("*[data-test='city']")).clear();
        driver.findElement(By.cssSelector("*[data-test='city']")).sendKeys("a" + Keys.BACK_SPACE);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert.alert-danger > div")));
        assertEquals("City is required.", alert.getText());
    }

    @Test
    public void warningForEmptyState(){
        login();
        addToCart();
        redirectToCheckout();
        proceedToSecondStep();
        proceedToThirdStep();
        driver.findElement(By.cssSelector("*[data-test='state']")).clear();
        driver.findElement(By.cssSelector("*[data-test='state']")).sendKeys("a" + Keys.BACK_SPACE);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert.alert-danger > div")));
        assertEquals("State is required.", alert.getText());
    }

    @Test
    public void warningForEmptyCountry(){
        login();
        addToCart();
        redirectToCheckout();
        proceedToSecondStep();
        proceedToThirdStep();
        driver.findElement(By.cssSelector("*[data-test='country']")).clear();
        driver.findElement(By.cssSelector("*[data-test='country']")).sendKeys("a" + Keys.BACK_SPACE);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert.alert-danger > div")));
        assertEquals("Country is required.", alert.getText());
    }

    @Test
    public void warningForEmptyPostcode(){
        login();
        addToCart();
        redirectToCheckout();
        proceedToSecondStep();
        proceedToThirdStep();
        driver.findElement(By.cssSelector("*[data-test='postcode']")).clear();
        driver.findElement(By.cssSelector("*[data-test='postcode']")).sendKeys("a" + Keys.BACK_SPACE);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert.alert-danger > div")));
        assertEquals("Postalcode is required.", alert.getText());
    }

    @Test
    public void proceedBtnDisabledUntilFieldsFilled(){
        login();
        addToCart();
        redirectToCheckout();
        proceedToSecondStep();
        proceedToThirdStep();
        List<WebElement> inputFields = new ArrayList<>();
        inputFields.add(driver.findElement(By.cssSelector("*[data-test='address']")));
        inputFields.add(driver.findElement(By.cssSelector("*[data-test='city']")));
        inputFields.add(driver.findElement(By.cssSelector("*[data-test='state']")));
        inputFields.add(driver.findElement(By.cssSelector("*[data-test='country']")));
        for (WebElement field : inputFields) {
            field.clear();
            field.sendKeys("test");
            WebElement proceedButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[data-test='proceed-3']")));
            assertFalse(proceedButton.isEnabled());
        }
        WebElement postcodeField = driver.findElement(By.cssSelector("*[data-test='postcode']"));
        postcodeField.clear();
        postcodeField.sendKeys("test");
        WebElement proceedButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[data-test='proceed-3']")));
        assertTrue(proceedButton.isEnabled());
    }

    @Test
    public void showsErrorIfPaymentMethodIsEmpty(){
        login();
        addToCart();
        redirectToCheckout();
        proceedToSecondStep();
        proceedToThirdStep();
        proceedToFourthStep();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#payment-method"))).click();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("app-payment h3"))).click();
        WebElement alertMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert.alert-danger > div")));
        assertEquals("Payment method is required.", alertMessage.getText());
    }

    @Test
    public void showsErrorForInvalidCardNumber() {
        login();
        addToCart();
        redirectToCheckout();
        proceedToSecondStep();
        proceedToThirdStep();
        proceedToFourthStep();
        {
            WebElement dropdown = driver.findElement(By.cssSelector("*[data-test=\"payment-method\"]"));
            dropdown.findElement(By.xpath("//option[. = 'Credit Card']")).click();
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"credit_card_number\"]"))).sendKeys("abc");
        assertEquals("Invalid card number format.",wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert > div"))).getText());
    }

    @Test
    public void showsWarningForInvalidDate(){
        login();
        addToCart();
        redirectToCheckout();
        proceedToSecondStep();
        proceedToThirdStep();
        proceedToFourthStep();
        {
            WebElement dropdown = driver.findElement(By.cssSelector("*[data-test=\"payment-method\"]"));
            dropdown.findElement(By.xpath("//option[. = 'Credit Card']")).click();
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"expiration_date\"]"))).sendKeys("abc");
        assertEquals("Invalid expiration date format. Use MM/YYYY.",wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert > div"))).getText());
    }

    @Test
    public void showsWarningForExpiredDate(){
        login();
        addToCart();
        redirectToCheckout();
        proceedToSecondStep();
        proceedToThirdStep();
        proceedToFourthStep();
        {
            WebElement dropdown = driver.findElement(By.cssSelector("*[data-test=\"payment-method\"]"));
            dropdown.findElement(By.xpath("//option[. = 'Credit Card']")).click();
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"expiration_date\"]"))).sendKeys("12/2023");
        assertEquals("Expiration date must be in the future.",wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert > div"))).getText());
    }

    @Test
    public void showsWarningForInvalidMonth(){
        login();
        addToCart();
        redirectToCheckout();
        proceedToSecondStep();
        proceedToThirdStep();
        proceedToFourthStep();
        {
            WebElement dropdown = driver.findElement(By.cssSelector("*[data-test=\"payment-method\"]"));
            dropdown.findElement(By.xpath("//option[. = 'Credit Card']")).click();
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"expiration_date\"]"))).sendKeys("15/2023");
        assertEquals("Invalid expiration date format. Use MM/YYYY.",wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert > div"))).getText());
    }

    @Test
    public void showsErrorForInvalidCVVLength(){
        login();
        addToCart();
        redirectToCheckout();
        proceedToSecondStep();
        proceedToThirdStep();
        proceedToFourthStep();
        {
            WebElement dropdown = driver.findElement(By.cssSelector("*[data-test=\"payment-method\"]"));
            dropdown.findElement(By.xpath("//option[. = 'Credit Card']")).click();
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"cvv\"]"))).sendKeys("1");
        assertEquals("CVV must be 3 or 4 digits.",wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert > div"))).getText());
    }

    @Test
    public void showsErrorForInvalidCVVFormat(){
        login();
        addToCart();
        redirectToCheckout();
        proceedToSecondStep();
        proceedToThirdStep();
        proceedToFourthStep();
        {
            WebElement dropdown = driver.findElement(By.cssSelector("*[data-test=\"payment-method\"]"));
            dropdown.findElement(By.xpath("//option[. = 'Credit Card']")).click();
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"cvv\"]"))).sendKeys("test");
        assertEquals("CVV must be 3 or 4 digits.",wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert > div"))).getText());
    }

    @Test
    public void submissionShowsSuccessMessage(){
        login();
        addToCart();
        redirectToCheckout();
        proceedToSecondStep();
        proceedToThirdStep();
        proceedToFourthStep();
        {
            WebElement dropdown = driver.findElement(By.cssSelector("*[data-test=\"payment-method\"]"));
            dropdown.findElement(By.xpath("//option[. = 'Credit Card']")).click();
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"expiration_date\"]"))).sendKeys("11/2026");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"credit_card_number\"]"))).sendKeys("1234-1234-1234-1234");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"cvv\"]"))).sendKeys("1234");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"card_holder_name\"]"))).sendKeys("Jane");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"finish\"]"))).click();
        assertEquals("Payment was successful",wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".help-block"))).getText());
    }
}

