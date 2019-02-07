 /*
  * Copyright (c) 2018 JSQL Sp.z.o.o. (Ltd, LLC) www.jsql.it
  * Licensed under the Commercial license, see www.jsql.it/terms-and-conditions
  */

package TestJsqlJunit;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static TestJsqlJunit.SetUp.clickWhenReady;
import static TestJsqlJunit.SetUp.getWhenVisible;
import static TestJsqlJunit.SetUp.isElementPresent;

public class AddAdminTest {

    public static WebDriver driver;

    @BeforeClass
    public static void setUp() {
        //getDriver selenium and login into homepage
        driver = SetUp.getDriver();
    }

    @Test
    public void addNewAdminTest() throws InterruptedException {
        Thread.sleep(500);
        clickWhenReady(By.xpath("//a[contains(.,'Admins')]"));
        Thread.sleep(500);
        List<WebElement> numberOfAdmins = driver.findElements(By.xpath("(//td[@class='description-overflow ng-binding'])"));

        //Add Admin
        clickWhenReady(By.xpath("//button[@type='submit']"));
        getWhenVisible(By.xpath("(//input[@type='text'])[2]")).sendKeys("test");
        getWhenVisible(By.xpath("(//input[@type='text'])[3]")).sendKeys("test");
        getWhenVisible(By.xpath("(//input[@type='text'])[1]")).sendKeys("admin@selenium.pl");
        clickWhenReady(By.xpath("//button[@type='submit'][contains(.,'Add')]"));
        Thread.sleep(500);
        clickWhenReady(By.xpath("//button[contains(.,'Ok')]"));
        Thread.sleep(500);

        List<WebElement> numberOfAdminsAfterAdd = driver.findElements(By.xpath("(//td[@class='description-overflow ng-binding'])"));

        //Delete Admin admin@selenium.pl
        clickWhenReady(By.xpath("(((//td[contains(.,'admin@selenium.pl')]/following-sibling::*)/child::*)/child::*)[1]"));
        Thread.sleep(500);
        clickWhenReady(By.xpath("//button[contains(.,'Yes')]"));
        Thread.sleep(500);
        clickWhenReady(By.xpath("//button[contains(.,'Ok')]"));
        Thread.sleep(500);

        List<WebElement> numberOfAdminsAfterDelete = driver.findElements(By.xpath("(//td[@class='description-overflow ng-binding'])"));

        Assert.assertTrue("Not true", ((numberOfAdminsAfterAdd.size()) == (numberOfAdmins.size() + 2)) && (numberOfAdmins.size() == numberOfAdminsAfterDelete.size()));
    }

    @Test
    public void demoteAdminTest() throws InterruptedException {
        clickWhenReady(By.xpath("//a[contains(.,'Admins')]"));
        Thread.sleep(500);

        //Add new admin
        clickWhenReady(By.xpath("//button[@type='submit']"));
        getWhenVisible(By.xpath("(//input[@type='text'])[2]")).sendKeys("test");
        getWhenVisible(By.xpath("(//input[@type='text'])[3]")).sendKeys("test");
        getWhenVisible(By.xpath("(//input[@type='text'])[1]")).sendKeys("demote@selenium.pl");
        clickWhenReady(By.xpath("//button[@type='submit'][contains(.,'Add')]"));
        Thread.sleep(500);
        clickWhenReady(By.xpath("//button[contains(.,'Ok')]"));

        //Demote admin
        Thread.sleep(500);
        clickWhenReady(By.xpath("(((//td[contains(.,'demote@selenium.pl')]/following-sibling::*)/child::*)/child::*)[2]"));
        clickWhenReady(By.xpath("//button[contains(.,'Yes')]"));
        Thread.sleep(500);
        clickWhenReady(By.xpath("//button[contains(.,'Ok')]"));

        //Go to team
        Thread.sleep(1000);
        clickWhenReady(By.xpath("(//a[contains(.,'Team')])[1]"));
        Thread.sleep(500);

        //check number of members
        List<WebElement> numberOfMembers = driver.findElements(By.xpath("(//td[@class='description-overflow ng-binding'])"));

        //check if admin was demote to member
        Assert.assertTrue("Not true", isElementPresent(By.xpath("//td[contains(.,'demote@selenium.pl')]")));

        //Delete member
        clickWhenReady(By.xpath("(((//td[contains(.,'demote@selenium.pl')]/following-sibling::*)/child::*)/child::*)[1]"));
        Thread.sleep(500);
        clickWhenReady(By.xpath("//button[contains(.,'Yes')]"));
        Thread.sleep(500);
        clickWhenReady(By.xpath("//button[contains(.,'Ok')]"));
        Thread.sleep(500);

        //check number of members
        List<WebElement> numberOfMembersAfterDelete = driver.findElements(By.xpath("(//td[@class='description-overflow ng-binding'])"));

        Assert.assertTrue(numberOfMembers.size() == (numberOfMembersAfterDelete.size() + 2));
    }

