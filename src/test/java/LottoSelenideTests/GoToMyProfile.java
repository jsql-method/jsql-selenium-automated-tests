 /*
  * Copyright (c) 2018 JSQL Sp.z.o.o. (Ltd, LLC) www.jsql.it
  * Licensed under the Commercial license, see www.jsql.it/terms-and-conditions
  */

package LottoSelenideTests;

import LottoSelenideTests.SetUp;
import com.codeborne.selenide.junit.TextReport;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.junit.rules.TestRule;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

 public class GoToMyProfile {

     @Rule
     public TestRule report = new TextReport();

     @BeforeClass
     public static void setUp() throws InterruptedException {
         //getDriver selenide and login into homepage
         SetUp.getDriver();
     }

     @Test
     public void GoToMyProfile() throws InterruptedException {
         $x("(//i[contains(@class,'fas fa-user')])[2]").shouldBe(visible).click();
         $x("//i[@class='ion ion-ios-arrow-left']").shouldBe(visible).click();

         $x("//i[@class='ion ion-navicon-round']").shouldBe(visible).click();
         $x("(//i[@class='fas fa-user'])[1]").shouldBe(visible).click();
         $x("//i[@class='ion ion-ios-arrow-left']").shouldBe(visible).click();

         $x("//li[@class='sort ng-binding active']").shouldBe(visible).shouldHave(text("Sortuj według głównych"));
     }

     @AfterClass
     public static void afterSuite() {
         getWebDriver().close();
     }
 }
