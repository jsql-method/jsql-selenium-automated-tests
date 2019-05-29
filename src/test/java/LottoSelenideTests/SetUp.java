 /*
  * Copyright (c) 2018 JSQL Sp.z.o.o. (Ltd, LLC) www.jsql.it
  * Licensed under the Commercial license, see www.jsql.it/terms-and-conditions
  */

package LottoSelenideTests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.impl.WebElementSelector;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.openqa.selenium.Keys.CONTROL;
import static org.openqa.selenium.Keys.F12;
import static org.openqa.selenium.Keys.SHIFT;


 public class SetUp {

     public static WebDriver getDriver() throws InterruptedException {

         Configuration.browser="firefox";
         Configuration.timeout = 15000;

         FirefoxProfile profile = new FirefoxProfile();
         profile.setPreference("general.useragent.override", "iPhone");

//        CORS
         FirefoxOptions firefoxOptions = new FirefoxOptions();
         firefoxOptions.setProfile(profile);
//         profile.setPreference("devtools.responsiveUI.presets", responsiveMode);
//         firefoxOptions.setHeadless(true);

         WebDriverRunner.setWebDriver(new FirefoxDriver(firefoxOptions));


//         FirefoxResponsiveMode.setViewSize(profile, "Mobile Device", 800, 600);
//         DesiredCapabilities cap = DesiredCapabilities.firefox();
//         cap.setCapability(FirefoxDriver.PROFILE, profile);
//         DRIVER = new FirefoxDriver(cap);
//         DRIVER.manage().window().maximize();
//         FirefoxResponsiveMode.pressShortcut(DRIVER);






         open("http://localhost:9099/");
         getWebDriver().manage().window().setSize(new Dimension(600, 900));
//         getWebDriver().manage().window().maximize();

         $x("/html/body").sendKeys(Keys.F12);
         $(By.cssSelector("body")).sendKeys(Keys.F12);

         WebElement input = $x("//div[@class='language-form']");
//         Actions action = new Actions(getWebDriver());
//         action
//                 .sendKeys(input, F12)
//                 .keyDown(input, CONTROL)
//                 .keyDown(input, SHIFT)
//                 .sendKeys(input, "m")
//                 .keyUp(input, CONTROL)
//                 .keyUp(input, SHIFT)
//                 .build().perform();

         actions()
                 .sendKeys(input, F12)
                 .build().perform();

         //login into page
         String userEmail = "jsqllotto4322@key-mail.net";
         String userPassword = "test123#";

         $(By.className("mr-3")).click();
         $(By.className("register-contener-information")).shouldBe(visible);
         $x("//i[contains(@class,'fas fa-user-lock')]").click();
         $x("//input[@placeholder='Email']").setValue(userEmail);
         $x("//input[@type='password']").setValue(userPassword).pressEnter();

         return getWebDriver();
     }

//     public static WebDriver getDriverOnly(){
//         Configuration.browser="firefox";
//         Configuration.timeout = 15000;
//         Configuration.headless=true;  //headless mode
//
//         open("http://localhost:9099/");
//         getWebDriver().manage().window().maximize();
//         return getWebDriver();
//     }
 }