    @Test
    public void promoteToAdmin() throws InterruptedException {
        Thread.sleep(500);
        clickWhenReady(By.xpath("(//a[contains(.,'Team')])[1]"));

        //Add member
        Thread.sleep(500);
        clickWhenReady(By.xpath("//button[@type='submit']"));
        getWhenVisible(By.xpath("(//input[@type='text'])[2]")).sendKeys("test");
        getWhenVisible(By.xpath("(//input[@type='text'])[3]")).sendKeys("test");
        getWhenVisible(By.xpath("(//input[@type='text'])[1]")).sendKeys("promote@selenium.pl");
        clickWhenReady(By.xpath("//button[@type='submit'][contains(.,'Add')]"));
        Thread.sleep(500);
        clickWhenReady(By.xpath("//button[contains(.,'Ok')]"));
        Thread.sleep(500);

        //Promote member
        clickWhenReady(By.xpath("//a[contains(.,'Admins')]"));
        clickWhenReady(By.xpath("//button[@type='submit']"));
        getWhenVisible(By.xpath("(//input[@type='text'])[2]")).sendKeys("test");
        getWhenVisible(By.xpath("(//input[@type='text'])[3]")).sendKeys("test");
        getWhenVisible(By.xpath("(//input[@type='text'])[1]")).sendKeys("promote@selenium.pl");
        clickWhenReady(By.xpath("//button[@type='submit'][contains(.,'Add')]"));
        Thread.sleep(500);
        clickWhenReady(By.xpath("//button[contains(.,'Ok')]"));
        Thread.sleep(500);

        //check if new admin is displayed
        Assert.assertTrue("not true", isElementPresent(By.xpath("//td[contains(.,'promote@selenium.pl')]")));

        //Delete admin promote@selenium.pl
        clickWhenReady(By.xpath("(((//td[contains(.,'promote@selenium.pl')]/following-sibling::*)/child::*)/child::*)[1]"));
        Thread.sleep(500);
        clickWhenReady(By.xpath("//button[contains(.,'Yes')]"));
        Thread.sleep(500);
        clickWhenReady(By.xpath("//button[contains(.,'Ok')]"));
        Thread.sleep(500);
    }

    @Test
    public void addExistingAdmin() throws InterruptedException {
        clickWhenReady(By.xpath("//a[contains(.,'Admins')]"));

        //check number of admins
        List<WebElement> numberOfAdmins = driver.findElements(By.xpath("(//td[@class='description-overflow ng-binding'])"));

        //Add new admin
        clickWhenReady(By.xpath("//button[@type='submit']"));
        getWhenVisible(By.xpath("(//input[@type='text'])[2]")).sendKeys("test");
        getWhenVisible(By.xpath("(//input[@type='text'])[3]")).sendKeys("test");
        getWhenVisible(By.xpath("(//input[@type='text'])[1]")).sendKeys("existing@selenium.pl");
        clickWhenReady(By.xpath("//button[@type='submit'][contains(.,'Add')]"));
        Thread.sleep(1000);
        clickWhenReady(By.xpath("//button[contains(.,'Ok')]"));
        Thread.sleep(500);

        //Add admin again
        clickWhenReady(By.xpath("//button[@type='submit']"));
        getWhenVisible(By.xpath("(//input[@type='text'])[2]")).sendKeys("test");
        getWhenVisible(By.xpath("(//input[@type='text'])[3]")).sendKeys("test");
        getWhenVisible(By.xpath("(//input[@type='text'])[1]")).sendKeys("existing@selenium.pl");
        clickWhenReady(By.xpath("//button[@type='submit'][contains(.,'Add')]"));
        Thread.sleep(500);

        //check if message is present
        boolean isPresent = isElementPresent(By.xpath("//*[@class='text-danger-my-team ng-binding'][contains(.,'User with given role and email already exists')]"));
        System.out.println(isPresent);
        Thread.sleep(500);
        clickWhenReady(By.xpath("//button[contains(.,'Back')]"));

        //check number of admins after adding same admin twice
        List<WebElement> numberOfAdminsNew = driver.findElements(By.xpath("(//td[@class='description-overflow ng-binding'])"));

        //Delete admin promote@selenium.pl
        clickWhenReady(By.xpath("(((//td[contains(.,'existing@selenium.pl')]/following-sibling::*)/child::*)/child::*)[1]"));
        Thread.sleep(500);
        clickWhenReady(By.xpath("//button[contains(.,'Yes')]"));
        Thread.sleep(500);
        clickWhenReady(By.xpath("//button[contains(.,'Ok')]"));
        Thread.sleep(500);

        //check if number of admin is correct
        boolean bool = ((numberOfAdminsNew.size()) == (numberOfAdmins.size() + 2));
        Assert.assertTrue("Not true", isPresent && bool);
    }

    @AfterClass
    public static void afterSuite() {
        driver.quit();
    }
}
