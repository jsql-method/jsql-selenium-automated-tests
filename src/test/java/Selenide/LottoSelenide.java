package Selenide;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class LottoSelenide {

    public static WebDriver driver;
    public String login = "dsenko";
    public String password = "Jsql1234";
    public int num1 = 8;
    public int num2 = 14;
    public int num3 = 17;
    public int num4 = 35;
    public int num5 = 38;

    @Test
    public void checkGoogleSearch() throws InterruptedException {


        System.setProperty("selenide.timeout ", "20000");
        Configuration.browser="firefox";
//        Configuration.headless=true;
        Configuration.reopenBrowserOnFail=true;
        Configuration.driverManagerEnabled=true;

        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("geo.enabled", true);
        profile.setPreference("geo.provider.use_corelocation", true);
        profile.setPreference("geo.prompt.testing", true);
        profile.setPreference("geo.prompt.testing.allow", true);
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setProfile(profile);
//        firefoxOptions.setHeadless(true);

        WebDriverRunner.setWebDriver(new FirefoxDriver(firefoxOptions));


        //go to lotto + close popups
        open("https://gry.lotto.pl/pl/home.html");
        getWebDriver().manage().window().maximize();
        $(By.xpath("(//div[@class='btn btn-primary'][contains(.,'akceptuję')])[2]")).click();
        $(By.xpath("(//button[@class='btn btn-primary'][contains(.,'Potwierdź')])[2]")).click();

        //login
        $(By.xpath("//a[@class='btn btn-login btn-account-header br-0'][contains(.,'Zaloguj się')]")).click();
        $(By.xpath("//input[@name='loginModal-username']")).setValue(login);
        $(By.xpath("//input[@type='password']")).setValue(password).pressEnter();

        //go to game + close popup
        Thread.sleep(3000);
        $(By.xpath("(//button[@class='btn btn-primary'][contains(.,'Zamknij')])[2]")).shouldBe(visible).shouldHave(text("ZAMKNIJ")).click();
        $(By.xpath("//a[contains(.,'Gry LOTTO')]")).click();
        $(By.xpath("//div[@class='game-image'][contains(.,'Play Mini Lott000007777o')]")).click();

        //fill up coupon
        $(By.xpath("//label[contains(@data-switch-on,'TAK')]")).click();
        $(By.xpath("(//span[contains(.,'zakład')])[4]")).click();
        Thread.sleep(800);
        $(By.xpath("(//span[contains(.,'zakład')])[3]")).click();
        Thread.sleep(800);
        $(By.xpath("(//span[contains(.,'zakład')])[2]")).click();
        //input numbers
        $(By.xpath("//input[@name='boards[0][primarySelections][0]']")).setValue(String.valueOf(num1));
        $(By.xpath("//input[@name='boards[0][primarySelections][1]']")).setValue(String.valueOf(num2));
        $(By.xpath("//input[@name='boards[0][primarySelections][2]']")).setValue(String.valueOf(num3));
        $(By.xpath("//input[@name='boards[0][primarySelections][3]']")).setValue(String.valueOf(num4));
        $(By.xpath("//input[@name='boards[0][primarySelections][4]']")).setValue(String.valueOf(num5));

        $(By.xpath("//button[@class='btn btn-primary'][contains(.,'POTWIERDŹ I ZAPŁAĆ')]")).click();
        $(By.xpath("//a[@class='btn btn-primary confirmationModal-confirmLink'][contains(.,'POTWIERDZAM I PŁACĘ')]")).click();

        //get coupon numbers
        WebElement couponNumberElement = $(By.xpath("//*[@id=\"confirmationItem1\"]/div/div/div[2]/div[1]"));
        String couponNumber = couponNumberElement.getText().split(":")[1];
        System.out.println(couponNumber);
        driver.close();
    }
}
