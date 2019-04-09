 /*
  * Copyright (c) 2018 JSQL Sp.z.o.o. (Ltd, LLC) www.jsql.it
  * Licensed under the Commercial license, see www.jsql.it/terms-and-conditions
  */

package TestJsqlSelenide;

import com.codeborne.selenide.Configuration;
import org.junit.*;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

 public class AddApiTest {

     @BeforeClass
     public static void setUp() {
         //getDriver selenium and login into homepage
//         WebDriverRunner.getWebDriver() = SetUp.getDriver();
         Configuration.browser="firefox";
         Configuration.timeout = 15000;

         open("https://customer.jsql.it/auth/login");
         getWebDriver().manage().window().maximize();

         //login into page
         String userEmail = "jsql@5-mail.info";
         String userPassword = "test123#";
         $(By.xpath("//input[@type='text']")).setValue(userEmail);
         $(By.xpath("//input[@type='password']")).setValue(userPassword).pressEnter();
     }

     @Test
     public void AddNewApi() throws InterruptedException {

         //check number of api keys
         $(By.xpath("//a[contains(.,'Applications')]")).click();
         int numberOfApi = $$(By.xpath("(//a[@class='ng-binding'])")).size();
         $(By.xpath("//a[contains(.,'Add App')]")).click();

         //Add new api
         $(By.xpath("//input[@placeholder='Name']")).setValue("selenium");
         $(By.xpath("//button[@type='submit']")).click();
         $(By.xpath("//button[contains(.,'Ok')]")).click();

         //check number of api keys after creating new api
         int numberOfApi2 = $$(By.xpath("(//a[@class='ng-binding'])")).size();

         //delete new api
         $(By.xpath("(//a[contains(.,'selenium')])[1]")).click();
         $(By.xpath("//button[contains(.,'Delete')]")).click();
         $(By.xpath("//button[contains(.,'Yes')]")).click();
         $(By.xpath("//button[contains(.,'Ok')]")).click();

         Assert.assertNotEquals(numberOfApi, numberOfApi2);

     }

     @AfterClass
     public static void afterSuite() {
         close();
     }
 }
