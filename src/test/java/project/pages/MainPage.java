package project.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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

    public MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void showVacancyWithoutFilters() {
        WebDriverWait bigWait = new WebDriverWait(driver, Duration.ofSeconds(40));
        bigWait.until(ExpectedConditions.elementToBeClickable(search));
        search.click();
    }

    public void showContactInfo(){
        WebDriverWait littleWait = new WebDriverWait(driver, Duration.ofSeconds(3));
        littleWait.until(ExpectedConditions.elementToBeClickable(showContactsButton));
        showContactsButton.click();
    }

    public boolean isContactsEmpty(){
        return contacts.isEmpty();
    }
}