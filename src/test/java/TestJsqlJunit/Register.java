package TestJsqlJunit;

import TestJsqlJunit.SetUp;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Register {
    public static WebDriver driver;

    @BeforeClass
    public static void setUp(){

        //getDriver selenium and login into homepage
        driver = SetUp.getDriver();

    }
    @Test
    public void RegisterNewUser() throws InterruptedException {

        //go to mail side, change mail name and copy it
        driver.get("https://temp-mail.org/en/");

        driver.findElement(By.xpath("//a[contains(.,'Change')]")).click();
        Thread.sleep(1000);

        String randNum = String.valueOf(System.currentTimeMillis());
        System.out.println(randNum);
        driver.findElement(By.xpath("//input[@class='form-control']")).sendKeys(randNum);
        driver.findElement(By.xpath("//button[contains(.,'Save')]")).click();
        String randMail = (randNum + "@2mailnext.com");

        //register new account using copied mail name
        driver.get("http://localhost:9090/auth/register");

        driver.findElement(By.xpath("//input[@placeholder='Email']")).sendKeys(randMail);
        driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("test123#");
        driver.findElement(By.xpath("//input[@placeholder='Repeat-Password']")).sendKeys("test123#");
        driver.findElement(By.xpath("//input[@placeholder='First Name']")).sendKeys("selenium");
        driver.findElement(By.xpath("//input[@placeholder='Last Name']")).sendKeys("selenium");
        driver.findElement(By.xpath("//button[contains(.,'Register')]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//button[contains(.,'Ok')]")).click();

        //verify mail address
        driver.get("https://temp-mail.org/en/");
        Thread.sleep(500);
        driver.findElement(By.xpath("//a[contains(.,'mailer@softwarecartoon.com')]")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//a[contains(.,'Verify account!')]")).click();

        //login on newly registered account
        Thread.sleep(1000);
        String childWindow = driver.getWindowHandle();
        driver.switchTo().window(childWindow);

        driver.get("http://localhost:9090/auth/login");

        WebElement email = driver.findElement(By.xpath("//input[@type='text']"));
        email.sendKeys(randMail);
        WebElement password = driver.findElement(By.xpath("//input[@type='password']"));
        password.sendKeys("test123#");
        password.sendKeys(Keys.ENTER);


    }

    @AfterClass
    public static void afterSuite(){
        driver.quit();
    }
}
