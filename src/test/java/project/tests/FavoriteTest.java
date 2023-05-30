package project.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import project.ConfProperties;
import project.pages.FavoritePage;
import project.pages.LoginPage;
import project.pages.ResumePage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class FavoriteTest {
    public List<WebDriver> driverList;
    public static WebDriver chromeDriver;
    public static WebDriver firefoxDriver;
    public static FavoritePage favoritePage;
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


    @Test
    public void testAddVacancyToFavorite() {
        driverList.forEach(driver -> {
            loginPage = new LoginPage(driver);
            favoritePage = new FavoritePage(driver);
            resumePage = new ResumePage(driver);

            driver.get(ConfProperties.getProperty("main-page"));
            driver.manage().window().maximize();

            loginPage.login();

            WebDriverWait bigWait = new WebDriverWait(driver, Duration.ofSeconds(40));
            WebDriverWait littleWait = new WebDriverWait(driver, Duration.ofSeconds(3));

            bigWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"HH-React-Root\"]/div/div[2]/div[2]/div/div/div/div/form/div/div[2]/button")));
            WebElement showAllVacancy = driver.findElement(By.xpath("//*[@id=\"HH-React-Root\"]/div/div[2]/div[2]/div/div/div/div/form/div/div[2]/button"));

            showAllVacancy.click();

            Class<? extends WebDriver> driverClass = driver.getClass();
            if (driverClass.equals(FirefoxDriver.class)) {
                resumePage.doScroll(); //перенести метод в mainPage
                WebElement addVacancyToFavorite = driver.findElement(By.xpath("//*[@id=\"a11y-main-content\"]/div[3]/div/div[6]/div/span[3]/button"));
                new Actions(driver).pause(Duration.ofSeconds(3)).moveToElement(addVacancyToFavorite).click(addVacancyToFavorite).perform();
            } else {
                WebElement addVacancyToFavorite = driver.findElement(By.xpath("//*[@id=\"a11y-main-content\"]/div[2]/div/div[8]/div/span[3]/button"));
                new Actions(driver).pause(Duration.ofSeconds(3)).moveToElement(addVacancyToFavorite).click(addVacancyToFavorite).perform();
            }

            littleWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[8]/div/div/div/div[2]/div[1]")));
            WebElement succes = driver.findElement(By.xpath("/html/body/div[8]/div/div/div/div[2]/div[1]"));
            assertEquals("Вакансия добавлена в избранное", succes.getText());
            driver.quit();
        });
    }

    @Test
    public void showContactsAndDeleteVacancyFromFavorite() {
        driverList.forEach(driver -> {
            LoginPage loginPage = new LoginPage(driver);
            FavoritePage favoritePage = new FavoritePage(driver);

            WebDriverWait littleWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebDriverWait bigWait = new WebDriverWait(driver, Duration.ofSeconds(40));

            driver.get(ConfProperties.getProperty("main-page"));
            driver.manage().window().maximize();

            Class<? extends WebDriver> driverClass = driver.getClass();
            if (driverClass.equals(FirefoxDriver.class)) {
                loginPage.login();
            }

            bigWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"HH-React-Root\"]/div/div[2]/div[1]/div/div/div/div[8]/div[1]/a")));
            WebElement favoriteIcon = driver.findElement(By.xpath("//*[@id=\"HH-React-Root\"]/div/div[2]/div[1]/div/div/div/div[8]/div[1]/a"));

            favoriteIcon.click();

            littleWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-qa='vacancy-search-mark-favorite_true']")));
            WebElement deleteVacancyFromFavorite = driver.findElement(By.xpath("//button[@data-qa='vacancy-search-mark-favorite_true']"));
            new Actions(driver).pause(Duration.ofSeconds(3)).moveToElement(deleteVacancyFromFavorite).click(deleteVacancyFromFavorite).pause(Duration.ofSeconds(1)).perform();

            //проверка что класс изменился на не добавленный в фавориты
            assertEquals("vacancy-search-mark-favorite_false", driver.findElement(By.xpath("//button[@class='bloko-button bloko-button_icon-only-small bloko-button_scale-small bloko-button_appearance-outlined']")).getAttribute("data-qa"));
            driver.quit();
        });
    }
}
