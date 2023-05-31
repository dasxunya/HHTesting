package project.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import project.ConfProperties;
import project.pages.LoginPage;
import project.pages.ResumePage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

// фаерфокс привереда к зоне видимости объекта при скролле
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ResumeTest {
    public List<WebDriver> driverList;
    public static WebDriver chromeDriver;
    public static WebDriver firefoxDriver;
    public static ResumePage resumePage;
    public static LoginPage loginPage;

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

            driver.get(ConfProperties.getProperty("main-page"));
            driver.manage().window().maximize();

            loginPage.login();

            WebDriverWait bigWait = new WebDriverWait(driver, Duration.ofSeconds(40));

            bigWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"HH-React-Root\"]/div/div[2]/div[1]/div/div/div/div[10]/div/div[1]/div/button"))); //ждем появления информации о профиле
            bigWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class=\"supernova-button supernova-button_secondary supernova-button_tinted\"]")));
            WebElement createResumeButton = driver.findElement(By.xpath("//*[@class=\"supernova-button supernova-button_secondary supernova-button_tinted\"]"));
            createResumeButton.click();

            Class<? extends WebDriver> driverClass = driver.getClass();
            if (driverClass.equals(FirefoxDriver.class)) {
                resumePage.createResume("Руководитель");
            } else resumePage.createResume("Помощник");

            driver.quit();
        });
    }

    @Order(2)
    @Test
    public void testEditResume() {
        driverList.forEach(driver -> {
            loginPage = new LoginPage(driver);
            resumePage = new ResumePage(driver);

            driver.get(ConfProperties.getProperty("main-page"));
            driver.manage().window().maximize();

            Class<? extends WebDriver> driverClass = driver.getClass();
            if (driverClass.equals(FirefoxDriver.class)) {
                loginPage.login();
            }

            WebDriverWait littleWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebDriverWait bigWait = new WebDriverWait(driver, Duration.ofSeconds(40));

            bigWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"HH-React-Root\"]/div/div[2]/div[1]/div/div/div/div[10]/div/div[1]/div/button"))); //ждем появления информации о профиле

            WebElement showMyResumes = driver.findElement(By.xpath("//*[@id=\"HH-React-Root\"]/div/div[2]/div[1]/div/div/div/div[1]/a"));
            showMyResumes.click();

            littleWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"HH-React-Root\"]/div/div[3]/div[1]/div/div/div[1]/div[5]/div/div/div[8]/div/div/div[3]/a")));
            WebElement editResume = driver.findElement(By.xpath("//*[@id=\"HH-React-Root\"]/div/div[3]/div[1]/div/div/div[1]/div[5]/div/div/div[8]/div/div/div[3]/a"));
            editResume.click();

            resumePage.doScroll();

            WebElement personalSettings = driver.findElement(By.xpath("//*[@id=\"a11y-main-content\"]/div[1]/p[2]/a/span"));
//            WebElement language = driver.findElement(By.xpath("//*[@id=\"HH-React-Root\"]/div/div[3]/div[1]/div/div[2]/div/div/div[4]/div[1]/div/div/div[5]/div[1]/div/div/div[1]/a"));
//            WebElement capabilities = driver.findElement(By.xpath("//*[@id=\"HH-React-Root\"]/div/div[3]/div[1]/div/div[2]/div/div/div[4]/div[1]/div/div/div[3]/div[1]/div/div/div[1]/a/span"));

            resumePage.changeResumeParamPersonalSettings(personalSettings);
//            resumePage.changeResumeParamLanguage(language);
//            resumePage.changeResumeParamCapabilities(capabilities);

            driver.quit();
        });
    }

    @Order(3)
    @Test
    public void testDeleteResume() {
        driverList.forEach(driver -> {
            loginPage = new LoginPage(driver);
            resumePage = new ResumePage(driver);

            driver.get(ConfProperties.getProperty("main-page"));
            driver.manage().window().maximize();

            Class<? extends WebDriver> driverClass = driver.getClass();
            if (driverClass.equals(FirefoxDriver.class)) {
                loginPage.login();
            }

            WebDriverWait bigWait = new WebDriverWait(driver, Duration.ofSeconds(40));

            bigWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"HH-React-Root\"]/div/div[2]/div[1]/div/div/div/div[10]/div/div[1]/div/button"))); //ждем появления информации о профиле
            bigWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class=\"supernova-button supernova-button_secondary supernova-button_tinted\"]")));
            WebElement allResumesButton = driver.findElement(By.xpath("//*[@id=\"HH-React-Root\"]/div/div[2]/div[1]/div/div/div/div[1]/a"));
            allResumesButton.click();

            resumePage.deleteResume();
            loginPage.logout();
            new Actions(driver).pause(Duration.ofSeconds(2)).perform();
            driver.quit();
        });
    }
}