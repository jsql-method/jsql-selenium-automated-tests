 /*
  * Copyright (c) 2018 JSQL Sp.z.o.o. (Ltd, LLC) www.jsql.it
  * Licensed under the Commercial license, see www.jsql.it/terms-and-conditions
  */

package TestJsqlJunit;

import TestJsqlJunit.SetUp;
import org.apache.commons.io.FileUtils;
import org.junit.*;
import org.junit.Test;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static TestJsqlJunit.SetUp.clickWhenReady;
import static TestJsqlJunit.SetUp.getWhenVisible;

public class AddApiTest {

    public static WebDriver driver;

    @BeforeClass
    public static void setUp() {
        //getDriver selenium and login into homepage
        driver = SetUp.getDriver();
    }

    @Test
    public void AddNewApi() throws InterruptedException, IOException {
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
        Thread.sleep(200);
        clickWhenReady(By.xpath("//button[contains(.,'Delete')]"));
        Thread.sleep(500);
        clickWhenReady(By.xpath("//button[contains(.,'Yes')]"));
        Thread.sleep(500);
        clickWhenReady(By.xpath("//button[contains(.,'Ok')]"));

        Assert.assertNotEquals(numberOfApi.size(), numberOfApiNew.size());

    }

    @AfterClass
    public static void afterSuite() {
        driver.quit();
    }
}
