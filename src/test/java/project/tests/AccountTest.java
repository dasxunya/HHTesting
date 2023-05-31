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
import project.pages.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountTest {
    public List<WebDriver> driverList;
    public static WebDriver chromeDriver;
    public static WebDriver firefoxDriver;
    public static LoginPage loginPage;
    public static AccountPage accountPage;
    public static ResumePage resumePage;

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
    public void testShowResponses() {
        driverList.forEach(driver -> {
            loginPage = new LoginPage(driver);
            accountPage = new AccountPage(driver);

            driver.get(ConfProperties.getProperty("main-page"));
            driver.manage().window().maximize();

            loginPage.login();

            assertEquals("Все отклики", accountPage.getTitle());
            driver.quit();
        });
    }

    @Order(2)
    @Test
    public void testShowChats() {
        driverList.forEach(driver -> {
            loginPage = new LoginPage(driver);
            accountPage = new AccountPage(driver);

            driver.get(ConfProperties.getProperty("main-page"));
            driver.manage().window().maximize();

            Class<? extends WebDriver> driverClass = driver.getClass();
            if (driverClass.equals(FirefoxDriver.class)) {
                loginPage.login();
            }

            accountPage.showChats();

            assertTrue(accountPage.getLoadedChats().isDisplayed());

            driver.quit();
        });
    }

    @Order(3)
    @Test
    public void testChangeResumeStatus() {
        driverList.forEach(driver -> {
            loginPage = new LoginPage(driver);
            accountPage = new AccountPage(driver);
            resumePage = new ResumePage(driver);

            Class<? extends WebDriver> driverClass = driver.getClass();
            if (driverClass.equals(FirefoxDriver.class)) {
                driver.get(ConfProperties.getProperty("main-page"));
            } else {
                driver.get(ConfProperties.getProperty("resumes-page"));
            }
            driver.manage().window().maximize();

            if (driverClass.equals(FirefoxDriver.class)) {
                loginPage.login();
                accountPage.showMyResumes();
            }

            resumePage.showResumeStatus();
            resumePage.changeResumeStatus(1);
            assertEquals("Рассматриваю входящие предложения", resumePage.getResumeStatusText());

            resumePage.showResumeStatus();
            resumePage.changeResumeStatus(4);
            assertEquals("Не ищу работу", resumePage.getResumeStatusText());

            driver.quit();
        });
    }
}