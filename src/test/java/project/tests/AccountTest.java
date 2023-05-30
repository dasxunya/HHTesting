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
import project.pages.AccountPage;
import project.pages.LoginPage;

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

            WebDriverWait littleWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            littleWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='supernova-link' and contains(@data-qa,'mainmenu_vacancyResponses')]")));
            WebElement responses = driver.findElement(By.xpath("//*[@class='supernova-link' and contains(@data-qa,'mainmenu_vacancyResponses')]"));
            responses.click();

            littleWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@data-qa,'negotiations-tab')]")));
            WebElement readyResponses = driver.findElements(By.xpath("//a[contains(@data-qa,'negotiations-tab')]")).get(1);
            assertEquals("Все отклики", readyResponses.getText());
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

            WebDriverWait littleWait = new WebDriverWait(driver, Duration.ofSeconds(5));

            littleWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='supernova-icon supernova-icon_chatik']")));
            WebElement chats = driver.findElement(By.xpath("//*[@class='supernova-icon supernova-icon_chatik']"));
            new Actions(driver).pause(Duration.ofSeconds(2)).click(chats).perform();

            littleWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='chatik-integration-iframe chatik-integration-iframe_loaded']")));
            assertTrue(driver.findElement(By.xpath("//*[@class='chatik-integration-iframe chatik-integration-iframe_loaded']")).isDisplayed());

            driver.quit();
        });
    }

    @Order(3)
    @Test
    public void testChangeResumeStatus() {
        driverList.forEach(driver -> {
            loginPage = new LoginPage(driver);
            accountPage = new AccountPage(driver);

            Class<? extends WebDriver> driverClass = driver.getClass();
            if (driverClass.equals(FirefoxDriver.class)) {
                driver.get(ConfProperties.getProperty("main-page"));
            } else {
                driver.get(ConfProperties.getProperty("resumes-page"));
            }
            driver.manage().window().maximize();

            if (driverClass.equals(FirefoxDriver.class)) {
                loginPage.login();
                WebDriverWait littleWait = new WebDriverWait(driver, Duration.ofSeconds(10));
                littleWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='supernova-link' and @data-qa='mainmenu_myResumes']")));
                driver.findElement(By.xpath("//*[@class='supernova-link' and @data-qa='mainmenu_myResumes']")).click();
            }

            WebDriverWait littleWait = new WebDriverWait(driver, Duration.ofSeconds(5));

            littleWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@data-qa='job-search-status-change-link']")));
            WebElement resumeStatus = driver.findElement(By.xpath("//*[@data-qa='job-search-status-change-link']"));

            new Actions(driver).click(resumeStatus).pause(Duration.ofSeconds(1)).perform();

            littleWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@class='bloko-text']")));
            WebElement changeStatusCheckbox = driver.findElements(By.xpath("//*[@class='bloko-text']")).get(1);
            changeStatusCheckbox.click();

            WebElement sendStatusButton = driver.findElement(By.xpath("//*[@class='bloko-button bloko-button_kind-primary bloko-button_scale-small' and contains(@data-qa,'job-search-status-change-confirm')]"));
            new Actions(driver).pause(Duration.ofSeconds(2)).click(sendStatusButton).pause(Duration.ofSeconds(2)).perform();

            WebElement myStatus = driver.findElement(By.xpath("//*[@class='status--bw_g56JqpCbVsNvxVzXy']"));

            assertEquals("Рассматриваю входящие предложения", myStatus.getText());

            // Возвращаем статус обратно
            new Actions(driver).click(resumeStatus).pause(Duration.ofSeconds(1)).perform();

            littleWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@class='bloko-text']")));
            WebElement returnStatusCheckbox = driver.findElements(By.xpath("//*[@class='bloko-text']")).get(4);
            returnStatusCheckbox.click();

            WebElement returnStatusButton = driver.findElement(By.xpath("//*[@class='bloko-button bloko-button_kind-primary bloko-button_scale-small' and contains(@data-qa,'job-search-status-change-confirm')]"));
            new Actions(driver).pause(Duration.ofSeconds(2)).click(returnStatusButton).pause(Duration.ofSeconds(2)).perform();

            assertEquals("Не ищу работу", myStatus.getText());

            driver.quit();
        });
    }
}
