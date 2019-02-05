package TestJsql;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import static TestJsql.SetUp.clickWhenReady;
import static TestJsql.SetUp.getWhenVisible;
import static junit.framework.TestCase.fail;

public class AddApiTest2 {

    WebDriver driver;

    @BeforeMethod
    @Parameters({"browser", "user"})
    public void SetUp(String browser, String user) {

        driver = SetUp.getDriverParam(browser, user);
    }

    @Test
    public void AddNewApi() throws Exception {
        try {
            Thread.sleep(500);

            //check number of api keys
            clickWhenReady(By.xpath("//a[contains(.,'Applications')]"));
            Thread.sleep(500);
            List<WebElement> numberOfApi = driver.findElements(By.xpath("(//a[@class='ng-binding'])"));
            clickWhenReady(By.xpath("//a[contains(.,'Add App')]"));
            Thread.sleep(500);

            //Add new api
            getWhenVisible(By.xpath("//input[@placeholder='Name']")).sendKeys("selenium");
            clickWhenReady(By.xpath("//button[@type='submit']"));
            Thread.sleep(500);
            clickWhenReady(By.xpath("//button[contains(.,'Ok')]"));
            Thread.sleep(500);

            //check number of api keys after creating new api
            List<WebElement> numberOfApiNew = driver.findElements(By.xpath("(//a[@class='ng-binding'])"));
            Thread.sleep(500);

            //delete new api
            clickWhenReady(By.xpath("(//a[contains(.,'selenium')])[1]"));
            clickWhenReady(By.xpath("//button[contains(.,'Delete')]"));
            Thread.sleep(500);
            clickWhenReady(By.xpath("//button[contains(.,'Yes')]"));
            Thread.sleep(500);
            clickWhenReady(By.xpath("//button[contains(.,'Ok')]"));

            Assert.assertNotEquals(numberOfApi.size(), numberOfApiNew.size());
        } catch (Exception e) {
            String date = Calendar.getInstance().getTime().toString().replace(':', '-').replace(' ', '_');
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshotFile, new File("screenshots\\AddNewApi_" + date + ".png"));
            fail();
        }
    }

    @AfterMethod
    public void closeBrowser() {
        driver.quit();
    }
}
