package project.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import project.ConfProperties;
import project.pages.CatalogPage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CatalogTest {
    public static WebDriver driver;
    public static CatalogPage catalogPage;

    @BeforeAll
    public static void setupDrivers() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--user-data-dir=C:\\Users\\Dasxunya\\AppData\\Local\\Google\\Chrome\\User Data\\Profile 3");
        options.addArguments("--profile-directory=Default");
        options.addArguments("--remote-allow-origins=*");
//        options.addArguments("--no-sandbox");
        // options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));

        catalogPage = new CatalogPage(driver);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testForNewPage() throws InterruptedException {
        driver.get(ConfProperties.getProperty("catalog-page"));

        //проверить залогинен ли пользователь, елси да - выйти выход - /html/body/div[12]/div/div/div[2]/div[3]/div/form/button

        WebElement button = driver.findElement(By.xpath("//*[@id=\"HH-React-Root\"]/div/div[2]/div/div/div/div/div[5]/a"));
        button.click();
        WebElement field = driver.findElement(By.xpath("//*[@id=\"HH-React-Root\"]/div/div[3]/div[1]/div/div/div/div/div/div[1]/div[1]/div/div[2]/form/div[1]/fieldset/input"));

//        By element = By.xpath("//*[@id=\"HH-React-Root\"]/div/div[3]/div[1]/div/div/div/div/div/div[1]/div[1]/div/div[2]/form/div[1]/fieldset/input::value");

        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("(document.getElementsByName('login')[0]).value=''", field);

        field.sendKeys("daria.kirill4@gmail.com");
        WebElement button2 = driver.findElement(By.xpath("//*[@id=\"HH-React-Root\"]/div/div[3]/div[1]/div/div/div/div/div/div[1]/div[1]/div/div[2]/form/div[5]/button[1]"));
        button2.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"HH-React-Root\"]/div/div[2]/div[1]/div/div/div/div[10]/div/div[1]/div/button")));
        WebElement span1 = driver.findElement(By.xpath("//*[@id=\"HH-React-Root\"]/div/div[2]/div[1]/div/div/div/div[10]/div/div[1]/div/button"));
        span1.click();
        WebElement span2 = driver.findElement(By.xpath("/html/body/div[12]/div/div/div[2]/div[1]/a/span"));
        assertEquals(span2.getText(), "Daria Daria");
    }


}