 /*
  * Copyright (c) 2018 JSQL Sp.z.o.o. (Ltd, LLC) www.jsql.it
  * Licensed under the Commercial license, see www.jsql.it/terms-and-conditions
  */

package TestJsqlJunit;

import TestJsqlJunit.SetUp;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static TestJsqlJunit.SetUp.clickWhenReady;
import static TestJsqlJunit.SetUp.getWhenVisible;
import static TestJsqlJunit.SetUp.isElementPresent;

public class EditProfileTest {
    public static WebDriver driver;
    WebDriverWait wait = new WebDriverWait(driver, 10000);

    String oldName = "test@test";
    String oldPassword = "test123#";
    String correctNewPassword = "Selenium123#";
    String incorrectPassword = "selenium";

    @BeforeClass
    public static void setUp(){
        //getDriver selenium and login into homepage
        driver = SetUp.getDriver();
    }

    @Test
    public void EditName() throws InterruptedException {
        Thread.sleep(500);
        driver.get("http://localhost:9090/profile");

        //Check actual value
        WebElement nameElement = getWhenVisible(By.xpath("//input[@placeholder='First name']"));
        String oldNameValue = nameElement.getAttribute("value");
        WebElement surnameElement = getWhenVisible(By.xpath("//input[@placeholder='Last name']"));
        String oldSurnameValue = surnameElement.getAttribute("value");

        //change name and surname
        nameElement.clear();
        nameElement.sendKeys("SeleniumName");
        surnameElement.clear();
        surnameElement.sendKeys("SeleniumSurname");

        clickWhenReady(By.xpath("//button[contains(.,'Save')]"));
        Thread.sleep(500);
        clickWhenReady(By.xpath("//button[@type='button'][contains(.,'Ok')]"));
        Thread.sleep(500);

        //Check new value
        String newNameValue = nameElement.getAttribute("value");
        String newSurnameValue = surnameElement.getAttribute("value");

        //change name and surname back to previous values
        nameElement.clear();
        nameElement.sendKeys(oldNameValue);
        surnameElement.clear();
        surnameElement.sendKeys(oldSurnameValue);

        clickWhenReady(By.xpath("//button[contains(.,'Save')]"));
        Thread.sleep(500);
        clickWhenReady(By.xpath("//button[@type='button'][contains(.,'Ok')]"));
        Thread.sleep(500);

        Assert.assertTrue("Not true", ("SeleniumName".equals(newNameValue)) && ("SeleniumSurname".equals(newSurnameValue)));
    }

    @Test
    public void ChangePasswordWithIncorrectData() throws InterruptedException {
        Thread.sleep(500);
        driver.get("http://localhost:9090/profile");

        //change password with incorrect data
        WebElement currentPasswordInput = getWhenVisible(By.xpath("//input[@placeholder='Current password']"));
        currentPasswordInput.sendKeys(oldPassword);
        WebElement newPasswordInput = getWhenVisible(By.xpath("//input[@placeholder='New password']"));
        newPasswordInput.sendKeys(incorrectPassword);
        WebElement repeatPasswordInput = getWhenVisible(By.xpath("//input[@placeholder='Repeat new password']"));
        repeatPasswordInput.sendKeys(incorrectPassword);
        clickWhenReady(By.xpath("//button[contains(.,'Submit')]"));

        //check if message is displayed
        Boolean isIncorrectPasswordMessagePresent = isElementPresent(By.xpath("//div[@class='text-danger-profile ng-binding ng-scope']" +
                "[contains(.,'Minimum 8 characters, at least one letter, one number and one special character!')]"));

        //new password does not match
        currentPasswordInput.clear();
        currentPasswordInput.sendKeys(oldPassword);
        newPasswordInput.clear();
        newPasswordInput.sendKeys(correctNewPassword);
        repeatPasswordInput.clear();
        repeatPasswordInput.sendKeys(correctNewPassword + "something");
        clickWhenReady(By.xpath("//button[contains(.,'Submit')]"));

        //check if message is displayed
        Boolean isPasswordDoesNotMatchMessagePresent = isElementPresent(By.xpath("//div[@class='text-danger-profile ng-binding ng-scope']" +
                "[contains(.,'Passwords does not match!')]"));

        //old password does not match
        currentPasswordInput.clear();
        currentPasswordInput.sendKeys(oldPassword + "selenium");
        newPasswordInput.clear();
        newPasswordInput.sendKeys(correctNewPassword);
        repeatPasswordInput.clear();
        repeatPasswordInput.sendKeys(correctNewPassword);
        clickWhenReady(By.xpath("//button[contains(.,'Submit')]"));
        Thread.sleep(500);

        //check if message is displayed
        Boolean isOldPasswordIsIncorrectMessagePresent = isElementPresent(By.xpath("//div[@class='contain-validation-contain-profile ng-binding']" +
                "[contains(.,'Old password is incorrect')]"));

        Assert.assertTrue("Not true", isIncorrectPasswordMessagePresent && isOldPasswordIsIncorrectMessagePresent && isPasswordDoesNotMatchMessagePresent);
    }

