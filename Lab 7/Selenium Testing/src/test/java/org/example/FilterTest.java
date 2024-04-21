package org.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FilterTest {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;
    private WebDriverWait wait;
    @Before
    public void setUp() {
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
        driver.get("https://practicesoftwaretesting.com/");
        driver.manage().window().setSize(new Dimension(1382, 754));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    @After
    public void tearDown() {
        driver.quit();
    }
    @Test
    public void filterByHammers() {
        js.executeScript("window.scrollTo(0,400)");
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("*[data-test=\"category-01HVTSB7B8SJ1EST2YH3DYM9A1\"]")));
        element.click();

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
        js.executeScript("window.scrollTo(0,600)");
        WebElement otherCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".checkbox:nth-child(15) > label")));
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

    @Test
    public void correctItemsAreFilteredForHandTools(){
        js.executeScript("window.scrollTo(0,400)");
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("*[data-test=\"category-01HVTSB7B43CRRXZCVY6G7FVTB\"]")));
        element.click();
        //wait for sorting to finish
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<WebElement> productElements = driver.findElements(By.cssSelector("*[data-test=\"product-name\"]"));
        List<String> productNames = productElements.stream().map(WebElement::getText).toList();

        List<String> expected = List.of("Claw Hammer with Shock Reduction Grip", "Hammer", "Claw Hammer", "Thor Hammer", "Sledgehammer", "Claw Hammer with Fiberglass Handle", "Court Hammer", "Wood Saw", "Adjustable Wrench");

        assertEquals(expected,productNames);
    }
    @Test
    public void correctItemsAreFilteredForPowerTools(){
        js.executeScript("window.scrollTo(0,400)");
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("*[data-test=\"category-01HVTSB7B43CRRXZCVY6G7FVTC\"]")));
        element.click();
        //wait for sorting to finish
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<WebElement> productElements = driver.findElements(By.cssSelector("*[data-test=\"product-name\"]"));
        List<String> productNames = productElements.stream().map(WebElement::getText).toList();

        List<String> expected = List.of("Sheet Sander","Belt Sander","Circular Saw", "Cordless Drill 24V","Cordless Drill 12V");
