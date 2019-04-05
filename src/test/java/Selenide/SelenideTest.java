package Selenide;

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.*;

import com.codeborne.selenide.Configuration;

import org.junit.Test;
import org.openqa.selenium.By;


public class SelenideTest {

    @Test
    public void checkGoogleSearch() {
        Configuration.browser="firefox";
        open("https://www.google.pl");
        getWebDriver().manage().window().maximize();
        $(By.name("q")).setValue("Selenide").pressEnter();
        $$("#ires div.g").shouldHave(size(10));
        $("#ires .g").shouldBe(visible).shouldHave(
                text("Selenide: concise UI tests in Java"),
                text("selenide.org")
        );
    }
}
