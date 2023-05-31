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
import java.util.List;

public class FavoritePage {
    public WebDriver driver;

    @FindBy(xpath = "//*[@class='bloko-button bloko-button_icon-only-small bloko-button_scale-small bloko-button_appearance-outlined' and contains(@data-qa, 'vacancy-search-mark-favorite_false')]")
    List<WebElement> favoriteButton;
    @FindBy(xpath = "//div[@class='bloko-notification__content']")
    WebElement notification;
    @FindBy(xpath = "//button[@data-qa='vacancy-search-mark-favorite_true']")
    WebElement iconLastFavoriteVacancy;

    @FindBy(xpath = "//button[@class='bloko-button bloko-button_icon-only-small bloko-button_scale-small bloko-button_appearance-outlined']")
    WebElement checkLastIcon;

    public FavoritePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void goToFavoritePage() {
        WebDriverWait bigWait = new WebDriverWait(driver, Duration.ofSeconds(40));

        bigWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"HH-React-Root\"]/div/div[2]/div[1]/div/div/div/div[8]/div[1]/a")));
        WebElement favoriteIcon = driver.findElement(By.xpath("//*[@id=\"HH-React-Root\"]/div/div[2]/div[1]/div/div/div/div[8]/div[1]/a"));

        favoriteIcon.click();
    }

    public void deleteLastVacancyFromFavorite() {
        WebDriverWait littleWait = new WebDriverWait(driver, Duration.ofSeconds(3));
        littleWait.until(ExpectedConditions.elementToBeClickable(iconLastFavoriteVacancy));
        new Actions(driver).pause(Duration.ofSeconds(3)).moveToElement(iconLastFavoriteVacancy).click(iconLastFavoriteVacancy).pause(Duration.ofSeconds(2)).perform();
    }

    public void addVacancyToFavorite(int id) {
        new Actions(driver).pause(Duration.ofSeconds(3)).moveToElement(favoriteButton.get(id)).click(favoriteButton.get(id)).perform();
    }

    public String getNotificationText() {
        WebDriverWait littleWait = new WebDriverWait(driver, Duration.ofSeconds(3));
        littleWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='bloko-notification__content']")));
        return notification.getText();
    }

    public String getLastIconStatus() {
        return checkLastIcon.getAttribute("data-qa");
    }
}
