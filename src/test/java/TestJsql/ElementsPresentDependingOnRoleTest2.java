 /*
  * Copyright (c) 2018 JSQL Sp.z.o.o. (Ltd, LLC) www.jsql.it
  * Licensed under the Commercial license, see www.jsql.it/terms-and-conditions
  */

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
import java.util.HashMap;
import java.util.Map;

import static TestJsql.SetUp.clickWhenReady;
import static TestJsql.SetUp.getWhenVisible;

public class ElementsPresentDependingOnRoleTest2 {
    WebDriver driver;

    public void loginToPage(String userEmail, String userPassword) {
        WebElement email = getWhenVisible(By.xpath("//input[@type='text']"));
        email.sendKeys(userEmail);
        WebElement password = getWhenVisible(By.xpath("//input[@type='password']"));
        password.sendKeys(userPassword);
        password.sendKeys(Keys.ENTER);
    }

    public Map<String, Boolean> resultMap() {
        Map<String, Boolean> resultMap = new HashMap<>();

        //check if some element is present
        Boolean isUsersPresent = driver.findElements(By.xpath("//div[@class='ng-binding'][contains(.,'Users:')]")).size() > 0;
        resultMap.put("isUsersPresent", isUsersPresent);
//        System.out.println("isUsersPresent: " + isUsersPresent);

        //check if 'Apps' is displayed
        Boolean isAppsPresent = driver.findElements(By.xpath("//div[@class='ng-binding'][contains(.,'Apps:')]")).size() > 0;
        resultMap.put("isAppsPresent", isAppsPresent);
//        System.out.println("isAppsPresent: " + isAppsPresent);

        //check if 'DevKey' section is available
        Boolean isDevKeyPresent = driver.findElements(By.xpath("(//*[@class='icon icon-developer-key'])")).size() > 0;
        resultMap.put("isDevKeyPresent", isDevKeyPresent);
//        System.out.println("isDevKeyPresent: " + isDevKeyPresent);

        //check if 'AddNewApi' in Application section is available
        clickWhenReady(By.xpath("//a[contains(.,'Applications')]"));
        Boolean isAddNewApiPresent = driver.findElements(By.xpath("//a[contains(.,'Add App')]")).size() > 0;
        resultMap.put("isAddNewApiPresent", isAddNewApiPresent);
//        System.out.println("isAddNewApiPresent: " + isAddNewApiPresent);

        //check if 'Security' section is available
        Boolean isSecurityPresent = driver.findElements(By.xpath("//a[contains(.,'Security')]")).size() > 0;
        resultMap.put("isSecurityPresent", isSecurityPresent);
//        System.out.println("isSecurityPresent: " + isSecurityPresent);

        //check if 'Team' section is available
        Boolean isTeamPresent = driver.findElements(By.xpath("//a[contains(.,'Team')]")).size() > 0;
        resultMap.put("isTeamPresent", isTeamPresent);
//        System.out.println("isTeamPresent: " + isTeamPresent);

        //check if 'Admins' section is available
        Boolean isAdminsPresent = driver.findElements(By.xpath("//a[contains(.,'Admins')]")).size() > 0;
        resultMap.put("isAdminsPresent", isAdminsPresent);
//        System.out.println("isAdminsPresent: " + isAdminsPresent);

        //check if 'Billing' section is available
        Boolean isBillingPresent = driver.findElements(By.xpath("//a[contains(.,'Billing')]")).size() > 0;
        resultMap.put("isBillingPresent", isBillingPresent);
//        System.out.println("isBillingPresent: " + isBillingPresent);

        return resultMap;
    }


    @BeforeMethod
    @Parameters("browser")
    public void setUp(String browser) {
        //getDriver selenium and go to homepage
        driver = SetUp.getDriverOnlyParam(browser);
    }

