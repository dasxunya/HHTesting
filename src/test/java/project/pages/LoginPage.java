package project.pages;

import org.openqa.selenium.*;
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

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void login(){
        buttonForEntry.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        try {
            wait.until(ExpectedConditions.attributeToBeNotEmpty(emailField, "value"));
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            jsExecutor.executeScript("(document.getElementsByName('login')[0]).value=''", emailField);
        } catch (TimeoutException ignored) {
        }

        wait.until(ExpectedConditions.not(ExpectedConditions.attributeToBeNotEmpty(emailField, "value")));
        emailField.sendKeys(ConfProperties.getProperty("login"));
        buttonForEmail.click();
    }

    public void logout(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"HH-React-Root\"]/div/div[2]/div[1]/div/div/div/div[10]/div/div[1]/div/button")));
        WebElement span1 = driver.findElement(By.xpath("//*[@id=\"HH-React-Root\"]/div/div[2]/div[1]/div/div/div/div[10]/div/div[1]/div/button"));
        span1.click();

        WebElement el = driver.findElement(By.xpath("/html/body/div[11]/div/div/div[2]/div[3]/div/form/button/span"));
        wait.until(ExpectedConditions.elementToBeClickable(el));
        el.click();

    }
}