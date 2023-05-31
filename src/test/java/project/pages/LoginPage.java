package project.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import project.ConfProperties;

import java.time.Duration;

public class LoginPage {
    public WebDriver driver;

    @FindBy(xpath = "//*[@class='supernova-button' and @data-qa='login']")
    WebElement buttonForEntry;
    @FindBy(xpath = "//*[@class='bloko-link bloko-link_pseudo' and @data-qa='expand-login-by-password']")
    WebElement entryWithEmailAndPasswordButton;
    @FindBy(xpath = "//*[@class='bloko-input-text' and @data-qa='login-input-username']")
    WebElement fieldForEmail;
    @FindBy(xpath = "//*[@class='bloko-input-text' and @data-qa='login-input-password']")
    WebElement fieldForPassword;
    @FindBy(xpath = "//*[@class='bloko-button bloko-button_kind-primary' and @data-qa='account-login-submit']")
    WebElement entry;
    @FindBy(xpath = "//*[@class='supernova-icon-link-switch' and @data-qa='mainmenu_applicantProfile']")
    WebElement profileButton;
    @FindBy(xpath = "//span[@class='bloko-text bloko-text_strong']")
    WebElement username;
    @FindBy(xpath = "//button[@class='supernova-dropdown-option supernova-dropdown-option_highlight-warning' and @data-qa='mainmenu_logoffUser']")
    WebElement exit;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void login() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        wait.until(ExpectedConditions.elementToBeClickable(buttonForEntry));
        buttonForEntry.click();

        wait.until(ExpectedConditions.visibilityOf(entryWithEmailAndPasswordButton));
        wait.until(ExpectedConditions.elementToBeClickable(entryWithEmailAndPasswordButton));
        entryWithEmailAndPasswordButton.click();

        fieldForEmail.sendKeys(Keys.CONTROL + "A");
        fieldForEmail.sendKeys(Keys.BACK_SPACE);
        wait.until(ExpectedConditions.not(ExpectedConditions.attributeToBeNotEmpty(fieldForEmail, "value")));
        new Actions(driver).sendKeys(fieldForEmail, ConfProperties.getProperty("login")).sendKeys(fieldForPassword, ConfProperties.getProperty("password")).pause(Duration.ofSeconds(1)).click(entry).perform();
    }

    public void logout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        wait.until(ExpectedConditions.visibilityOf(profileButton));
        wait.until(ExpectedConditions.elementToBeClickable(profileButton));
        new Actions(driver).pause(Duration.ofSeconds(1)).click(profileButton).pause(Duration.ofSeconds(1)).perform();

        wait.until(ExpectedConditions.visibilityOf(exit));
        wait.until(ExpectedConditions.elementToBeClickable(exit));
        exit.click();
    }

    public String getUserName() {
        WebDriverWait littleWait = new WebDriverWait(driver, Duration.ofSeconds(3));
        waitProfileLoad();
        new Actions(driver).pause(Duration.ofSeconds(2)).click(profileButton).perform();
        littleWait.until(ExpectedConditions.visibilityOf(username));
        return username.getText();
    }

    public void waitProfileLoad() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        wait.until(ExpectedConditions.visibilityOf(profileButton));
    }

    public boolean isUserLogout() {
        return buttonForEntry.isDisplayed();
    }
}