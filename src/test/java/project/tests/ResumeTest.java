package project.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import project.ConfProperties;
import project.pages.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ResumeTest {
    public List<WebDriver> driverList;
    public static WebDriver chromeDriver;
    public static WebDriver firefoxDriver;
    public static ResumePage resumePage;
    public static LoginPage loginPage;
    public static AccountPage accountPage;

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
    public void testCreateResume() {
        driverList.forEach(driver -> {
            loginPage = new LoginPage(driver);
            resumePage = new ResumePage(driver);
            accountPage = new AccountPage(driver);

            driver.get(ConfProperties.getProperty("main-page"));
            driver.manage().window().maximize();

            loginPage.login();

            accountPage.showMyResumes();
            int count_before = resumePage.getResumesCount();

            resumePage.goToCreateResume(loginPage);
            Class<? extends WebDriver> driverClass = driver.getClass();
            if (driverClass.equals(FirefoxDriver.class)) {
                resumePage.createResume("Руководитель");
            } else resumePage.createResume("Помощник Руководителя");

            accountPage.showMyResumes();
            int count_after = resumePage.getResumesCount();

            assertEquals(1, count_after - count_before);
            driver.quit();
        });
    }

    @Order(2)
    @Test
    public void testEditResume() {
        driverList.forEach(driver -> {
            loginPage = new LoginPage(driver);
            resumePage = new ResumePage(driver);
            accountPage = new AccountPage(driver);

            driver.get(ConfProperties.getProperty("main-page"));
            driver.manage().window().maximize();

            Class<? extends WebDriver> driverClass = driver.getClass();
            if (driverClass.equals(FirefoxDriver.class)) {
                loginPage.login();
            }

            loginPage.waitProfileLoad();
            accountPage.showMyResumes();

            if (driverClass.equals(FirefoxDriver.class)) {
                resumePage.goToEditResume(1);
            } else {
                resumePage.goToEditResume(0);
            }
//            WebElement language = driver.findElement(By.xpath("//*[@id=\"HH-React-Root\"]/div/div[3]/div[1]/div/div[2]/div/div/div[4]/div[1]/div/div/div[5]/div[1]/div/div/div[1]/a"));
//            WebElement capabilities = driver.findElement(By.xpath("//*[@id=\"HH-React-Root\"]/div/div[3]/div[1]/div/div[2]/div/div/div[4]/div[1]/div/div/div[3]/div[1]/div/div/div[1]/a/span"));

            resumePage.changeResumeParamPersonalSettings();
//            resumePage.changeResumeParamLanguage(language);
//            resumePage.changeResumeParamCapabilities(capabilities);

            assertEquals("Daria Дарья", accountPage.getPersonalName());
            assertTrue(accountPage.getTravelStatus().matches(".*?готова к переезду.*?"));
//            driver.quit();
        });
    }

    @Order(3)
    @Test
    public void testDeleteResume() {
        driverList.forEach(driver -> {
            loginPage = new LoginPage(driver);
            resumePage = new ResumePage(driver);
            accountPage = new AccountPage(driver);

            driver.get(ConfProperties.getProperty("main-page"));
            driver.manage().window().maximize();

            Class<? extends WebDriver> driverClass = driver.getClass();
            if (driverClass.equals(FirefoxDriver.class)) {
                loginPage.login();
            }

            accountPage.showMyResumes();

            int count_before = resumePage.getResumesCount();
            resumePage.deleteResume();

            new Actions(driver).pause(Duration.ofSeconds(1)).perform();

            resumePage.doCloseNotification();
            int count_after = resumePage.getResumesCount();

            assertEquals(1, count_before - count_after);

            loginPage.logout();
            driver.quit();
        });
    }
}