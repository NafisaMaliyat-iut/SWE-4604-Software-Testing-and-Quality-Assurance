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
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class FavoriteAfterLoginTest {
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

    private void loginToAccountWithoutFavorites(){
        driver.get("https://practicesoftwaretesting.com/#/auth/login");
        driver.manage().window().setSize(new Dimension(697, 723));
        driver.findElement(By.cssSelector("*[data-test=\"email\"]")).sendKeys("t68031422@gmail.com");
        driver.findElement(By.cssSelector("*[data-test=\"password\"]")).sendKeys("WelcomeCustomer_01");
        driver.findElement(By.cssSelector("*[data-test=\"login-submit\"]")).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void login(){
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

    @Test
    public void noFavoritesDisplaysProperMessage(){
        loginToAccountWithoutFavorites();
        driver.get("https://practicesoftwaretesting.com/#/account/favorites");
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".col > div")));
        assertEquals("There are no favorites yet. In order to add favorites, please go to the product listing and mark some products as your favorite.",element.getText());
    }

    @Test
    public void displaysFavoritesAfterAddingThem(){
        login();
        driver.get("https://practicesoftwaretesting.com/#/product/01HVZTPSVTWW10JFY9VHF80ZW8");
        driver.manage().window().setSize(new Dimension(1382, 754));
        WebElement addToFavBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"add-to-favorites\"]")));
        addToFavBtn.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.get("https://practicesoftwaretesting.com/#/account/favorites");
        List<WebElement> productElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("*[data-test=\"product-name\"]")));
        List<String> productNames = productElements.stream().map(WebElement::getText).toList();
        assertTrue(productNames.contains("Thor Hammer"));
    }

    @Test
    public void deletesFavoritesSuccessfully(){
        login();
        driver.get("https://practicesoftwaretesting.com/#/product/01HVZY4N3SHVF91SED22SEGYYJ");
        wait.until(ExpectedConditions.urlMatches(".*/#/product/01HVZY4N3SHVF91SED22SEGYYJ"));
        driver.manage().window().setSize(new Dimension(1382, 754));
        WebElement addToFavBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*[data-test=\"add-to-favorites\"]")));
        addToFavBtn.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.get("https://practicesoftwaretesting.com/#/account/favorites");
        List<WebElement> productElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("*[data-test=\"product-name\"]")));

        for (WebElement productElement : productElements) {
            String productName = productElement.getText().trim();
            if (productName.equals("Thor Hammer")) {
                WebElement deleteButton = productElement.findElement(By.xpath("./ancestor::div[contains(@class,'row')]//button[@data-test='delete']"));
                deleteButton.click();
                break;
            }
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<WebElement> productElementsAfterDelete = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("*[data-test=\"product-name\"]")));
        List<String> productNames = productElementsAfterDelete.stream().map(WebElement::getText).toList();
        assertFalse(productNames.contains("Thor Hammer"));
    }
}
