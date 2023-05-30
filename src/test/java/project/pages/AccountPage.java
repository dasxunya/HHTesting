package project.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;


public class AccountPage {
    public WebDriver driver;
    public AccountPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }
}
