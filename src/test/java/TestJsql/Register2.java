package TestJsql;

import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Calendar;

import static TestJsql.SetUp.clickWhenReady;
import static TestJsql.SetUp.getWhenVisible;

public class Register2 {
    WebDriver driver;

    @BeforeMethod
    @Parameters("browser")
    public void setUp(String browser){

        //getDriver selenium and login into homepage
        driver = SetUp.getDriverOnlyParam(browser);

    }
    @Test
    public void RegisterNewUser() throws Exception {
        try {
        //go to mail side, change mail name and copy it
        driver.get("https://temp-mail.org/en/");

        clickWhenReady(By.xpath("//a[contains(.,'Change')]"));
        Thread.sleep(1000);

        String randNum = String.valueOf(System.currentTimeMillis());
        getWhenVisible(By.xpath("//input[@class='form-control']")).sendKeys(randNum);
        clickWhenReady(By.xpath("//button[contains(.,'Save')]"));
        String randMail = (randNum + "@next2cloud.info");

        //register new account using copied mail name
        driver.get("http://localhost:9090/auth/register");
        Thread.sleep(500);
        getWhenVisible(By.xpath("//input[@placeholder='Email']")).sendKeys(randMail);
        getWhenVisible(By.xpath("//input[@placeholder='Password']")).sendKeys("test123#");
        getWhenVisible(By.xpath("//input[@placeholder='Repeat-Password']")).sendKeys("test123#");
        getWhenVisible(By.xpath("//input[@placeholder='First Name']")).sendKeys("selenium");
        getWhenVisible(By.xpath("//input[@placeholder='Last Name']")).sendKeys("selenium");
        clickWhenReady(By.xpath("//button[contains(.,'Register')]"));
        Thread.sleep(1000);
        clickWhenReady(By.xpath("//button[contains(.,'Ok')]"));

        //verify mail address
        Thread.sleep(3000);
        driver.get("https://temp-mail.org/en/");
        Thread.sleep(1500);
        clickWhenReady(By.xpath("//a[contains(.,'mailer@softwarecartoon.com')]"));
        Thread.sleep(500);
        clickWhenReady(By.xpath("//a[contains(.,'Verify account!')]"));

        //login on newly registered account
        Thread.sleep(1000);
        String childWindow = driver.getWindowHandle();
        driver.switchTo().window(childWindow);

        driver.get("http://localhost:9090/auth/login");

        getWhenVisible(By.xpath("//input[@type='text']")).sendKeys(randMail);
        WebElement password = driver.findElement(By.xpath("//input[@type='password']"));
        password.sendKeys("test123#");
        password.sendKeys(Keys.ENTER);



        } catch (Exception e) {
            String date = Calendar.getInstance().getTime().toString().replace(':', '-').replace(' ', '_');
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshotFile, new File("screenshots\\RegisterNewUser_" + date + ".png"));
            TestCase.fail();
        }
    }

    @AfterMethod
    public void afterSuite(){
        driver.quit();
    }
}
