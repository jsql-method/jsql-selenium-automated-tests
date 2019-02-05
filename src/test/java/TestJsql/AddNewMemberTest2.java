package TestJsql;

import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import static TestJsql.SetUp.clickWhenReady;
import static TestJsql.SetUp.getWhenVisible;
import static TestJsql.SetUp.isElementPresent;

public class AddNewMemberTest2 {

    WebDriver driver;

    @BeforeMethod
    @Parameters({"browser", "user"})
    public void SetUp(String browser, String user) {

        driver = SetUp.getDriverParam(browser, user);
    }

    @Test
    public void addNewMember() throws Exception {
        try {
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

            Assert.assertTrue((numberOfMembersAfterAdd.size()) == (numberOfMembers.size() + 2), "Not true");

        } catch (Exception e) {
            String date = Calendar.getInstance().getTime().toString().replace(':', '-').replace(' ', '_');
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshotFile, new File("screenshots\\addNewMember_" + date + ".png"));
            TestCase.fail();
        }
    }

    @Test
    public void addExistingMember() throws Exception {
        try {
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

        Assert.assertTrue(isPresent && (numberOfMembersNew.size()) == (numberOfMembers.size() + 2), "Not true");

        } catch (Exception e) {
            String date = Calendar.getInstance().getTime().toString().replace(':', '-').replace(' ', '_');
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshotFile, new File("screenshots\\addExistingMember_" + date + ".png"));
            TestCase.fail();
        }
    }

    @Test
    public void assignMemberToApplication() throws Exception {
        try {
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

            Assert.assertTrue((assignButtons.size() == unassignButtons.size()) && (assignButtons.size() == assignButtonsNew.size()));

            clickWhenReady(By.xpath("//button[contains(.,'Back')]"));
            Thread.sleep(500);

            //Delete member
            clickWhenReady(By.xpath("(((//td[contains(.,'assign@selenium.pl')]/following-sibling::*)/child::*)/child::*)[1]"));
            Thread.sleep(500);
            clickWhenReady(By.xpath("//button[contains(.,'Yes')]"));
            Thread.sleep(500);
            clickWhenReady(By.xpath("//button[contains(.,'Ok')]"));
            Thread.sleep(500);

        } catch (Exception e) {
            String date = Calendar.getInstance().getTime().toString().replace(':', '-').replace(' ', '_');
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshotFile, new File("screenshots\\assignMemberToApplication_" + date + ".png"));
            TestCase.fail();
        }
    }

    @AfterMethod
    public void afterSuite() {
        driver.quit();
    }
}
