 /*
  * Copyright (c) 2018 JSQL Sp.z.o.o. (Ltd, LLC) www.jsql.it
  * Licensed under the Commercial license, see www.jsql.it/terms-and-conditions
  */

package TestJsql;

import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Calendar;

import static TestJsql.SetUp.clickWhenReady;
import static TestJsql.SetUp.getWhenVisible;

 public class RegisterNewCompanyAdmins {
     WebDriver driver;

     @BeforeMethod
     @Parameters("browser")
     public void setUp(String browser){

         //getDriver selenium and login into homepage
         driver = SetUp.getDriverOnlyParam(browser);

     }

     @Test
     public void RegisterNewUser() throws Exception {
             for(int i=2; i<10; i++) {
                 //go to mail side, change mail name and copy it
                 driver.get("https://temp-mail.org/en/");
                 clickWhenReady(By.xpath("//a[contains(.,'Change')]"));
                 Thread.sleep(1000);

                 getWhenVisible(By.xpath("//input[@class='form-control']")).sendKeys("comp" + i);
                 clickWhenReady(By.xpath("//button[contains(.,'Save')]"));
                 String randMail = ("comp" + i + "@mailprotech.com");

                 //register new account using copied mail name
                 driver.get("https://test.jsql.it/auth/register");
                 Thread.sleep(500);
                 getWhenVisible(By.xpath("//input[@placeholder='Email']")).sendKeys(randMail);
                 getWhenVisible(By.xpath("//input[@placeholder='Password']")).sendKeys("test123#");
                 getWhenVisible(By.xpath("//input[@placeholder='Repeat-Password']")).sendKeys("test123#");
                 getWhenVisible(By.xpath("//input[@placeholder='First Name']")).sendKeys("selenium");
                 getWhenVisible(By.xpath("//input[@placeholder='Last Name']")).sendKeys("selenium");
                 clickWhenReady(By.xpath("//input[contains(@placeholder,'Choose Plan')]"));
                 clickWhenReady(By.xpath("//p[contains(.,'Starter')]"));
                 clickWhenReady(By.xpath("//button[contains(.,'Register')]"));
                 Thread.sleep(1000);
                 clickWhenReady(By.xpath("//button[contains(.,'Ok')]"));

                 //verify mail address
                 Thread.sleep(3000);
                 driver.get("https://temp-mail.org/en/");
                 Thread.sleep(1500);
                 clickWhenReady(By.xpath("//a[contains(.,'notification@jsql.it')]"));
                 Thread.sleep(500);
                 clickWhenReady(By.xpath("//a[contains(.,'Verify account!')]"));

             }


     }

     @AfterMethod
     public void afterSuite(){
         driver.quit();
     }
 }
