 /*
  * Copyright (c) 2018 JSQL Sp.z.o.o. (Ltd, LLC) www.jsql.it
  * Licensed under the Commercial license, see www.jsql.it/terms-and-conditions
  */

 package TestJsqlSelenide;

 import com.codeborne.selenide.Configuration;
 import com.codeborne.selenide.ElementsCollection;
 import org.junit.AfterClass;
 import org.junit.Assert;
 import org.junit.BeforeClass;
 import org.junit.Test;
 import org.openqa.selenium.By;

 import static com.codeborne.selenide.Condition.exist;
 import static com.codeborne.selenide.Selenide.*;
 import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

 public class AddNewMemberTest {


     @BeforeClass
     public static void setUp() {
         //getDriver selenium and login into homepage
//         WebDriverRunner.getWebDriver() = SetUp.getDriver();
         Configuration.browser = "firefox";
         Configuration.timeout = 15000;

         open("https://customer.jsql.it/auth/login");
         getWebDriver().manage().window().maximize();

         //login into page
         String userEmail = "test@test";
         String userPassword = "test123";
         $(By.xpath("//input[@type='text']")).setValue(userEmail);
         $(By.xpath("//input[@type='password']")).setValue(userPassword).pressEnter();
     }

     @Test
     public void addNewMember() throws InterruptedException {
         $(By.xpath("(//a[contains(.,'Team')])[1]")).click();

         int numberOfMembers = $$(By.xpath("(//td[@class='description-overflow ng-binding'])")).size();

         //Add member
         $(By.xpath("//button[@type='submit']")).click();
         $(By.xpath("(//input[@type='text'])[2]")).setValue("test");
         $(By.xpath("(//input[@type='text'])[3]")).setValue("test");
         $(By.xpath("(//input[@type='text'])[1]")).setValue("member@selenium.pl");
         $(By.xpath("//button[@type='submit'][contains(.,'Add')]")).click();
         $(By.xpath("//button[contains(.,'Ok')]")).click();

         int numberOfMembersAfterAdd = $$(By.xpath("(//td[@class='description-overflow ng-binding'])")).size();


         //Delete member
         $(By.xpath("(((//td[contains(.,'member@selenium.pl')]/following-sibling::*)/child::*)/child::*)[1]")).click();
         $(By.xpath("//button[contains(.,'Yes')]")).click();
         $(By.xpath("//button[contains(.,'Ok')]")).click();

         Assert.assertEquals(numberOfMembersAfterAdd, (numberOfMembers + 2));
     }

     @Test
     public void addExistingMember() throws InterruptedException {
         $(By.xpath("(//a[contains(.,'Team')])[1]")).click();

         //check number of members
         int numberOfMembers = $$(By.xpath("(//td[@class='description-overflow ng-binding'])")).size();

         //Add member
         $(By.xpath("//button[@type='submit']")).click();
         $(By.xpath("(//input[@type='text'])[2]")).setValue("test");
         $(By.xpath("(//input[@type='text'])[3]")).setValue("test");
         $(By.xpath("(//input[@type='text'])[1]")).setValue("existingMember@selenium.pl");
         $(By.xpath("//button[@type='submit'][contains(.,'Add')]")).click();
         $(By.xpath("//button[contains(.,'Ok')]")).click();

         //Add member again
         $(By.xpath("//button[@type='submit']")).click();
         $(By.xpath("(//input[@type='text'])[2]")).setValue("test");
         $(By.xpath("(//input[@type='text'])[3]")).setValue("test");
         $(By.xpath("(//input[@type='text'])[1]")).setValue("existingMember@selenium.pl");
         $(By.xpath("//button[@type='submit'][contains(.,'Add')]")).click();

         //check if message is present
         $(By.xpath("//p[contains(.,'The given e-mail is already registered in the system. Use the Forgot Password option')]")).shouldBe(exist);
         $(By.xpath("//button[contains(.,'Back')]")).click();

         //check number of members
         int numberOfMembersNew = $$(By.xpath("(//td[@class='description-overflow ng-binding'])")).size();

         //Delete member
         $(By.xpath("(((//td[contains(.,'existingMember@selenium.pl')]/following-sibling::*)/child::*)/child::*)[1]")).click();
         $(By.xpath("//button[contains(.,'Yes')]")).click();
         $(By.xpath("//button[contains(.,'Ok')]")).click();

         Assert.assertEquals(numberOfMembersNew, (numberOfMembers + 2));
     }

     @Test
     public void assignMemberToApplication() throws InterruptedException {
         $(By.xpath("(//a[contains(.,'Team')])[1]")).click();

         //Add member
         $(By.xpath("//button[@type='submit']")).click();
         $(By.xpath("(//input[@type='text'])[2]")).setValue("test");
         $(By.xpath("(//input[@type='text'])[3]")).setValue("test");
         $(By.xpath("(//input[@type='text'])[1]")).setValue("assign@selenium.pl");
         $(By.xpath("//button[@type='submit'][contains(.,'Add')]")).click();
         $(By.xpath("//button[contains(.,'Ok')]")).click();


         //check number of assign buttons
         $(By.xpath("(((//td[contains(.,'assign@selenium.pl')]/following-sibling::*)/child::*)/child::*)[2]")).click();
         ElementsCollection assignButtons = $$(By.xpath("(//button[contains(.,'Assign')])"));
         int assignButtonsSize = assignButtons.size();

         //Assign applications
         for (int i = 0; i < assignButtonsSize; i++) {
             assignButtons.get(0).click();
             Thread.sleep(200);
         }

         //check number of unassign buttons
         ElementsCollection unassignButtons = $$(By.xpath("(//button[contains(.,'Unassign')])"));
         int unassignButtonsSize = unassignButtons.size();

         //Unassign application
         for (int i = 0; i < unassignButtonsSize; i++) {
             unassignButtons.get(0).click();
             Thread.sleep(200);
         }

         //check number of assign buttons again
         int assignButtonsNew = $$(By.xpath("(//button[contains(.,'Assign')])")).size();

         $(By.xpath("//button[contains(.,'Back')]")).click();

         //Delete member
         $(By.xpath("(((//td[contains(.,'assign@selenium.pl')]/following-sibling::*)/child::*)/child::*)[1]")).click();
         $(By.xpath("//button[contains(.,'Yes')]")).click();
         $(By.xpath("//button[contains(.,'Ok')]")).click();

         Assert.assertTrue("Not true", (assignButtonsSize == unassignButtonsSize && assignButtonsSize == assignButtonsNew));
     }

     @AfterClass
     public static void afterSuite() {
         close();
     }
 }
