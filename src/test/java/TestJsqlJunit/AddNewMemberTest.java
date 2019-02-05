package TestJsqlJunit;

import TestJsqlJunit.SetUp;
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

public class AddNewMemberTest {

    public static WebDriver driver;

    @BeforeClass
    public static void setUp() {
        //getDriver selenium and login into homepage
        driver = SetUp.getDriver();
    }

    @Test
    public void addNewMember() throws InterruptedException {
        clickWhenReady(By.xpath("(//a[contains(.,'Team')])[1]"));

        List<WebElement> numberOfMembers = driver.findElements(By.xpath("(//td[@class='description-overflow ng-binding'])"));

        //Add member
        clickWhenReady(By.xpath("//button[@type='submit']"));
        getWhenVisible(By.xpath("(//input[@type='text'])[2]")).sendKeys("test");
        getWhenVisible(By.xpath("(//input[@type='text'])[3]")).sendKeys("test");
        getWhenVisible(By.xpath("(//input[@type='text'])[1]")).sendKeys("member@selenium.pl");
        clickWhenReady(By.xpath("//button[@type='submit'][contains(.,'Add')]"));
        Thread.sleep(500);
        clickWhenReady(By.xpath("//button[contains(.,'Ok')]"));
        Thread.sleep(1000);

        List<WebElement> numberOfMembersAfterAdd = driver.findElements(By.xpath("(//td[@class='description-overflow ng-binding'])"));


        //Delete member
        clickWhenReady(By.xpath("(((//td[contains(.,'member@selenium.pl')]/following-sibling::*)/child::*)/child::*)[1]"));
        Thread.sleep(500);
        clickWhenReady(By.xpath("//button[contains(.,'Yes')]"));
        Thread.sleep(500);
        clickWhenReady(By.xpath("//button[contains(.,'Ok')]"));
        Thread.sleep(500);

        Assert.assertTrue("Not true", (numberOfMembersAfterAdd.size()) == (numberOfMembers.size() + 2));
    }

    @Test
    public void addExistingMember() throws InterruptedException {
        Thread.sleep(500);
        clickWhenReady(By.xpath("(//a[contains(.,'Team')])[1]"));
        Thread.sleep(500);

        //check number of members
        List<WebElement> numberOfMembers = driver.findElements(By.xpath("(//td[@class='description-overflow ng-binding'])"));

        //Add member
        clickWhenReady(By.xpath("//button[@type='submit']"));
        getWhenVisible(By.xpath("(//input[@type='text'])[2]")).sendKeys("test");
        getWhenVisible(By.xpath("(//input[@type='text'])[3]")).sendKeys("test");
        getWhenVisible(By.xpath("(//input[@type='text'])[1]")).sendKeys("existing@selenium.pl");
        clickWhenReady(By.xpath("//button[@type='submit'][contains(.,'Add')]"));
        Thread.sleep(500);
        clickWhenReady(By.xpath("//button[contains(.,'Ok')]"));
        Thread.sleep(500);

        //Add member again
        clickWhenReady(By.xpath("//button[@type='submit']"));
        getWhenVisible(By.xpath("(//input[@type='text'])[2]")).sendKeys("test");
        getWhenVisible(By.xpath("(//input[@type='text'])[3]")).sendKeys("test");
        getWhenVisible(By.xpath("(//input[@type='text'])[1]")).sendKeys("existing@selenium.pl");
        clickWhenReady(By.xpath("//button[@type='submit'][contains(.,'Add')]"));
        Thread.sleep(500);

        //check if message is present
        boolean isPresent = isElementPresent(By.xpath("//*[@class='text-danger-my-team ng-binding'][contains(.,'The given e-mail is already registered in the system. Use the Forgot Password option')]"));
        System.out.println(isPresent);
        Thread.sleep(500);
        clickWhenReady(By.xpath("//button[contains(.,'Back')]"));

        //check number of members
        Thread.sleep(500);
        List<WebElement> numberOfMembersNew = driver.findElements(By.xpath("(//td[@class='description-overflow ng-binding'])"));

        //Delete member
        clickWhenReady(By.xpath("(((//td[contains(.,'existing@selenium.pl')]/following-sibling::*)/child::*)/child::*)[1]"));
        Thread.sleep(500);
        clickWhenReady(By.xpath("//button[contains(.,'Yes')]"));
        Thread.sleep(500);
        clickWhenReady(By.xpath("//button[contains(.,'Ok')]"));
        Thread.sleep(500);

        Assert.assertTrue("Not true", isPresent && (numberOfMembersNew.size()) == (numberOfMembers.size() + 2));
    }

    @Test
    public void assignMemberToApplication() throws InterruptedException {
        Thread.sleep(500);
        clickWhenReady(By.xpath("(//a[contains(.,'Team')])[1]"));
        Thread.sleep(500);

        //Add member
        clickWhenReady(By.xpath("//button[@type='submit']"));
        getWhenVisible(By.xpath("(//input[@type='text'])[2]")).sendKeys("test");
        getWhenVisible(By.xpath("(//input[@type='text'])[3]")).sendKeys("test");
        getWhenVisible(By.xpath("(//input[@type='text'])[1]")).sendKeys("assign@selenium.pl");
        clickWhenReady(By.xpath("//button[@type='submit'][contains(.,'Add')]"));
        Thread.sleep(500);
        clickWhenReady(By.xpath("//button[contains(.,'Ok')]"));
        Thread.sleep(500);


        //check number of assign buttons
        clickWhenReady(By.xpath("(((//td[contains(.,'assign@selenium.pl')]/following-sibling::*)/child::*)/child::*)[2]"));
        Thread.sleep(500);
        List<WebElement> assignButtons = driver.findElements(By.xpath("(//button[contains(.,'Assign')])"));

        //Assign applications
        for (int i = 0; i < assignButtons.size(); i++) {
            assignButtons.get(i).click();
            Thread.sleep(200);
        }

        Thread.sleep(500);
        //check number of unassign buttons
        List<WebElement> unassignButtons = driver.findElements(By.xpath("(//button[contains(.,'Unassign')])"));
        Thread.sleep(500);

        //Unassign application
        for (int i = 0; i < unassignButtons.size(); i++) {
            unassignButtons.get(i).click();
            Thread.sleep(200);
        }

        //check number of assign buttons again
        List<WebElement> assignButtonsNew = driver.findElements(By.xpath("(//button[contains(.,'Assign')])"));
        Thread.sleep(500);

        clickWhenReady(By.xpath("//button[contains(.,'Back')]"));
        Thread.sleep(500);

        //Delete member
        clickWhenReady(By.xpath("(((//td[contains(.,'assign@selenium.pl')]/following-sibling::*)/child::*)/child::*)[1]"));
        Thread.sleep(500);
        clickWhenReady(By.xpath("//button[contains(.,'Yes')]"));
        Thread.sleep(500);
        clickWhenReady(By.xpath("//button[contains(.,'Ok')]"));
        Thread.sleep(500);

        Assert.assertTrue("Not true", (assignButtons.size() == unassignButtons.size()) && (assignButtons.size() == assignButtonsNew.size()));
    }

    @AfterClass
    public static void afterSuite() {
        driver.quit();
    }
}
