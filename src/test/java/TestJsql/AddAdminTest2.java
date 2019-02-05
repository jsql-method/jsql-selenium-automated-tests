package TestJsql;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import static TestJsql.SetUp.clickWhenReady;
import static TestJsql.SetUp.getWhenVisible;
import static TestJsql.SetUp.isElementPresent;
import static junit.framework.TestCase.fail;

public class AddAdminTest2 {

    WebDriver driver;

    @BeforeMethod
    @Parameters({"browser", "user"})
    public void getDriverParam(String browser, String user) {
        driver = SetUp.getDriverParam(browser, user);
    }

    @Test
    public void addNewAdminTest() throws InterruptedException, IOException {
        try {
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

            Assert.assertTrue(((numberOfAdminsAfterAdd.size()) == (numberOfAdmins.size() + 2)) && (numberOfAdmins.size() == numberOfAdminsAfterDelete.size()), "Not true");
        } catch (Exception e) {
            String date = Calendar.getInstance().getTime().toString().replace(':', '-').replace(' ', '_');
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshotFile, new File("screenshots\\addNewAdminTest_" + date + ".png"));
            fail();
        }
    }

    @Test
    public void demoteAdminTest() throws InterruptedException, IOException {
        try {
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
            Assert.assertTrue(isElementPresent(By.xpath("//td[contains(.,'demote@selenium.pl')]")), "Not true");

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

        } catch (Exception e) {
            String date = Calendar.getInstance().getTime().toString().replace(':', '-').replace(' ', '_');
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshotFile, new File("screenshots\\demoteAdminTest_" + date + ".png"));
            fail();
        }

    }

    @Test
    public void promoteToAdmin() throws InterruptedException, IOException {
        try {
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
            Assert.assertTrue(isElementPresent(By.xpath("//td[contains(.,'promote@selenium.pl')]")), "not true");

            //Delete admin promote@selenium.pl
            clickWhenReady(By.xpath("(((//td[contains(.,'promote@selenium.pl')]/following-sibling::*)/child::*)/child::*)[1]"));
            Thread.sleep(500);
            clickWhenReady(By.xpath("//button[contains(.,'Yes')]"));
            Thread.sleep(500);
            clickWhenReady(By.xpath("//button[contains(.,'Ok')]"));
            Thread.sleep(500);

        } catch (Exception e) {
            String date = Calendar.getInstance().getTime().toString().replace(':', '-').replace(' ', '_');
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshotFile, new File("screenshots\\promoteToAdmin_" + date + ".png"));
            fail();
        }
    }

    @Test
    public void addExistingAdmin() throws InterruptedException, IOException {
        try {
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
            Assert.assertTrue(isPresent && bool, "Not true");

        } catch (Exception e) {
            String date = Calendar.getInstance().getTime().toString().replace(':', '-').replace(' ', '_');
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshotFile, new File("screenshots\\addExistingAdmin_" + date + ".png"));
            fail();
        }
    }

    @AfterMethod
    public void afterSuite() {
        driver.quit();
    }
}
