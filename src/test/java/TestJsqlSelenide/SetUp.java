 /*
  * Copyright (c) 2018 JSQL Sp.z.o.o. (Ltd, LLC) www.jsql.it
  * Licensed under the Commercial license, see www.jsql.it/terms-and-conditions
  */

package TestJsqlSelenide;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;


 public class SetUp {

     public static WebDriver getDriver() {

         Configuration.browser="firefox";
         Configuration.timeout = 15000;
         Configuration.headless=true;  //headless mode

         open("https://customer.jsql.it/auth/login");
         getWebDriver().manage().window().maximize();

         //login into page
         String userEmail = "jsql@5-mail.info";
         String userPassword = "test123#";
         $(By.xpath("//input[@type='text']")).setValue(userEmail);
         $(By.xpath("//input[@type='password']")).setValue(userPassword).pressEnter();

         return getWebDriver();
     }

     public static WebDriver getDriverOnly(){
         Configuration.browser="firefox";
         Configuration.timeout = 15000;
         Configuration.headless=true;  //headless mode

         open("https://customer.jsql.it/auth/login");
         getWebDriver().manage().window().maximize();
         return getWebDriver();
     }
 }