    @Test
    public void appDev() throws Exception {
        try {
            //login into page
            String userEmail = "app@dev.pl";
            String userPassword = "test123#";

            loginToPage(userEmail, userPassword);
            Thread.sleep(1500);

            Map<String, Boolean> resultMap = resultMap();

            //Check which elements are present
            Assert.assertTrue(resultMap.get("isAppsPresent") && resultMap.get("isDevKeyPresent"));
            //Check which elements aren't present
            Assert.assertTrue(!resultMap.get("isUsersPresent") && !resultMap.get("isAddNewApiPresent") && !resultMap.get("isSecurityPresent")
                    && !resultMap.get("isTeamPresent") && !resultMap.get("isAdminsPresent") && !resultMap.get("isBillingPresent"));

        } catch (Exception e) {
            String date = Calendar.getInstance().getTime().toString().replace(':', '-').replace(' ', '_');
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshotFile, new File("screenshots\\appDev_" + date + ".png"));
            TestCase.fail();
        }
    }

    @Test
    public void appAdmin() throws Exception {
        try {
            //login into page
            String userEmail = "admin@nowy";
            String userPassword = "test123#";

            loginToPage(userEmail, userPassword);
            Thread.sleep(1500);

            Map<String, Boolean> resultMap = resultMap();
            //Check which elements are present
            Assert.assertTrue(resultMap.get("isAppsPresent") && resultMap.get("isUsersPresent") && resultMap.get("isAddNewApiPresent")
                    && resultMap.get("isSecurityPresent") && resultMap.get("isTeamPresent"));
            //Check which elements aren't present
            Assert.assertTrue(!resultMap.get("isDevKeyPresent") && !resultMap.get("isAdminsPresent") && !resultMap.get("isBillingPresent"));

        } catch (Exception e) {
            String date = Calendar.getInstance().getTime().toString().replace(':', '-').replace(' ', '_');
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshotFile, new File("screenshots\\appAdmin_" + date + ".png"));
            TestCase.fail();
        }
    }

    @Test
    public void companyAdmin() throws Exception {
        try {
            //login into page
            String userEmail = "test@test";
            String userPassword = "test123#";

            loginToPage(userEmail, userPassword);
            Thread.sleep(1500);

            Map<String, Boolean> resultMap = resultMap();
            //Check which elements are present
            Assert.assertTrue(resultMap.get("isAppsPresent") && resultMap.get("isUsersPresent") && resultMap.get("isAddNewApiPresent")
                    && resultMap.get("isSecurityPresent") && resultMap.get("isTeamPresent") && resultMap.get("isAdminsPresent") && resultMap.get("isBillingPresent"));
            //Check which elements aren't present
            Assert.assertTrue(!resultMap.get("isDevKeyPresent"));

        } catch (Exception e) {
            String date = Calendar.getInstance().getTime().toString().replace(':', '-').replace(' ', '_');
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshotFile, new File("screenshots\\companyAdmin_" + date + ".png"));
            TestCase.fail();
        }
    }

    @Test
    public void admin() throws Exception {
        try {
            //login into page
            String userEmail = "admin@2";
            String userPassword = "test123#";

            loginToPage(userEmail, userPassword);
            Thread.sleep(1500);

            Map<String, Boolean> resultMap = resultMap();
            //Check which elements are present
            Assert.assertTrue(resultMap.get("isAppsPresent") && resultMap.get("isUsersPresent") && resultMap.get("isAddNewApiPresent")
                    && resultMap.get("isSecurityPresent") && resultMap.get("isTeamPresent") && resultMap.get("isAdminsPresent") && resultMap.get("isBillingPresent"));
            //Check which elements aren't present
            Assert.assertTrue(!resultMap.get("isDevKeyPresent"));

        } catch (Exception e) {
            String date = Calendar.getInstance().getTime().toString().replace(':', '-').replace(' ', '_');
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshotFile, new File("screenshots\\admin_" + date + ".png"));
            TestCase.fail();
        }
    }

    @AfterMethod
    public void afterSuite() {
        driver.quit();
    }
}
