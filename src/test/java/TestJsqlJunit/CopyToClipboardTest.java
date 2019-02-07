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
import org.openqa.selenium.interactions.Actions;

import static TestJsqlJunit.SetUp.clickWhenReady;
import static TestJsqlJunit.SetUp.getWhenVisible;


public class CopyToClipboardTest {
    public static WebDriver driver;

    @BeforeClass
    public static void setUp() {
        //getDriver selenium and login into homepage
        driver = SetUp.getDriverOnly();
        WebElement email = driver.findElement(By.xpath("//input[@type='text']"));
        email.sendKeys("app@dev.pl");
        WebElement password = driver.findElement(By.xpath("//input[@type='password']"));
        password.sendKeys("test123#");
        password.sendKeys(Keys.ENTER);
    }

    @Test
    public void CopyApiKeyToClipboard() throws InterruptedException {
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
        driver.get("http://localhost:9090/profile");
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
    }

    @Test
    public void CopyDevKeyToClipboard() throws InterruptedException {
        Thread.sleep(1000);

        //Check DevKey displayed
        clickWhenReady(By.xpath("//*[@id=\"sidebar\"]/ul/li[2]/a"));
        WebElement displayedDevKeyElement = getWhenVisible(By.id("developerKey"));
        String displayedDevKey = displayedDevKeyElement.getAttribute("value");

        //copy ApiKey to clipboard by clicking button
        clickWhenReady(By.className("btn-primary"));
        Thread.sleep(500);
        clickWhenReady(By.xpath("//button[contains(.,'Ok')]"));

        //paste copied ApiKey somewhere else
        driver.get("http://localhost:9090/profile");
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
    }

    @AfterClass
    public static void afterSuite() {
        driver.quit();
    }
}
