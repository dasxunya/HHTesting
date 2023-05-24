package project;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;

public class ConnectWithProxy {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();

        String proxyAddr = "5.161.109.67:8888";
        ChromeOptions chromeOptions = new ChromeOptions();

        chromeOptions.addArguments("--ignore-certificate-errors");
        chromeOptions.addArguments("--proxy-server=https://" + proxyAddr);
        chromeOptions.addArguments("--user-data-dir=C:\\Users\\Dasxunya\\AppData\\Local\\Google\\Chrome\\User Data\\Profile 3");
        chromeOptions.addArguments("--profile-directory=Default");
        chromeOptions.addArguments("--remote-allow-origins=*");
//        chromeOptions.addArguments("--profile-directory=Profile 3");
        WebDriver driver = new ChromeDriver(chromeOptions);

        // Открытие сайта с использованием прокси
        driver.get("https://linkedin.com/");

        // Закрытие браузера
//        driver.quit();
    }

}
