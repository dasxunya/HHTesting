package project.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import project.ConfProperties;
import project.pages.FavoritePage;
import project.pages.LoginPage;
import project.pages.MainPage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainTest {
    public List<WebDriver> driverList;
    public static WebDriver chromeDriver;
    public static WebDriver firefoxDriver;
    public static FavoritePage favoritePage;
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
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[4]/div/div[3]/div[1]/div[1]/div/div/div[2]/div/form/div/div[2]/button")));
            WebElement field = driver.findElement(By.xpath("//*[@id=\"a11y-search-input\"]"));

            Class<? extends WebDriver> driverClass = driver.getClass();
            if (driverClass.equals(FirefoxDriver.class)) {
                field.sendKeys("Лаборатория Касперского");
                field.sendKeys(Keys.ENTER);
            } else {
                field.sendKeys("Лаборатория Касперского\n");
            }

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"a11y-main-content\"]/div[4]/div[1]/div/div[1]")));
            WebElement companyName = driver.findElement(By.xpath("//*[@id=\"a11y-main-content\"]/div[4]/div[1]/div/div[1]/span[2]"));

            assertTrue(companyName.getText().matches(".*?«Лаборатория Касперского».*?"));
            driver.quit();
        });
    }

    @Test
    public void testFilterParam() {
        driverList.forEach(driver -> {
            driver.get(ConfProperties.getProperty("main-page"));
            driver.manage().window().maximize();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='bloko-button bloko-button_icon-only-large bloko-button_scale-large']")));
            wait.until(ExpectedConditions.attributeToBeNotEmpty(driver.findElement(By.xpath("//*[@class='bloko-button bloko-button_icon-only-large bloko-button_scale-large']")), "href"));
            WebElement filterButton = driver.findElement(By.xpath("//*[@class='bloko-button bloko-button_icon-only-large bloko-button_scale-large']"));
            new Actions(driver).pause(Duration.ofSeconds(2)).click(filterButton).perform();


            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"HH-React-Root\"]/div/div[3]/div[1]/div/div/div[1]/form/div[1]/div[2]/div[1]/fieldset/input")));
            WebElement field = driver.findElement(By.xpath("//*[@id=\"HH-React-Root\"]/div/div[3]/div[1]/div/div/div[1]/form/div[1]/div[2]/div[1]/fieldset/input"));
            new Actions(driver).sendKeys(field, "Лаборатория Касперского").pause(Duration.ofSeconds(1)).perform();

            WebElement companyName = driver.findElement(By.xpath("//*[@id=\"HH-React-Root\"]/div/div[3]/div[1]/div/div/div[1]/form/div[1]/div[2]/div[4]/label/span"));
            companyName.click();

            WebElement button = driver.findElement(By.xpath("//*[@id=\"HH-React-Root\"]/div/div[3]/div[1]/div/div/div[1]/form/div[32]/div[2]/button"));
            new Actions(driver).click(button).pause(Duration.ofSeconds(2)).perform();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"a11y-main-content\"]/div[4]/div[1]/div/div[1]")));
            WebElement gottenCompanyName = driver.findElement(By.xpath("//*[@id=\"a11y-main-content\"]/div[4]/div[1]/div/div[1]/span[2]"));

            assertTrue(gottenCompanyName.getText().matches(".*?«Лаборатория Касперского».*?"));
            driver.quit();
            driver.quit();
        });
    }

    @Test
    public void showMap() {
        driverList.forEach(driver -> {
            favoritePage = new FavoritePage(driver);

            WebDriverWait littleWait = new WebDriverWait(driver, Duration.ofSeconds(3));

            driver.get(ConfProperties.getProperty("vacancy-page"));
            driver.manage().window().maximize();

            littleWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class=\"bloko-button bloko-button_icon-only\" and contains (@data-qa, \"serp_settings__vacancy-map\")]")));
            WebElement showMap = driver.findElement(By.xpath("//*[@class=\"bloko-button bloko-button_icon-only\" and contains (@data-qa, \"serp_settings__vacancy-map\")]"));
            showMap.click();

            littleWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ymaps")));
            assertNotNull(driver.findElement(By.xpath("//ymaps")));

            driver.quit();
        });
    }
}