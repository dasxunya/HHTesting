package project.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import project.ConfProperties;

import java.time.Duration;

public class LoginPage {
    public WebDriver driver;

    @FindBy(xpath = "//*[@id=\"HH-React-Root\"]/div/div[2]/div/div/div/div/div[5]/a")
    WebElement buttonForEntry;
    @FindBy(xpath = "//*[@id=\"HH-React-Root\"]/div/div[3]/div[1]/div/div/div/div/div/div[1]/div[1]/div/div[2]/form/div[5]/button[1]")
    WebElement buttonForEmail;
    @FindBy(xpath = "//*[@id=\"HH-React-Root\"]/div/div[3]/div[1]/div/div/div/div/div/div[1]/div[1]/div/div[2]/form/div[1]/fieldset/input")
    WebElement emailField;
    @FindBy(xpath = "/html/body/div[5]/div/div[3]/div[1]/div/div/div/div/div/div[1]/div[1]/div/div[2]/form/div[5]/button[2]")
    WebElement entryWithEmailAndPassButton;
    @FindBy(xpath = "/html/body/div[5]/div/div[3]/div[1]/div/div/div/div/div/div[1]/div[1]/div/form/div[1]/fieldset/input")
    WebElement fieldForEntryWithEmail;
    @FindBy(xpath = "//*[@id=\"HH-React-Root\"]/div/div[3]/div[1]/div/div/div/div/div/div[1]/div[1]/div/form/div[2]/fieldset/input")
    WebElement fieldForPassword;
    @FindBy(xpath = "/html/body/div[5]/div/div[3]/div[1]/div/div/div/div/div/div[1]/div[1]/div/form/div[5]/div/button[1]")
    WebElement entry;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void login() {
        buttonForEntry.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        try {
            wait.until(ExpectedConditions.attributeToBeNotEmpty(fieldForEntryWithEmail, "value"));
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            jsExecutor.executeScript("(document.getElementsByName('username')[0]).value=''");
        } catch (TimeoutException ignored) {
        }

        entryWithEmailAndPassButton.click();
        fieldForEntryWithEmail.sendKeys(Keys.CONTROL + "A");
        fieldForEntryWithEmail.sendKeys(Keys.BACK_SPACE);
        wait.until(ExpectedConditions.not(ExpectedConditions.attributeToBeNotEmpty(fieldForEntryWithEmail, "value")));
        new Actions(driver).sendKeys(fieldForEntryWithEmail, ConfProperties.getProperty("login")).sendKeys(fieldForPassword, ConfProperties.getProperty("password")).pause(Duration.ofSeconds(1)).click(entry).perform();
    }

    public void logout() {
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
//
//        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"HH-React-Root\"]/div/div[2]/div[1]/div/div/div/div[10]/div/div[1]/div/button")));
//        WebElement span1 = driver.findElement(By.xpath("//*[@id=\"HH-React-Root\"]/div/div[2]/div[1]/div/div/div/div[10]/div/div[1]/div/button"));
//        span1.click();
//
//        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[12]/div/div/div[2]/div[3]/div/form/button/span")));
//        WebElement el = driver.findElement(By.xpath("/html/body/div[12]/div/div/div[2]/div[3]/div/form/button/span"));
//        el.click();
    }
}