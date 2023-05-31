package project.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import project.ConfProperties;
import project.pages.FavoritePage;
import project.pages.MainPage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
            mainPage = new MainPage(driver);

            driver.get(ConfProperties.getProperty("main-page"));
            driver.manage().window().maximize();

            mainPage.searchBySearchField("Лаборатория Касперского");
            assertTrue(mainPage.getCompanyName().matches(".*?Лаборатория Касперского.*?"));
            driver.quit();
        });
    }

    @Test
    public void testFilterParam() {
        driverList.forEach(driver -> {
            mainPage = new MainPage(driver);

            driver.get(ConfProperties.getProperty("main-page"));
            driver.manage().window().maximize();

            mainPage.searchByFilters("Лаборатория Касперского");
            assertTrue(mainPage.getCompanyName().matches(".*?Лаборатория Касперского.*?"));

            driver.quit();
        });
    }

    @Test
    public void showMap() {
        driverList.forEach(driver -> {
            favoritePage = new FavoritePage(driver);
            mainPage = new MainPage(driver);
            mainPage.showMap();
            assertTrue(mainPage.getLoadedMap().isDisplayed());
            driver.quit();
        });
    }
}