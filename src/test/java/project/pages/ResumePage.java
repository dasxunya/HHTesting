package project.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import project.ConfProperties;

import java.time.Duration;

public class ResumePage {
    public WebDriver driver;

    @FindBy(xpath = "//*[@id=\"HH-React-Root\"]/div/div[3]/div[1]/div/div/form/div[2]/div[2]/div[1]/div[2]/div[2]/div[1]/fieldset/input")
    WebElement fieldNumber;
    @FindBy(xpath = "//*[@id=\"HH-React-Root\"]/div/div[3]/div[1]/div/div/form/div[3]/div[2]/div[1]/div[1]/div[3]/div/div[2]/div[2]/label/span")
    WebElement sexButton;
    @FindBy(xpath = "//*[@id=\"HH-React-Root\"]/div/div[3]/div[1]/div/div/form/div[3]/div[2]/div[1]/div[4]/div/div[2]/label/span")
    WebElement fieldExperience;
    @FindBy(xpath = "//*[@id=\"HH-React-Root\"]/div/div[3]/div[1]/div/div/form/div[4]/div[2]/div[1]/div[1]/div[2]/div/fieldset/input")
    WebElement fieldPosition;
    @FindBy(xpath = "//*[@id=\"HH-React-Root\"]/div/div[3]/div[1]/div/div/form/div[8]/div[2]/button")
    WebElement sendButton;
    @FindBy(xpath = "//*[@id=\"HH-React-Root\"]/div/div[3]/div[1]/div/div/div[1]/div[5]/div[1]/div/div[8]/div/div/div[3]/a")
    WebElement editButton;
    @FindBy(xpath = "//*[@id=\"HH-React-Root\"]/div/div[3]/div[1]/div/div[2]/div/div/div[2]/div/div[1]/div/div/div/div[2]/div/div[2]/div/div/div/button[3]")
    WebElement deleteButton;
    @FindBy(xpath = "/html/body/div[12]/div/div[1]/div[2]/div/div[1]/form/button")
    WebElement finallyDeleteButton;

    public ResumePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void createResume(String post) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

        try {
            new Actions(driver).click(fieldNumber);
            wait.until(ExpectedConditions.attributeToBeNotEmpty(fieldNumber, "value"));
            new Actions(driver).pause(Duration.ofSeconds(3)).perform();
            jsExecutor.executeScript("(document.getElementsByName('phone[0].formatted')[0]).value=''", fieldNumber);
        } catch (TimeoutException ignored) {
        }

        fieldNumber.sendKeys("9609211500");
        new Actions(driver).pause(Duration.ofSeconds(3)).perform();
//        new Actions(driver).doubleClick(fieldNumber).click(fieldNumber).sendKeys(post).perform();
        sexButton.click();
        fieldExperience.click();

        jsExecutor.executeScript("window.scrollBy(0, 1000);");

        try {
            wait.until(ExpectedConditions.attributeToBeNotEmpty(fieldPosition, "value"));
            new Actions(driver).pause(Duration.ofSeconds(3)).perform();
            jsExecutor.executeScript("(document.getElementsByName('title[0].string')[0]).value=''", fieldPosition);
        } catch (TimeoutException ignored) {
        }

        fieldPosition.sendKeys(post);
        new Actions(driver).pause(Duration.ofSeconds(3)).perform();
//        new Actions(driver).doubleClick(fieldPosition).click(fieldPosition).sendKeys(post).perform();
        sendButton.click();
    }

    public void deleteResume() {
        WebDriverWait littleWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        editButton.click();
        littleWait.until(ExpectedConditions.elementToBeClickable(deleteButton));
        new Actions(driver).pause(Duration.ofSeconds(2)).click(deleteButton).perform();
        littleWait.until(ExpectedConditions.elementToBeClickable(finallyDeleteButton));
        finallyDeleteButton.click();
    }
}
