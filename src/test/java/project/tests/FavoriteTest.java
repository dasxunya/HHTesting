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
import project.pages.LoginPage;
import project.pages.MainPage;
import project.pages.ResumePage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FavoriteTest {
    public List<WebDriver> driverList;
    public static WebDriver chromeDriver;
    public static WebDriver firefoxDriver;
    public static LoginPage loginPage;
    public static FavoritePage favoritePage;
    public static ResumePage resumePage;
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
        firefoxDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));

        driverList.add(chromeDriver);
        driverList.add(firefoxDriver);
    }

    @Order(1)
    @Test
    public void testAddVacancyToFavorite() {
        driverList.forEach(driver -> {
            loginPage = new LoginPage(driver);
            favoritePage = new FavoritePage(driver);
            resumePage = new ResumePage(driver);
            mainPage = new MainPage(driver);

            driver.get(ConfProperties.getProperty("main-page"));
            driver.manage().window().maximize();

            loginPage.login();
            mainPage.showVacancyWithoutFilters();

            Class<? extends WebDriver> driverClass = driver.getClass();
            if (driverClass.equals(FirefoxDriver.class)) {
                favoritePage.addVacancyToFavorite(0);
            } else {
                favoritePage.addVacancyToFavorite(1);
            }

            assertEquals("Вакансия добавлена в избранное", favoritePage.getNotificationText());

            driver.quit();
        });
    }

    @Order(2)
    @Test
    public void showContacts() {
        driverList.forEach(driver -> {
            favoritePage = new FavoritePage(driver);
            resumePage = new ResumePage(driver);
            mainPage = new MainPage(driver);

            driver.get(ConfProperties.getProperty("vacancy-page"));
            driver.manage().window().maximize();

            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            jsExecutor.executeScript("window.scrollBy(0, 1300);");

            assertTrue(mainPage.isContactsEmpty());

            mainPage.showContactInfo();

            assertFalse(mainPage.isContactsEmpty());

            driver.quit();
        });
    }

    @Order(3)
    @Test
    public void deleteVacancyFromFavorite() {
        driverList.forEach(driver -> {
            loginPage = new LoginPage(driver);
            favoritePage = new FavoritePage(driver);


            driver.get(ConfProperties.getProperty("main-page"));
            driver.manage().window().maximize();

            Class<? extends WebDriver> driverClass = driver.getClass();
            if (driverClass.equals(FirefoxDriver.class)) {
                loginPage.login();
            }

            favoritePage.goToFavoritePage();
            favoritePage.deleteLastVacancyFromFavorite();

            //проверка что статус изменился на не добавленный в фавориты
            assertEquals("vacancy-search-mark-favorite_false", favoritePage.getLastIconStatus());

            loginPage.logout();
            driver.quit();
        });
    }
}
