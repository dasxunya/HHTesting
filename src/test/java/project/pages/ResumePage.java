package project.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import project.ConfProperties;

import java.time.Duration;
import java.util.List;

public class ResumePage {
    public WebDriver driver;

    @FindBy(xpath = "//*[@name=\"phone[0].formatted\"]")
    WebElement fieldNumber;
    @FindBy(xpath = "//input[@name=\"gender[0].string\" and contains (@value, \"female\")]")
    WebElement sexButton;
    @FindBy(xpath = "//*[@class=\"bloko-radio\" and contains (@data-qa, \"without-experience has-experience-s\")]")
    WebElement fieldExperience;
    @FindBy(xpath = "//*[@name=\"title[0].string\"]")
    WebElement fieldPosition;
    @FindBy(xpath = "//*[contains(text(), 'Сохранить и опубликовать')]")
    WebElement sendButton;
    @FindBy(xpath = "//*[@class='applicant-resumes-action']/a[@data-qa='resume-edit']")
    List<WebElement> editButton; //по количеству таких кнопок узнаем кол-во открытых резюме
    @FindBy(xpath = "//button[@class='bloko-button bloko-button_icon-only' and @data-qa='resume-delete']")
    WebElement deleteButton;
    @FindBy(xpath = "//button[@class='bloko-button' and @data-qa='resume-delete-confirm']")
    WebElement finallyDeleteButton;
    @FindBy(xpath = "//*[@data-qa='job-search-status-change-link']")
    WebElement resumeStatus;
    @FindBy(xpath = "//*[@class='status--bw_g56JqpCbVsNvxVzXy']")
    WebElement myResumesStatus; //установленнный статус резюме
    @FindBy(xpath = "//*[@class='bloko-button bloko-button_kind-primary bloko-button_scale-small' and contains(@data-qa,'job-search-status-change-confirm')]")
    WebElement setNewResumeStatus;
    @FindBy(xpath = "//*[@class=\"supernova-button supernova-button_secondary supernova-button_tinted\"]")
    WebElement createResumeButton;
    @FindBy(xpath = "//*[@class='bloko-text']")
    List<WebElement> changeStatusCheckbox;
    @FindBy(xpath = "//*[@name=\"firstName[0].string\"]")
    WebElement firstNameField;
    @FindBy(xpath = "//button[@class='bloko-button bloko-button_kind-primary' and @data-qa='resume-submit']")
    WebElement sendEditedResume;
    @FindBy(xpath = "//a[@class='resume-block-edit resume-block-edit_capitalize' and @data-qa='resume-block-personal-edit']")
    WebElement personalSettings;
    @FindBy(xpath = "//*[@class='bloko-notification__close']")
    WebElement closeNotification;

    public ResumePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void goToCreateResume(LoginPage profile) {
        WebDriverWait bigWait = new WebDriverWait(driver, Duration.ofSeconds(40));
        bigWait.until(ExpectedConditions.visibilityOf(profile.profileButton)); //ждем появления информации о профиле
        bigWait.until(ExpectedConditions.visibilityOf(createResumeButton));
        createResumeButton.click();
    }

    public void createResume(String post) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        new Actions(driver).pause(Duration.ofSeconds(2)).perform();
        fieldNumber.sendKeys(Keys.CONTROL + "A");
        fieldNumber.sendKeys(Keys.BACK_SPACE);
        new Actions(driver).pause(Duration.ofSeconds(3)).perform();
        fieldNumber.sendKeys(ConfProperties.getProperty("phone-number"));

        Class<? extends WebDriver> driverClass = driver.getClass();
        if (driverClass.equals(ChromeDriver.class)) {
            doScroll("1000");
        } else {
            doScroll("400");
        }

        new Actions(driver).pause(Duration.ofSeconds(1)).click(sexButton).perform();
        new Actions(driver).pause(Duration.ofSeconds(1)).click(fieldExperience).perform();
        if (driverClass.equals(ChromeDriver.class)) {
            doScroll("1000");
        }

        new Actions(driver).pause(Duration.ofSeconds(2)).perform();
        fieldPosition.sendKeys(Keys.CONTROL + "A");
        fieldPosition.sendKeys(Keys.BACK_SPACE);
        new Actions(driver).pause(Duration.ofSeconds(3)).sendKeys(fieldPosition, post).pause(Duration.ofSeconds(1)).perform();

        if (driverClass.equals(ChromeDriver.class)) {
            doScroll("500");
        } else {
            doScroll("1000");
        }

        wait.until(ExpectedConditions.elementToBeClickable(sendButton));
        new Actions(driver).click(sendButton).pause(Duration.ofSeconds(2)).perform();
    }

    public void deleteResume() {
        WebDriverWait littleWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        editButton.get(0).click();
        littleWait.until(ExpectedConditions.elementToBeClickable(deleteButton));
        new Actions(driver).pause(Duration.ofSeconds(2)).click(deleteButton).perform();
        littleWait.until(ExpectedConditions.elementToBeClickable(finallyDeleteButton));
        finallyDeleteButton.click();
        new Actions(driver).pause(Duration.ofSeconds(2)).perform();
    }

    public void doScroll(String px) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("window.scrollBy(0, " + px + ");");
    }

    public void goToEditResume(int id) {
        editButton.get(id).click();
    }

    public void changeResumeParamPersonalSettings() {
        WebDriverWait littleWait = new WebDriverWait(driver, Duration.ofSeconds(5));

        littleWait.until(ExpectedConditions.elementToBeClickable(personalSettings));
        littleWait.until(ExpectedConditions.attributeToBeNotEmpty(personalSettings, "href"));
        new Actions(driver).pause(Duration.ofSeconds(2)).click(personalSettings).perform();

        littleWait.until(ExpectedConditions.visibilityOf(firstNameField));
        firstNameField.sendKeys(Keys.CONTROL + "A");
        firstNameField.sendKeys(Keys.BACK_SPACE);
        firstNameField.sendKeys("Дарья");

        doScroll("300");

        littleWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Возможен')]")));
        WebElement radioButton = driver.findElement(By.xpath("//*[contains(text(), 'Возможен')]"));
        radioButton.click();

        littleWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Женский')]")));
        WebElement sexButtonCheckBox = driver.findElement(By.xpath("//*[contains(text(), 'Женский')]"));
        sexButtonCheckBox.click();

        doScroll("300");
        new Actions(driver).pause(Duration.ofSeconds(2)).click(sendEditedResume).perform();
    }

    public void showResumeStatus() {
        WebDriverWait littleWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        littleWait.until(ExpectedConditions.visibilityOf(resumeStatus));
        new Actions(driver).click(resumeStatus).pause(Duration.ofSeconds(1)).perform();
    }

    public void changeResumeStatus(int id) {
        WebDriverWait littleWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        littleWait.until(ExpectedConditions.visibilityOfAllElements(changeStatusCheckbox));
        changeStatusCheckbox.get(id).click();
        new Actions(driver).pause(Duration.ofSeconds(2)).click(setNewResumeStatus).pause(Duration.ofSeconds(2)).perform();
    }

    public String getResumeStatusText() {
        return myResumesStatus.getText();
    }

    public int getResumesCount(){
        return editButton.size();
    }

    public void doCloseNotification(){
        closeNotification.click();
    }

    public void changeResumeParamLanguage(WebElement param) {
        param.click();
    }

    public void changeResumeParamCapabilities(WebElement param) {
        param.click();
    }
}
