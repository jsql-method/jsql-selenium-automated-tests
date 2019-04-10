 /*
  * Copyright (c) 2018 JSQL Sp.z.o.o. (Ltd, LLC) www.jsql.it
  * Licensed under the Commercial license, see www.jsql.it/terms-and-conditions
  */

 package TestJsqlSelenide;

 import TestJsqlJunit.SetUp;
 import com.codeborne.selenide.Configuration;
 import org.junit.AfterClass;
 import org.junit.Assert;
 import org.junit.BeforeClass;
 import org.junit.Test;
 import org.openqa.selenium.By;

 import static com.codeborne.selenide.Condition.exist;
 import static com.codeborne.selenide.Condition.visible;
 import static com.codeborne.selenide.Selenide.*;
 import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

 public class AddAdminTest {

     @BeforeClass
     public static void setUp() {
         //getDriver selenium and login into homepage
//         WebDriverRunner.getWebDriver() = SetUp.getDriver();
         Configuration.browser = "firefox";
         Configuration.timeout = 15000;
         Configuration.headless=true;

         open("https://customer.jsql.it/auth/login");
         getWebDriver().manage().window().maximize();

         //login into page
         String userEmail = "jsql@5-mail.info";
         String userPassword = "test123#";
         $(By.xpath("//input[@type='text']")).setValue(userEmail);
         $(By.xpath("//input[@type='password']")).setValue(userPassword).pressEnter();
     }

     @Test
     public void addNewAdminTest() throws InterruptedException {
         $(By.xpath("//a[contains(.,'Admins')]")).click();

         int numberOfAdmins = $$(By.xpath("(//td[@class='description-overflow ng-binding'])")).size();

         //Add Admin
         $(By.xpath("//button[@type='submit']")).click();
         $(By.xpath("(//input[@type='text'])[2]")).setValue("test");
         $(By.xpath("(//input[@type='text'])[3]")).setValue("test");
         $(By.xpath("(//input[@type='text'])[1]")).setValue("admin@selenium.pl");
         $(By.xpath("//button[@type='submit'][contains(.,'Add')]")).click();
         $(By.xpath("//button[contains(.,'Ok')]")).click();

         int numberOfAdminsAfterAdd = $$(By.xpath("(//td[@class='description-overflow ng-binding'])")).size();

         //Delete Admin admin@selenium.pl
         $(By.xpath("(((//td[contains(.,'admin@selenium.pl')]/following-sibling::*)/child::*)/child::*)[1]")).click();
         $(By.xpath("//button[contains(.,'Yes')]")).click();
         $(By.xpath("//button[contains(.,'Ok')]")).click();

         int numberOfAdminsAfterDelete = $$(By.xpath("(//td[@class='description-overflow ng-binding'])")).size();

         Assert.assertTrue("Not true", (numberOfAdminsAfterAdd == (numberOfAdmins + 2)) && (numberOfAdmins == numberOfAdminsAfterDelete));
     }

     @Test
     public void demoteAdminTest() throws InterruptedException {
         $(By.xpath("//a[contains(.,'Admins')]")).click();

         //Add new admin
         $(By.xpath("//button[@type='submit']")).click();
         $(By.xpath("(//input[@type='text'])[2]")).setValue("test");
         $(By.xpath("(//input[@type='text'])[3]")).setValue("test");
         $(By.xpath("(//input[@type='text'])[1]")).setValue("demote@selenium.pl");
         $(By.xpath("//button[@type='submit'][contains(.,'Add')]")).click();
         $(By.xpath("//button[contains(.,'Ok')]")).click();

         //Demote admin
         $(By.xpath("(((//td[contains(.,'demote@selenium.pl')]/following-sibling::*)/child::*)/child::*)[2]")).click();
         $(By.xpath("//button[contains(.,'Yes')]")).click();
         $(By.xpath("//button[contains(.,'Ok')]")).click();

         //Go to team
         $(By.xpath("(//a[contains(.,'Team')])[1]")).click();

         //check number of members
         int numberOfMembers = $$(By.xpath("(//td[@class='description-overflow ng-binding'])")).size();

         //check if admin was demote to member
         $(By.xpath("//td[contains(.,'demote@selenium.pl')]")).shouldBe(visible);

         //Delete member
         $(By.xpath("(((//td[contains(.,'demote@selenium.pl')]/following-sibling::*)/child::*)/child::*)[1]")).click();
         $(By.xpath("//button[contains(.,'Yes')]")).click();
         $(By.xpath("//button[contains(.,'Ok')]")).click();

         //check number of members
         int numberOfMembersAfterDelete = $$(By.xpath("(//td[@class='description-overflow ng-binding'])")).size();

         Assert.assertEquals(numberOfMembers, (numberOfMembersAfterDelete + 2));
     }

     @Test
     public void promoteToAdmin() throws InterruptedException {
         $(By.xpath("(//a[contains(.,'Team')])[1]")).click();

         //Add member
         $(By.xpath("//button[@type='submit']")).click();
         $(By.xpath("(//input[@type='text'])[2]")).setValue("test");
         $(By.xpath("(//input[@type='text'])[3]")).setValue("test");
         $(By.xpath("(//input[@type='text'])[1]")).setValue("promote@selenium.pl");
         $(By.xpath("//button[@type='submit'][contains(.,'Add')]")).click();
         $(By.xpath("//button[contains(.,'Ok')]")).click();

         //Promote member
         $(By.xpath("//a[contains(.,'Admins')]")).click();
         $(By.xpath("//button[@type='submit']")).click();
         $(By.xpath("(//input[@type='text'])[2]")).setValue("test");
         $(By.xpath("(//input[@type='text'])[3]")).setValue("test");
         $(By.xpath("(//input[@type='text'])[1]")).setValue("promote@selenium.pl");
         $(By.xpath("//button[@type='submit'][contains(.,'Add')]")).click();
         $(By.xpath("//button[contains(.,'Ok')]")).click();

         //check if new admin is displayed
         $(By.xpath("//td[contains(.,'promote@selenium.pl')]")).shouldBe(visible);

         //Delete admin promote@selenium.pl
         $(By.xpath("(((//td[contains(.,'promote@selenium.pl')]/following-sibling::*)/child::*)/child::*)[1]")).click();
         $(By.xpath("//button[contains(.,'Yes')]")).click();
         $(By.xpath("//button[contains(.,'Ok')]")).click();
     }

     @Test
     public void addExistingAdmin() throws InterruptedException {
         $(By.xpath("//a[contains(.,'Admins')]")).click();

         //check number of admins
         int numberOfAdmins = $$(By.xpath("(//td[@class='description-overflow ng-binding'])")).size();

         //Add new admin
         $(By.xpath("//button[@type='submit']")).click();
         $(By.xpath("(//input[@type='text'])[2]")).setValue("test");
         $(By.xpath("(//input[@type='text'])[3]")).setValue("test");
         $(By.xpath("(//input[@type='text'])[1]")).setValue("existing@selenium.pl");
         $(By.xpath("//button[@type='submit'][contains(.,'Add')]")).click();
         $(By.xpath("//button[contains(.,'Ok')]")).click();

         //Add admin again
         $(By.xpath("//button[@type='submit']")).click();
         $(By.xpath("(//input[@type='text'])[2]")).setValue("test");
         $(By.xpath("(//input[@type='text'])[3]")).setValue("test");
         $(By.xpath("(//input[@type='text'])[1]")).setValue("existing@selenium.pl");
         $(By.xpath("//button[@type='submit'][contains(.,'Add')]")).click();

         //check if message is present

         $(By.xpath("//p[contains(.,'User with given role and email already exists')]")).shouldBe(exist);
         $(By.xpath("//button[contains(.,'Back')]")).click();

         //check number of admins after adding same admin twice
         int numberOfAdminsNew = $$(By.xpath("(//td[@class='description-overflow ng-binding'])")).size();

         //Delete admin promote@selenium.pl
         $(By.xpath("(((//td[contains(.,'existing@selenium.pl')]/following-sibling::*)/child::*)/child::*)[1]")).click();
         $(By.xpath("//button[contains(.,'Yes')]")).click();
         $(By.xpath("//button[contains(.,'Ok')]")).click();

         //check if number of admin is correct
         Assert.assertEquals(numberOfAdminsNew, (numberOfAdmins + 2));
     }

     @AfterClass
     public static void afterSuite() {
         close();
     }
 }
