 /*
  * Copyright (c) 2018 JSQL Sp.z.o.o. (Ltd, LLC) www.jsql.it
  * Licensed under the Commercial license, see www.jsql.it/terms-and-conditions
  */

package TestJsql;

import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Calendar;

import static TestJsql.SetUp.clickWhenReady;
import static TestJsql.SetUp.getWhenVisible;

public class CopyToClipboardTest2 {
    WebDriver driver;

    @BeforeMethod
    @Parameters({"browser"})
    public void SetUp(String browser) {
        //getDriver selenium and login into homepage
        driver = SetUp.getDriverOnlyParam(browser);

        WebElement email = driver.findElement(By.xpath("//input[@type='text']"));
        email.sendKeys("appdev@nextmail.info");
        WebElement password = driver.findElement(By.xpath("//input[@type='password']"));
        password.sendKeys("test123#");
        password.sendKeys(Keys.ENTER);
    }

    @Test
    public void CopyApiKeyToClipboard() throws Exception {
        try {
            Thread.sleep(500);

            //Check ApiKey displayed
            clickWhenReady(By.xpath("//a[contains(.,'Applications')]"));
            clickWhenReady(By.xpath("(//a[contains(@class,'ng-binding')])[2]"));
            WebElement displayedApiKeyElement = getWhenVisible(By.id("applicationKey"));
            String displayedApiKey = displayedApiKeyElement.getAttribute("value");

            //copy ApiKey to clipboard by clicking button
            clickWhenReady(By.className("btn-primary"));
            Thread.sleep(500);
            clickWhenReady(By.xpath("//button[contains(.,'Ok')]"));

            //paste copied ApiKey somewhere else
            driver.get("https://test.jsql.it/profile");
            WebElement input = getWhenVisible(By.xpath("//input[@id='firstName']"));
            input.clear();
            Actions action = new Actions(driver);
            action.keyDown(input, Keys.CONTROL)
                    .sendKeys(input, "v")
                    .keyUp(input, Keys.CONTROL)
                    .build().perform();

            //Check if ApiKey was copied correctly
            WebElement copiedApiKeyElement = getWhenVisible(By.xpath("//input[@id='firstName']"));
            String copiedApiKey = copiedApiKeyElement.getAttribute("value");
            Assert.assertEquals(displayedApiKey, copiedApiKey);

        } catch (Exception e) {
            String date = Calendar.getInstance().getTime().toString().replace(':', '-').replace(' ', '_');
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshotFile, new File("screenshots\\CopyApiKeyToClipboard_" + date + ".png"));
            TestCase.fail();
        }
    }

    @Test
    public void CopyDevKeyToClipboard() throws Exception {
        try {
            Thread.sleep(1000);

            //Check DevKey displayed
            clickWhenReady(By.xpath("//a[contains(.,'Developer key')]"));
            WebElement displayedDevKeyElement = getWhenVisible(By.id("developerKey"));
            String displayedDevKey = displayedDevKeyElement.getAttribute("value");

            //copy ApiKey to clipboard by clicking button
            clickWhenReady(By.className("btn-primary"));
            Thread.sleep(500);
            clickWhenReady(By.xpath("//button[contains(.,'Ok')]"));

            //paste copied ApiKey somewhere else
            driver.get("https://test.jsql.it/profile");
            WebElement input = getWhenVisible(By.xpath("//input[@id='firstName']"));
            input.clear();
            Actions action = new Actions(driver);
            action.keyDown(input, Keys.CONTROL)
                    .sendKeys(input, "v")
                    .keyUp(input, Keys.CONTROL)
                    .build().perform();

            //Check if DevKey was copied correctly
            WebElement copiedDevKeyElement = getWhenVisible(By.xpath("//input[@id='firstName']"));
            String copiedDevKey = copiedDevKeyElement.getAttribute("value");
            Assert.assertEquals(displayedDevKey, copiedDevKey);

        } catch (Exception e) {
            String date = Calendar.getInstance().getTime().toString().replace(':', '-').replace(' ', '_');
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshotFile, new File("screenshots\\CopyDevKeyToClipboard_" + date + ".png"));
            TestCase.fail();
        }
    }

    @AfterMethod
    public void afterSuite() {
        driver.quit();
    }
}
