 /*
  * Copyright (c) 2018 JSQL Sp.z.o.o. (Ltd, LLC) www.jsql.it
  * Licensed under the Commercial license, see www.jsql.it/terms-and-conditions
  */

 package TestJsqlSelenide;

 import TestJsqlJunit.SetUp;
 import com.codeborne.selenide.Configuration;
 import org.junit.Assert;
 import org.junit.Before;
 import org.junit.Test;
 import org.openqa.selenium.By;
 import org.openqa.selenium.Keys;
 import org.openqa.selenium.WebElement;

 import static com.codeborne.selenide.Selenide.*;
 import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

 public class CopyToClipboardTest {

     @Before
     public void setUp() {
         //getDriver selenium and login into homepage
//         WebDriverRunner.getWebDriver() = SetUp.getDriver();
         Configuration.browser = "firefox";
         Configuration.timeout = 15000;

         open("https://customer.jsql.it/auth/login");
         getWebDriver().manage().window().maximize();

         //login into page
         String userEmail = "jsql2@5-mail.info";
         String userPassword = "test123#";
         $(By.xpath("//input[@type='text']")).setValue(userEmail);
         $(By.xpath("//input[@type='password']")).setValue(userPassword).pressEnter();
     }

     @Test
     public void CopyApiKeyToClipboard() {
         //Check ApiKey displayed
         $(By.xpath("//a[contains(.,'Applications')]")).click();
         $(By.xpath("(//a[contains(@class,'ng-binding')])[2]")).click();

         String displayedApiKey = $(By.id("applicationKey")).getAttribute("value");

         //copy ApiKey to clipboard by clicking button
         $(By.className("btn-primary")).click();
         $(By.xpath("//button[contains(.,'Ok')]")).click();

         //paste copied ApiKey somewhere else
         open("https://customer.jsql.it/profile");
         WebElement input = $(By.xpath("//input[@id='firstName']"));
         input.clear();
         actions()
                 .keyDown(input, Keys.CONTROL)
                 .sendKeys(input, "v")
                 .keyUp(input, Keys.CONTROL)
                 .build().perform();

         //Check if ApiKey was copied correctly
         String copiedApiKey = $(By.xpath("//input[@id='firstName']")).getAttribute("value");
         Assert.assertEquals(displayedApiKey, copiedApiKey);
         close();
     }

     @Test
     public void CopyDevKeyToClipboard() {
         //Check DevKey displayed
         $(By.xpath("//a[contains(.,'Developer key')]")).click();
         String displayedDevKey = $(By.id("developerKey")).getAttribute("value");

         //copy ApiKey to clipboard by clicking button
         $(By.className("btn-primary")).click();
         $(By.xpath("//button[contains(.,'Ok')]")).click();

         //paste copied ApiKey somewhere else
         open("https://customer.jsql.it/profile");
         WebElement input = $(By.xpath("//input[@id='firstName']"));
         input.clear();
         actions()
                 .keyDown(input, Keys.CONTROL)
                 .sendKeys(input, "v")
                 .keyUp(input, Keys.CONTROL)
                 .build().perform();

         //Check if DevKey was copied correctly
         String copiedDevKey = $(By.xpath("//input[@id='firstName']")).getAttribute("value");
         Assert.assertEquals(displayedDevKey, copiedDevKey);
         close();
     }
 }