//
        assertEquals(expected,productNames);
    }


    @Test
    public void correctItemsAreFilteredForOther(){
        js.executeScript("window.scrollTo(0,400)");
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("*[data-test=\"category-01HVTSB7B43CRRXZCVY6G7FVTD\"]")));
        element.click();
        //wait for sorting to finish
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<WebElement> productElements = driver.findElements(By.cssSelector("*[data-test=\"product-name\"]"));
        List<String> productNames = productElements.stream().map(WebElement::getText).toList();

        List<String> expected = List.of("Leather toolbelt","Drawer Tool Cabinet","Tool Cabinet","Safety Goggles",
                "Safety Helmet Face Shield","Protective Gloves","Super-thin Protection Gloves",
                "Construction Helmet","Ear Protection");

        assertEquals(expected,productNames);
    }

    @Test
    public void correctNumberOfItemsAreFilteredForHandAndPowerTools() {
        js.executeScript("window.scrollTo(0,400)");
        WebElement powerToolsCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("*[data-test=\"category-01HVTWS31XEBTFP4F3TRSD3YB6\"]")));
        powerToolsCheckbox.click();
        WebElement handToolsCheckbox = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("*[data-test=\"category-01HVTWS31XEBTFP4F3TRSD3YB7\"]")));
        handToolsCheckbox.click();

        List<String> allProductNames = new ArrayList<>();


        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<WebElement> productElements = driver.findElements(By.cssSelector("*[data-test=\"product-name\"]"));
            List<String> productNamesOnPage = productElements.stream().map(WebElement::getText).toList();

            allProductNames.addAll(productNamesOnPage);

            WebElement nextPageLink = driver.findElement(By.cssSelector(".pagination .page-item.active + .page-item a.page-link"));
            nextPageLink.click();

        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<WebElement> productElements = driver.findElements(By.cssSelector("*[data-test=\"product-name\"]"));
        List<String> productNamesOnPage = productElements.stream().map(WebElement::getText).toList();

        allProductNames.addAll(productNamesOnPage);
        assertEquals(30,allProductNames.size());
    }


    @Test
    public void correctNumberOfItemsAreFilteredForForgeFlexTools() {
        js.executeScript("window.scrollTo(0,400)");
        WebElement forgeFlex = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("*[data-test=\"brand-01HVTWS31GYW27RW0MQ3YJ65N1\"]")));
        forgeFlex.click();

        List<String> allProductNames = new ArrayList<>();


        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<WebElement> productElements = driver.findElements(By.cssSelector("*[data-test=\"product-name\"]"));
            List<String> productNamesOnPage = productElements.stream().map(WebElement::getText).toList();

            allProductNames.addAll(productNamesOnPage);

            WebElement nextPageLink = driver.findElement(By.cssSelector(".pagination .page-item.active + .page-item a.page-link"));
            nextPageLink.click();

        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<WebElement> productElements = driver.findElements(By.cssSelector("*[data-test=\"product-name\"]"));
        List<String> productNamesOnPage = productElements.stream().map(WebElement::getText).toList();

        allProductNames.addAll(productNamesOnPage);
        assertEquals(36,allProductNames.size());
    }

    @Test
    public void correctNumberOfItemsAreFilteredForMightyCraft() {
        js.executeScript("window.scrollTo(0,400)");
        WebElement mightyCraft = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".checkbox:nth-child(19) > label")));
        mightyCraft.click();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<WebElement> productElements = driver.findElements(By.cssSelector("*[data-test=\"product-name\"]"));
        List<String> productNamesOnPage = productElements.stream().map(WebElement::getText).toList();

        List<String> allProductNames = new ArrayList<>(productNamesOnPage);
        assertEquals(9,allProductNames.size());
    }

    @Test
    public void correctItemsAreFilteredForForgeFlexTools(){
        js.executeScript("window.scrollTo(0,400)");
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("*[data-test=\"brand-01HVTWS31GYW27RW0MQ3YJ65N1\"]")));
        element.click();
        //wait for sorting to finish
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<WebElement> productElements = driver.findElements(By.cssSelector("*[data-test=\"product-name\"]"));
        List<String> productNames = productElements.stream().map(WebElement::getText).toList();

        List<String> expected = List.of("Combination Pliers","Pliers","Claw Hammer with Shock Reduction Grip","Hammer","Thor Hammer","Sledgehammer","Claw Hammer with Fiberglass Handle","Court Hammer","Wood Saw");

        assertEquals(expected,productNames);
    }

    @Test
    public void correctItemsAreFilteredForMightyCraft(){
        js.executeScript("window.scrollTo(0,400)");
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".checkbox:nth-child(19) > label")));
        element.click();
        //wait for sorting to finish
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<WebElement> productElements = driver.findElements(By.cssSelector("*[data-test=\"product-name\"]"));
        List<String> productNames = productElements.stream().map(WebElement::getText).toList();

        List<String> expected = List.of("Bolt Cutters","Long Nose Pliers","Slip Joint Pliers","Claw Hammer","Chisels Set","Screws","Drawer Tool Cabinet","Belt Sander","Cordless Drill 12V");

        assertEquals(expected,productNames);
    }

    @Test
    public void correctNumberOfItemsAreFilteredForBothBrandSelected(){
        js.executeScript("window.scrollTo(0,600)");
        WebElement element1 = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".checkbox:nth-child(18) > label")));
        element1.click();

        WebElement element2 = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("*[data-test=\"brand-01HVV0704GHHV1A596RX6VV64Y\"]")));
        element2.click();

        List<String> allProductNames = new ArrayList<>();


        for (int i = 0; i < 4; i++) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<WebElement> productElements = driver.findElements(By.cssSelector("*[data-test=\"product-name\"]"));
            List<String> productNamesOnPage = productElements.stream().map(WebElement::getText).toList();

            allProductNames.addAll(productNamesOnPage);

            WebElement nextPageLink = driver.findElement(By.cssSelector(".pagination .page-item.active + .page-item a.page-link"));
            nextPageLink.click();

        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<WebElement> productElements = driver.findElements(By.cssSelector("*[data-test=\"product-name\"]"));
        List<String> productNamesOnPage = productElements.stream().map(WebElement::getText).toList();

        allProductNames.addAll(productNamesOnPage);
        assertEquals(45,allProductNames.size());
    }
}
