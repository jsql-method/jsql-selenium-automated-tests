 /*
  * Copyright (c) 2018 JSQL Sp.z.o.o. (Ltd, LLC) www.jsql.it
  * Licensed under the Commercial license, see www.jsql.it/terms-and-conditions
  */

 package TestJsqlSelenide;

 import com.codeborne.selenide.Configuration;
 import org.junit.AfterClass;
 import org.junit.Assert;
 import org.junit.Before;
 import org.junit.Test;
 import org.openqa.selenium.By;
 import org.openqa.selenium.Keys;
 import org.openqa.selenium.WebElement;

 import static com.codeborne.selenide.Condition.visible;
 import static com.codeborne.selenide.Selenide.*;
 import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

 public class EditProfileTest {

     String oldName = "jsql@5-mail.info";
     String oldPassword = "test123#";
     String correctNewPassword = "Selenium123#";
     String incorrectPassword = "selenium";

     @Before
     public void setUp() {
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
     public void EditName() throws InterruptedException {
         $(By.xpath("//a[contains(@data-target,'#profile')]")).click();
         $(By.xpath("//a[@href='/profile'][contains(.,'Profile')]")).click();

         //Check actual value
         WebElement nameElement = $(By.xpath("//input[@placeholder='First name']"));
         String oldNameValue = nameElement.getAttribute("value");
         WebElement surnameElement = $(By.xpath("//input[@placeholder='Last name']"));
         String oldSurnameValue = surnameElement.getAttribute("value");

         //change name and surname
         nameElement.clear();
         nameElement.sendKeys("SeleniumName");
         surnameElement.clear();
         surnameElement.sendKeys("SeleniumSurname");

         $(By.xpath("//button[contains(.,'Save')]")).click();
         $(By.xpath("//button[@type='button'][contains(.,'Ok')]")).click();

         //Check new value
         String newNameValue = nameElement.getAttribute("value");
         String newSurnameValue = surnameElement.getAttribute("value");

         //change name and surname back to previous values
         nameElement.clear();
         nameElement.sendKeys(oldNameValue);
         surnameElement.clear();
         surnameElement.sendKeys(oldSurnameValue);

         $(By.xpath("//button[contains(.,'Save')]")).click();
         $(By.xpath("//button[@type='button'][contains(.,'Ok')]")).click();

         Assert.assertTrue("Not true", ("SeleniumName".equals(newNameValue)) && ("SeleniumSurname".equals(newSurnameValue)));
         close();
     }

     @Test
     public void ChangePasswordWithIncorrectData() {
         $(By.xpath("//a[contains(@data-target,'#profile')]")).click();
         $(By.xpath("//a[@href='/profile'][contains(.,'Profile')]")).click();

         //change password with incorrect data
         WebElement currentPasswordInput = $(By.xpath("//input[@placeholder='Current password']"));
         currentPasswordInput.sendKeys(oldPassword);
         WebElement newPasswordInput = $(By.xpath("//input[@placeholder='New password']"));
         newPasswordInput.sendKeys(incorrectPassword);
         WebElement repeatPasswordInput = $(By.xpath("//input[@placeholder='Repeat new password']"));
         repeatPasswordInput.sendKeys(incorrectPassword);
         $(By.xpath("//button[contains(.,'Submit')]")).click();

         //check if message is displayed
         $(By.xpath("//div[@class='text-danger-profile ng-binding ng-scope'][contains(.,'Minimum 8 characters, at least one letter, one number and one special character!')]")).shouldBe(visible);

         //new password does not match
         currentPasswordInput.clear();
         currentPasswordInput.sendKeys(oldPassword);
         newPasswordInput.clear();
         newPasswordInput.sendKeys(correctNewPassword);
         repeatPasswordInput.clear();
         repeatPasswordInput.sendKeys(correctNewPassword + "something");
         $(By.xpath("//button[contains(.,'Submit')]")).click();

         //check if message is displayed
         $(By.xpath("//div[@class='text-danger-profile ng-binding ng-scope'][contains(.,'Passwords does not match!')]")).shouldBe(visible);

         //old password does not match
         currentPasswordInput.clear();
         currentPasswordInput.sendKeys(oldPassword + "selenium");
         newPasswordInput.clear();
         newPasswordInput.sendKeys(correctNewPassword);
         repeatPasswordInput.clear();
         repeatPasswordInput.sendKeys(correctNewPassword);
         $(By.xpath("//button[contains(.,'Submit')]")).click();

         //check if message is displayed
         $(By.xpath("//div[@class='contain-validation-contain-profile ng-binding'][contains(.,'Old password is incorrect')]")).shouldBe(visible);

         close();
     }

     @Test
     public void ChangePasswordWithCorrectData() throws InterruptedException {
         $(By.xpath("//a[contains(@data-target,'#profile')]")).click();
         $(By.xpath("//a[@href='/profile'][contains(.,'Profile')]")).click();

         //change password with correct data
         WebElement currentPasswordInput = $(By.xpath("//input[@placeholder='Current password']"));
         currentPasswordInput.sendKeys(oldPassword);
         WebElement newPasswordInput = $(By.xpath("//input[@placeholder='New password']"));
         newPasswordInput.sendKeys(correctNewPassword);
         WebElement repeatPasswordInput = $(By.xpath("//input[@placeholder='Repeat new password']"));
         repeatPasswordInput.sendKeys(correctNewPassword);
         $(By.xpath("//button[contains(.,'Submit')]")).click();
         $(By.xpath("//button[contains(.,'Ok')]")).click();

         //logout
         $(By.xpath("//a[@class='logout-sidebar']")).click();

         //login with old password
         $(By.xpath("//input[@type='text']")).setValue(oldName);
         WebElement passwordInput = $(By.xpath("//input[@type='password']"));
         passwordInput.sendKeys(oldPassword);
         passwordInput.sendKeys(Keys.ENTER);
         $(By.xpath("//div[@class='contain-validation-contain ng-binding'][contains(.,'Password not match')]")).shouldBe(visible);

         //login with new password
         passwordInput.clear();
         passwordInput.sendKeys(correctNewPassword);
         passwordInput.sendKeys(Keys.ENTER);

         //change password to previous one
         $(By.xpath("//a[contains(@data-target,'#profile')]")).click();
         $(By.xpath("//a[@href='/profile'][contains(.,'Profile')]")).click();
         $(By.xpath("//input[@placeholder='Current password']")).setValue(correctNewPassword);
         $(By.xpath("//input[@placeholder='New password']")).setValue(oldPassword);
         $(By.xpath("//input[@placeholder='Repeat new password']")).setValue(oldPassword);
         $(By.xpath("//button[contains(.,'Submit')]")).click();
         $(By.xpath("//button[contains(.,'Ok')]")).click();

         close();
     }

     @AfterClass
     public static void afterSuite() {
         close();
     }
 }