    @Test
    public void ChangePasswordWithCorrectData() throws InterruptedException {
        Thread.sleep(500);
        driver.get("http://localhost:9090/profile");

        //change password with correct data
        Thread.sleep(500);
        WebElement currentPasswordInput = getWhenVisible(By.xpath("//input[@placeholder='Current password']"));
        currentPasswordInput.sendKeys(oldPassword);
        WebElement newPasswordInput = getWhenVisible(By.xpath("//input[@placeholder='New password']"));
        newPasswordInput.sendKeys(correctNewPassword);
        WebElement repeatPasswordInput = getWhenVisible(By.xpath("//input[@placeholder='Repeat new password']"));
        repeatPasswordInput.sendKeys(correctNewPassword);
        clickWhenReady(By.xpath("//button[contains(.,'Submit')]"));
        Thread.sleep(500);
        clickWhenReady(By.xpath("//button[contains(.,'Ok')]"));

        //logout
        Thread.sleep(500);
        clickWhenReady(By.xpath("//a[@class='logout-sidebar']"));
        Thread.sleep(500);

        //login with old password
        WebElement loginInput = getWhenVisible(By.xpath("//input[@type='text']"));
        loginInput.sendKeys(oldName);
        WebElement passwordInput = getWhenVisible(By.xpath("//input[@type='password']"));
        passwordInput.sendKeys(oldPassword);
        passwordInput.sendKeys(Keys.ENTER);
        Boolean isPasswordDoesNotMatchPresent = isElementPresent(By.xpath("//div[@class='contain-validation-contain-login ng-binding'][contains(.,'Password not match')]"));

        //login with new password
        Thread.sleep(500);
        passwordInput.clear();
        passwordInput.sendKeys(correctNewPassword);
        passwordInput.sendKeys(Keys.ENTER);
        Thread.sleep(500);

        //change password to previous one
        driver.get("http://localhost:9090/profile");
        WebElement currentPasswordInput2 = getWhenVisible(By.xpath("//input[@placeholder='Current password']"));
        currentPasswordInput2.sendKeys(correctNewPassword);
        WebElement newPasswordInput2 = getWhenVisible(By.xpath("//input[@placeholder='New password']"));
        newPasswordInput2.sendKeys(oldPassword);
        WebElement repeatPasswordInput2 = getWhenVisible(By.xpath("//input[@placeholder='Repeat new password']"));
        repeatPasswordInput2.sendKeys(oldPassword);
        clickWhenReady(By.xpath("//button[contains(.,'Submit')]"));
        Thread.sleep(500);
        clickWhenReady(By.xpath("//button[contains(.,'Ok')]"));

        Assert.assertTrue(isPasswordDoesNotMatchPresent);
    }


    @AfterClass
    public static void afterSuite(){

        driver.quit();
    }
}
