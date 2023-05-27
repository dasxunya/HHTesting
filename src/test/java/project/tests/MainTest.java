package project.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import project.ConfProperties;
import project.pages.MainPage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainTest {
    public List<WebDriver> driverList;
    public static WebDriver chromeDriver;
    public static WebDriver firefoxDriver;
    public static MainPage mainPage;

    @BeforeAll
    public static void setupDrivers() {
        WebDriverManager.chromedriver().setup();
        WebDriverManager.firefoxdriver().setup();
    }

    @BeforeEach
    public void setup() {
        driverList = new ArrayList<>();

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--user-data-dir=C:\\Users\\Dasxunya\\AppData\\Local\\Google\\Chrome\\User Data\\Profile 1");
        chromeOptions.addArguments("--profile-directory=Default");
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);

        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);

        chromeDriver = new ChromeDriver(chromeOptions);
        firefoxDriver = new FirefoxDriver(firefoxOptions);

        chromeDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        firefoxDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));

        driverList.add(chromeDriver);
        driverList.add(firefoxDriver);
    }

    @Test
    public void testSearchField() {
        driverList.forEach(driver -> {
            driver.get(ConfProperties.getProperty("main-page"));

            driver.manage().window().maximize();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            Class<? extends WebDriver> driverClass = driver.getClass();
            if (driverClass.equals(FirefoxDriver.class)) {
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[4]/div/div[3]/div[1]/div[1]/div/div/div[2]/div/form/div/div[2]/button")));
                WebElement field = driver.findElement(By.xpath("//*[@id=\"a11y-search-input\"]"));
                field.sendKeys("Лаборатория Касперского");
                field.sendKeys(Keys.ENTER);
            } else {
                WebElement field = driver.findElement(By.xpath("//*[@id=\"a11y-search-input\"]"));
                field.sendKeys("Лаборатория Касперского\n");
            }

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"a11y-main-content\"]/div[4]/div[1]/div/div[1]")));
            WebElement companyName = driver.findElement(By.xpath("//*[@id=\"a11y-main-content\"]/div[4]/div[1]/div/div[1]/span[2]"));

            assertTrue(companyName.getText().matches(".*?«Лаборатория Касперского».*?"));
            driver.quit();
        });
    }
}
