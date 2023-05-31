package project.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class AccountPage {

    public WebDriver driver;
    @FindBy(xpath = "//*[@class='supernova-link' and contains(@data-qa,'mainmenu_vacancyResponses')]")
    WebElement responses;
    @FindBy(xpath = "//a[contains(@data-qa,'negotiations-tab')]")
    WebElement readyResponses;
    @FindBy(xpath = "//*[@class='supernova-icon supernova-icon_chatik']")
    WebElement chats;
    @FindBy(xpath = "//*[@class='chatik-integration-iframe chatik-integration-iframe_loaded']")
    WebElement loadedChats;
    @FindBy(xpath = "//*[@class='supernova-link' and @data-qa='mainmenu_myResumes']")
    WebElement myResumes;
    @FindBy(xpath = "")
    WebElement element3;

    public AccountPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public String getTitle() {
        showMyFeedbacks();
        return readyResponses.getText();
    }

    public void showChats() {
        WebDriverWait littleWait = new WebDriverWait(driver, Duration.ofSeconds(5));

        littleWait.until(ExpectedConditions.elementToBeClickable(chats));
        new Actions(driver).pause(Duration.ofSeconds(2)).click(chats).perform();

        littleWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='chatik-integration-iframe chatik-integration-iframe_loaded']")));
    }

    public void showMyFeedbacks() {
        WebDriverWait littleWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        littleWait.until(ExpectedConditions.elementToBeClickable(responses));
        responses.click();
        littleWait.until(ExpectedConditions.elementToBeClickable(readyResponses));
    }

    public void showMyResumes(){
        WebDriverWait littleWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        littleWait.until(ExpectedConditions.elementToBeClickable(myResumes));
        myResumes.click();
    }

    public WebElement getLoadedChats() {
        return this.loadedChats;
    }
}
