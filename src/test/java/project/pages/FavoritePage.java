package project.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class FavoritePage {
    public WebDriver driver;

    public FavoritePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }
}
