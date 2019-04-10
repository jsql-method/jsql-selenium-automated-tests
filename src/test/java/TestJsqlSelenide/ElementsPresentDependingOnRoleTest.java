 /*
  * Copyright (c) 2018 JSQL Sp.z.o.o. (Ltd, LLC) www.jsql.it
  * Licensed under the Commercial license, see www.jsql.it/terms-and-conditions
  */

 package TestJsqlSelenide;

 import TestJsqlJunit.SetUp;
 import com.codeborne.selenide.Configuration;
 import com.codeborne.selenide.SelenideElement;
 import org.junit.*;
 import org.openqa.selenium.*;

 import java.util.HashMap;
 import java.util.Map;

 import static com.codeborne.selenide.Selenide.$;
 import static com.codeborne.selenide.Selenide.close;
 import static com.codeborne.selenide.Selenide.open;
 import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

 public class ElementsPresentDependingOnRoleTest {

     public void loginToPage(String userEmail, String userPassword) throws InterruptedException {
         $(By.xpath("//input[@type='text']")).setValue(userEmail);
         SelenideElement password = $(By.xpath("//input[@type='password']"));
         password.setValue(userPassword);
         password.pressEnter();
         Thread.sleep(3000);
     }

     public Map<String, Boolean> resultMap() {
         Map<String, Boolean> resultMap = new HashMap<>();

         //check if some element is present
         Boolean isUsersPresent = $(By.xpath("//div[@class='ng-binding'][contains(.,'Users:')]")).isDisplayed();
         resultMap.put("isUsersPresent", isUsersPresent);
//         System.out.println("isUsersPresent: " + isUsersPresent);

         //check if 'Apps' is displayed
         Boolean isAppsPresent = $(By.xpath("//a[contains(.,'Applications')]")).isDisplayed();
         resultMap.put("isAppsPresent", isAppsPresent);
//         System.out.println("isAppsPresent: " + isAppsPresent);

         //check if 'DevKey' section is available
         Boolean isDevKeyPresent = $(By.xpath("(//*[@class='icon icon-developer-key'])")).isDisplayed();
         resultMap.put("isDevKeyPresent", isDevKeyPresent);
//         System.out.println("isDevKeyPresent: " + isDevKeyPresent);

         //check if 'AddNewApi' in Application section is available
         $(By.xpath("//a[contains(.,'Applications')]")).click();
         Boolean isAddNewApiPresent = $(By.xpath("//a[contains(.,'Add App')]")).isDisplayed();
         resultMap.put("isAddNewApiPresent", isAddNewApiPresent);
//         System.out.println("isAddNewApiPresent: " + isAddNewApiPresent);

         //check if 'Security' section is available
         Boolean isSecurityPresent = $(By.xpath("//a[contains(.,'Security')]")).isDisplayed();
         resultMap.put("isSecurityPresent", isSecurityPresent);
//         System.out.println("isSecurityPresent: " + isSecurityPresent);

         //check if 'Team' section is available
         Boolean isTeamPresent = $(By.xpath("//a[contains(.,'Team')]")).isDisplayed();
         resultMap.put("isTeamPresent", isTeamPresent);
//         System.out.println("isTeamPresent: " + isTeamPresent);

         //check if 'Admins' section is available
         Boolean isAdminsPresent = $(By.xpath("//a[contains(.,'Admins')]")).isDisplayed();
         resultMap.put("isAdminsPresent", isAdminsPresent);
//         System.out.println("isAdminsPresent: " + isAdminsPresent);

         //check if 'Billing' section is available
         Boolean isBillingPresent = $(By.xpath("//a[contains(.,'Billing')]")).isDisplayed();
         resultMap.put("isBillingPresent", isBillingPresent);
//         System.out.println("isBillingPresent: " + isBillingPresent);

         return resultMap;
     }


     @Before
     public void setUp() {
         //getDriver selenium and go to homepage
         Configuration.browser = "firefox";
         Configuration.timeout = 15000;
         Configuration.headless=true;

         open("https://customer.jsql.it/auth/login");
         getWebDriver().manage().window().maximize();
     }

     @Test
     public void appDev() throws InterruptedException {
         //login into page
         String userEmail = "jsql2@5-mail.info";
         String userPassword = "test123#";

         loginToPage(userEmail, userPassword);

         Map<String, Boolean> resultMap = resultMap();

         //Check which elements are present
         Assert.assertTrue(resultMap.get("isDevKeyPresent") && resultMap.get("isAppsPresent"));
         //Check which elements aren't present
         Assert.assertTrue(!resultMap.get("isUsersPresent") && !resultMap.get("isAddNewApiPresent") && !resultMap.get("isSecurityPresent")
                 && !resultMap.get("isTeamPresent") && !resultMap.get("isAdminsPresent") && !resultMap.get("isBillingPresent"));
         close();
     }

     @Test
     public void appAdmin() throws InterruptedException {
         //login into page
         String userEmail = "appadmin@clearmail.online";
         String userPassword = "test123#";

         loginToPage(userEmail, userPassword);

         Map<String, Boolean> resultMap = resultMap();
         //Check which elements are present
         Assert.assertTrue(resultMap.get("isAppsPresent") && resultMap.get("isUsersPresent") && resultMap.get("isAddNewApiPresent")
                 && resultMap.get("isSecurityPresent") && resultMap.get("isTeamPresent"));
         //Check which elements aren't present
         Assert.assertTrue(!resultMap.get("isDevKeyPresent") && !resultMap.get("isAdminsPresent") && !resultMap.get("isBillingPresent"));
         close();
     }

     @Test
     public void companyAdmin() throws InterruptedException {
         //login into page
         String userEmail = "jsql@5-mail.info";
         String userPassword = "test123#";

         loginToPage(userEmail, userPassword);

         Map<String, Boolean> resultMap = resultMap();
         //Check which elements are present
         Assert.assertTrue(resultMap.get("isAppsPresent") && resultMap.get("isUsersPresent") && resultMap.get("isAddNewApiPresent")
                 && resultMap.get("isSecurityPresent") && resultMap.get("isTeamPresent") && resultMap.get("isAdminsPresent") && resultMap.get("isBillingPresent"));
         //Check which elements aren't present
         Assert.assertTrue(!resultMap.get("isDevKeyPresent"));
         close();
     }

     //     @Test
//     public void admin() throws InterruptedException {
//         //login into page
//         String userEmail = "admin@2";
//         String userPassword = "test123#";
//
//         loginToPage(userEmail, userPassword);
//         Thread.sleep(1500);
//
//         Map<String, Boolean> resultMap = resultMap();
//         //Check which elements are present
//         Assert.assertTrue(resultMap.get("isAppsPresent") && resultMap.get("isUsersPresent") && resultMap.get("isAddNewApiPresent")
//                 && resultMap.get("isSecurityPresent") && resultMap.get("isTeamPresent") && resultMap.get("isAdminsPresent") && resultMap.get("isBillingPresent"));
//         //Check which elements aren't present
//         Assert.assertTrue(!resultMap.get("isDevKeyPresent"));
//
//     }
     @AfterClass
     public static void afterSuite() {
         close();
     }
 }
