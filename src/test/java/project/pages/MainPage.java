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
import java.util.List;

public class MainPage {
    public WebDriver driver;

    @FindBy(xpath = "//*[@class='bloko-button bloko-button_kind-primary bloko-button_stretched' and @data-qa='search-button']")
    WebElement search;
    @FindBy(xpath = "//*[@class='bloko-button bloko-button_kind-success bloko-button_scale-small bloko-button_collapsible bloko-button_appearance-outlined' and contains (@data-qa, 'vacancy-serp__vacancy_contacts')]")
    WebElement showContactsButton;
    @FindBy(xpath = "//*[@class='bloko-drop__padding-wrapper bloko-drop__padding-wrapper_down']")
    List<WebElement> contacts;
    @FindBy(xpath = "//*[@id='a11y-search-input']")
    WebElement mainSearchField;
    @FindBy(xpath = "//div[@data-qa='wizard-company-title']/div[@class='bloko-text bloko-text_small bloko-text_strong']")
    WebElement companyName;
    @FindBy(xpath = "//*[@class='bloko-button bloko-button_icon-only-large bloko-button_scale-large']")
    WebElement filtersButton;
    @FindBy(xpath = "//*[@class='bloko-input-text' and @data-qa='vacancysearch__keywords-input']")
    WebElement keyWordsField;
    @FindBy(xpath = "//*[@data-qa='control-vacancysearch__search_field-item control-vacancysearch__search_field-item_company_name']/following::span[@class='bloko-checkbox__text']")
    WebElement checkBoxForSearchInCompanyName;
    @FindBy(xpath = "//*[@data-qa='advanced-search-submit-button']")
    WebElement sendFilters;
    @FindBy(xpath = "//*[@class=\"bloko-button bloko-button_icon-only\" and contains (@data-qa, \"serp_settings__vacancy-map\")]")
    WebElement showMap;
    @FindBy(xpath = "//ymaps")
    WebElement map;
    public MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void showVacancyWithoutFilters() {
        WebDriverWait bigWait = new WebDriverWait(driver, Duration.ofSeconds(40));
        bigWait.until(ExpectedConditions.elementToBeClickable(search));
        search.click();
    }

    public void showContactInfo() {
        WebDriverWait littleWait = new WebDriverWait(driver, Duration.ofSeconds(3));
        littleWait.until(ExpectedConditions.elementToBeClickable(showContactsButton));
        showContactsButton.click();
    }

    public boolean isContactsEmpty() {
        return contacts.isEmpty();
    }

    public void searchBySearchField(String param) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        wait.until(ExpectedConditions.visibilityOf(mainSearchField));
        mainSearchField.sendKeys(param);
        mainSearchField.sendKeys(Keys.ENTER);
    }

    public void searchByFilters(String param) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        wait.until(ExpectedConditions.elementToBeClickable(filtersButton));
        wait.until(ExpectedConditions.attributeToBeNotEmpty(filtersButton, "href"));
        new Actions(driver).pause(Duration.ofSeconds(2)).click(filtersButton).perform();

        wait.until(ExpectedConditions.visibilityOf(keyWordsField));
        new Actions(driver).sendKeys(keyWordsField, param).pause(Duration.ofSeconds(1)).perform();

        wait.until(ExpectedConditions.elementToBeClickable(checkBoxForSearchInCompanyName));
        checkBoxForSearchInCompanyName.click();

        new Actions(driver).click(sendFilters).pause(Duration.ofSeconds(2)).perform();
    }

    public String getCompanyName() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(companyName));
        return companyName.getText();
    }

    public void showMap(){
        WebDriverWait littleWait = new WebDriverWait(driver, Duration.ofSeconds(3));

        driver.get(ConfProperties.getProperty("vacancy-page"));
        driver.manage().window().maximize();

        littleWait.until(ExpectedConditions.elementToBeClickable(showMap));
        showMap.click();

    }

    public WebElement getLoadedMap(){
        WebDriverWait littleWait = new WebDriverWait(driver, Duration.ofSeconds(3));
        littleWait.until(ExpectedConditions.visibilityOf(map));
        return map;
    }
}