package TestOthers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestSelenium {

    public static void main(String[] args){

        WebDriverManager.chromedriver().version("72").setup();
        WebDriver driver = new ChromeDriver();

        driver.get("http://google.com");
//        driver.quit();
    }
}
