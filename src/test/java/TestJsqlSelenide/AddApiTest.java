 /*
  * Copyright (c) 2018 JSQL Sp.z.o.o. (Ltd, LLC) www.jsql.it
  * Licensed under the Commercial license, see www.jsql.it/terms-and-conditions
  */

package TestJsqlSelenide;

import com.codeborne.selenide.junit.TextReport;
import org.junit.*;
import org.junit.rules.TestRule;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

 public class AddApiTest {

     @Rule
     public TestRule report = new TextReport();

     @BeforeClass
     public static void setUp() {
         //getDriver selenide and login into homepage
         SetUp.getDriver();
     }

     @Test
     public void AddNewApi() {

         //check number of api keys
         $(By.xpath("//a[contains(.,'Applications')]")).click();
         int numberOfApi = $$(By.xpath("(//a[@class='ng-binding'])")).size();
         $(By.xpath("//a[contains(.,'Add application')]")).click();

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
