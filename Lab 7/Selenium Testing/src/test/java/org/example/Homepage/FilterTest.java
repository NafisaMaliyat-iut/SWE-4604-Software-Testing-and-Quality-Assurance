package org.example.Homepage;

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

import static org.junit.Assert.assertTrue;

public class FilterTest {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;
    @Before
    public void setUp() {
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
        driver.get("https://practicesoftwaretesting.com/");
        driver.manage().window().setSize(new Dimension(1382, 754));
    }
    @After
    public void tearDown() {
        driver.quit();
    }
    @Test
    public void filterByHammers() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("*[data-test=\"category-01HVSKJQK75AYECPNBR5QXAKQY\"]")));
        element.click();
        //wait for sorting to finish
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<WebElement> productElements = driver.findElements(By.cssSelector("*[data-test=\"product-name\"]"));
        List<String> productNames = productElements.stream().map(WebElement::getText).toList();

        for (String productName : productNames) {
            assertTrue(productName.toLowerCase().contains("hammer".toLowerCase()));
        }
    }

    @Test
    public void filterByOtherSelectsAllSubCategories() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement otherCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("*[data-test=\"category-01HVSQ0KNPH0MKWDM1RKDNK6F3\"]")));
        otherCheckbox.click();

        //wait for sorting to finish
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<WebElement> childCheckboxes = driver.findElements(By.cssSelector(".checkbox:nth-child(15) input[type='checkbox']"));

        boolean allChildCheckboxesChecked = true;
        for (WebElement checkbox : childCheckboxes) {
            if (!checkbox.isSelected()) {
                allChildCheckboxesChecked = false;
                break;
            }
        }

        assertTrue(allChildCheckboxesChecked);
    }


}
