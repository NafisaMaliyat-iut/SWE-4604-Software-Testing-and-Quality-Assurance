import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class stesting {
    static WebDriver chrome;
    @BeforeAll
    public static void beforeall(){
        System.setProperty("webdriver.chrome.driver","C:\\Users\\User\\Downloads\\chromedriver_win32\\chromedriver.exe");
        ChromeOptions options=new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        chrome=new ChromeDriver(options);
    }
    @Test
    public void test1(){
        chrome.get("https://demoqa.com/text-box");

        chrome.findElement(By.id("userName")).sendKeys("hello");
        chrome.findElement(By.id("userEmail")).sendKeys("abc@gmail.com");
        chrome.findElement(By.xpath("//*[@id=\"currentAddress\"]")).sendKeys("abcde");
        JavascriptExecutor js = (JavascriptExecutor) chrome;

        js.executeScript("window.scrollBy(0,400)","");
        chrome.findElement(By.id("submit")).click();

        String name=chrome.findElement(By.id("name")).getText();
        assertTrue(name.contains("hello"));
    }

    @Test
    public void test2(){
        chrome.navigate().to("https://demoqa.com/checkbox");

        chrome.findElement(By.xpath("//*[@id=\"tree-node\"]/div/button[1]")).click();

        JavascriptExecutor js=(JavascriptExecutor) chrome;

        js.executeScript("window.scrollBy(0,400)","");

        chrome.findElement(By.xpath("//*[@id=\"tree-node\"]/ol/li/ol/li[3]/span/label")).click();

        String result= chrome.findElement(By.id("result")).getText();
        assertTrue(result.contains("downloads"));
    }

    @Test
    public void test3(){
        chrome.navigate().to("https://demoqa.com/radio-button");
        chrome.findElement(By.xpath("//*[@id=\"app\"]/div/div/div[2]/div[2]/div[2]/div[3]/label")).click();

        String result=chrome.findElement(By.className("text-success")).getText();
        assertEquals("Impressive",result);
    }

    @Test
    public void test4(){
        chrome.get("https://demoqa.com/buttons");
        WebElement doubleButton=chrome.findElement(By.id("doubleClickBtn"));
        WebElement rightButton=chrome.findElement(By.id("rightClickBtn"));
        Actions ac= new Actions(chrome);

        ac.doubleClick(doubleButton).pause(200).perform();

        ac.contextClick(rightButton).pause(200).perform();

        String msg1=chrome.findElement(By.id("doubleClickMessage")).getText();

        String msg2=chrome.findElement(By.id("rightClickMessage")).getText();

        assertEquals("You have done a double click",msg1);

        assertEquals("You have done a right click" ,msg2);

    }

    @Test
    public void test5(){
        chrome.navigate().to("https://demoqa.com/broken");

        List<WebElement> image= chrome.findElements(By.tagName("img"));

        for(int i=0; i<image.size();i++) {
            if (image.get(i).getAttribute("naturalWidth").equals("0")) {
                System.out.println(image.get(i).getAttribute("src"));
                assertTrue(false);
            }
        }
    }

    @Test
    public void test6(){
        chrome.navigate().to("https://demoqa.com/upload-download");

        chrome.findElement(By.id("uploadFile")).sendKeys("C:\\Users\\User\\Downloads\\file2.csv");

        String file=chrome.findElement(By.id("uploadedFilePath")).getText();
        assertTrue(file.contains("file2.csv"));

    }

    @Test
    public void test7(){
        chrome.get("https://demoqa.com/dynamic-properties");

        JavascriptExecutor js= (JavascriptExecutor) chrome;
        js.executeScript("window.scrollBy(0,300)","");
        WebDriverWait wait=new WebDriverWait(chrome, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("enableAfter")));

        chrome.findElement(By.id("enableAfter")).click();

    }
    @AfterAll
    public static void afterall(){
        chrome.close();
    }
}
