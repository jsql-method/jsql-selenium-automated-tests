 /*
  * Copyright (c) 2018 JSQL Sp.z.o.o. (Ltd, LLC) www.jsql.it
  * Licensed under the Commercial license, see www.jsql.it/terms-and-conditions
  */

package TestJsql;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SetUp {
    public static WebDriver driver;

    public static void clickWhenReady(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }

    public static WebElement getWhenVisible(By locator) {
        WebElement element = null;
        WebDriverWait wait = new WebDriverWait(driver, 10);
        element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return element;
    }

    public static boolean isElementPresent(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public static void elementNotDisplayed(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static WebDriver getDriverParam(String browser, String user) {

        if (browser.equalsIgnoreCase("Chrome")) {
            driver = new ChromeDriver();

        } else if (browser.equalsIgnoreCase("Firefox")) {
            driver = new FirefoxDriver();

        } else if (browser.equalsIgnoreCase("Edge")) {
            driver = new EdgeDriver();

        } else if (browser.equalsIgnoreCase("Opera")) {
            String path = "C:/Users/CP24/Desktop/projekt/jsql-selenium-automated-tests/operadriver.exe";
            OperaOptions options = new OperaOptions();
            options.setBinary("C:/Program Files/Opera/57.0.3098.116/opera.exe");
            System.setProperty("webdriver.opera.driver", path);
            driver = new OperaDriver(options);

        } else if (browser.equalsIgnoreCase("Chrome headless")) {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--headless");
            driver = new ChromeDriver(chromeOptions);

        } else if (browser.equalsIgnoreCase("Firefox headless")) {
            FirefoxBinary firefoxBinary = new FirefoxBinary();
            firefoxBinary.addCommandLineOptions("--headless");
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.setBinary(firefoxBinary);
            driver = new FirefoxDriver(firefoxOptions);

        }

        driver.manage().window().maximize();

        driver.get("https://test.jsql.it/auth/login");

        WebDriverWait wait = new WebDriverWait(driver, 10000);
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.tagName("input"), 0));

        //login into page

        String userEmail = null;
        String userPassword = null;
        if (user.equalsIgnoreCase("company admin")) {
            userEmail = "test@test";
            userPassword = "test123";
        } else if (user.equalsIgnoreCase("admin")) {
            userEmail = "admin@nextmail.info";
            userPassword = "test123#";
        } else if (user.equalsIgnoreCase("app admin")) {
            userEmail = "appadmin@nextmail.info";
            userPassword = "test123#";
        } else if (user.equalsIgnoreCase("app dev")) {
            userEmail = "appdev@nextmail.info";
            userPassword = "test123#";
        }

        WebElement email = driver.findElement(By.xpath("//input[@type='text']"));
        email.sendKeys(userEmail);
        WebElement password = driver.findElement(By.xpath("//input[@type='password']"));
        password.sendKeys(userPassword);
        password.sendKeys(Keys.ENTER);

        return driver;

    }

    public static WebDriver getDriverOnlyParam(String browser) {

        if (browser.equalsIgnoreCase("Chrome")) {
            driver = new ChromeDriver();

        } else if (browser.equalsIgnoreCase("Firefox")) {
            driver = new FirefoxDriver();

        } else if (browser.equalsIgnoreCase("Edge")) {
            driver = new EdgeDriver();

        } else if (browser.equalsIgnoreCase("Opera")) {
            String path = "C:/Users/CP24/Desktop/projekt/jsql-selenium-automated-tests/operadriver.exe";
            OperaOptions options = new OperaOptions();
            options.setBinary("C:/Program Files/Opera/57.0.3098.116/opera.exe");
            System.setProperty("webdriver.opera.driver", path);
            driver = new OperaDriver(options);

        } else if (browser.equalsIgnoreCase("Chrome headless")) {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--headless");
            driver = new ChromeDriver(chromeOptions);

        } else if (browser.equalsIgnoreCase("Firefox headless")) {
            FirefoxBinary firefoxBinary = new FirefoxBinary();
            firefoxBinary.addCommandLineOptions("--headless");
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.setBinary(firefoxBinary);
            driver = new FirefoxDriver(firefoxOptions);

        }

        driver.manage().window().maximize();
        driver.get("https://test.jsql.it/auth/login");

        return driver;
    }
}
