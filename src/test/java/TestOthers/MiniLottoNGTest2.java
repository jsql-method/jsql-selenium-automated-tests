package TestOthers;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MiniLottoNGTest2 {

    public static WebDriver driver;
    public String login = "dsenko";
    public String password = "Jsql1234";
    public int num1 = 4;
    public int num2 = 24;
    public int num3 = 17;
    public int num4 = 34;
    public int num5 = 21;

    public int num6 = 8;

    public int numSecondary1 = 2;
    public int numSecondary2 = 6;

    public static void clickWhenReady(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }

    public static WebElement getWhenVisible(By locator) {
        WebElement element = null;
        WebDriverWait wait = new WebDriverWait(driver, 20);
        element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return element;
    }

    public static void elementNotDisplayed(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    @BeforeMethod
    public static void setUp() {

//        geoEnabled.setPreference("geo.wifi.uri", "C:/Users/CP24/Desktop/projekt/jsql-selenium-automated-tests/geoLocation.json");

        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("geo.enabled", true);
        profile.setPreference("geo.provider.use_corelocation", true);
        profile.setPreference("geo.prompt.testing", true);
        profile.setPreference("geo.prompt.testing.allow", true);
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setProfile(profile);
//        firefoxOptions.setHeadless(true);
        driver = new FirefoxDriver(firefoxOptions);
        driver.manage().window().maximize();

    }

    @Test
    public void MiniLotto() throws InterruptedException {

        //go to lotto website
        driver.get("https://gry.lotto.pl/pl/home.html");
        Thread.sleep(500);

        //pop ups
        clickWhenReady(By.xpath("(//div[@class='btn btn-primary'][contains(.,'akceptuję')])[2]"));
        elementNotDisplayed(By.xpath("(//div[@class='btn btn-primary'][contains(.,'akceptuję')])[2]"));
        clickWhenReady(By.xpath("(//button[@class='btn btn-primary'][contains(.,'Potwierdź')])[2]"));
        elementNotDisplayed(By.xpath("(//button[@class='btn btn-primary'][contains(.,'Potwierdź')])[2]"));
        Thread.sleep(500);


        //login
        clickWhenReady(By.xpath("//a[@class='btn btn-login btn-account-header br-0'][contains(.,'Zaloguj się')]"));

        getWhenVisible(By.xpath("//input[@name='loginModal-username']")).sendKeys(login);
        WebElement passwordInput = driver.findElement(By.xpath("//input[@type='password']"));
        passwordInput.sendKeys(password);

        passwordInput.sendKeys(Keys.ENTER);
        while (getWhenVisible(By.xpath("(//div[contains(.,'Serwer tymczasowo niedostępny. Spróbuj ponownie później.')])[14]")).isDisplayed()) {
            Thread.sleep(10000);
            passwordInput.sendKeys(Keys.ENTER);
        }

        Thread.sleep(1000);
        getWhenVisible(By.xpath("/html/body/div[3]/div"));
        clickWhenReady(By.xpath("(//button[@class='btn btn-primary'][contains(.,'Zamknij')])[2]"));
        elementNotDisplayed(By.xpath("(//button[@class='btn btn-primary'][contains(.,'Zamknij')])[2]"));

        //go to mini lotto
        Thread.sleep(1000);
        clickWhenReady(By.xpath("//a[contains(.,'Gry LOTTO')]"));
        Thread.sleep(500);
        clickWhenReady(By.xpath("/html/body/div[2]/div[2]/div[4]/div/div/div/div/nav/div[2]/ul/li[3]/div/ul/li[3]/a"));

        //fill up form (random numbers OFF, only one coupon)
        clickWhenReady(By.xpath("//label[contains(@data-switch-on,'TAK')]"));
        clickWhenReady(By.xpath("(//span[contains(.,'zakład')])[4]"));
        Thread.sleep(800);
        clickWhenReady(By.xpath("(//span[contains(.,'zakład')])[3]"));
        Thread.sleep(800);
        clickWhenReady(By.xpath("(//span[contains(.,'zakład')])[2]"));

        //input numbers
        getWhenVisible(By.xpath("//input[@name='boards[0][primarySelections][0]']")).sendKeys(String.valueOf(num1));
        getWhenVisible(By.xpath("//input[@name='boards[0][primarySelections][1]']")).sendKeys(String.valueOf(num2));
        getWhenVisible(By.xpath("//input[@name='boards[0][primarySelections][2]']")).sendKeys(String.valueOf(num3));
        getWhenVisible(By.xpath("//input[@name='boards[0][primarySelections][3]']")).sendKeys(String.valueOf(num4));
        getWhenVisible(By.xpath("//input[@name='boards[0][primarySelections][4]']")).sendKeys(String.valueOf(num5));

        //confirm
        Thread.sleep(500);
        clickWhenReady(By.xpath("//button[@class='btn btn-primary'][contains(.,'POTWIERDŹ I ZAPŁAĆ')]"));
        elementNotDisplayed(By.xpath("//button[@class='btn btn-primary'][contains(.,'POTWIERDŹ I ZAPŁAĆ')]"));
        System.out.println("check :D");
//        clickWhenReady(By.xpath("//a[@class='btn btn-primary confirmationModal-confirmLink'][contains(.,'POTWIERDZAM I PŁACĘ')]"));
//        elementNotDisplayed(By.xpath("//a[@class='btn btn-primary confirmationModal-confirmLink'][contains(.,'POTWIERDZAM I PŁACĘ')]"));

        //get coupon number and chosen numbers
        Thread.sleep(500);
        //getting text like:
        //Numer kuponu:
        //I600000-10153-1294526-1-10
        WebElement couponNumberElement = getWhenVisible(By.xpath("//*[@id=\"confirmationItem1\"]/div/div/div[2]/div[1]"));
        String couponNumber = couponNumberElement.getText();
        System.out.println(couponNumber);

        //getting text like:
        //5, 11, 22, 35, 41
        WebElement chosenNumbersElement1 = getWhenVisible(By.xpath("//*[@id=\"confirmationItem1\"]/div/div/div[1]/div/span[2]/i"));
        WebElement chosenNumbersElement2 = getWhenVisible(By.xpath("//*[@id=\"confirmationItem1\"]/div/div/div[1]/div/span[3]/i"));
        WebElement chosenNumbersElement3 = getWhenVisible(By.xpath("//*[@id=\"confirmationItem1\"]/div/div/div[1]/div/span[4]/i"));
        WebElement chosenNumbersElement4 = getWhenVisible(By.xpath("//*[@id=\"confirmationItem1\"]/div/div/div[1]/div/span[5]/i"));
        WebElement chosenNumbersElement5 = getWhenVisible(By.xpath("//*[@id=\"confirmationItem1\"]/div/div/div[1]/div/span[6]/i"));
        String chosenNumber1 = chosenNumbersElement1.getText();
        String chosenNumber2 = chosenNumbersElement2.getText();
        String chosenNumber3 = chosenNumbersElement3.getText();
        String chosenNumber4 = chosenNumbersElement4.getText();
        String chosenNumber5 = chosenNumbersElement5.getText();
        System.out.println(chosenNumber1 + ", " + chosenNumber2 + ", " + chosenNumber3 + ", " + chosenNumber4 + ", " + chosenNumber5);

    }

//    @Test
//    public void Lotto() throws InterruptedException {
//
//        //go to lotto webside
//        driver.get("https://gry.lotto.pl/pl/home.html");
//        Thread.sleep(500);
//
//        //pop ups
//        clickWhenReady(By.xpath("(//div[@class='btn btn-primary'][contains(.,'akceptuję')])[2]"));
//        elementNotDisplayed(By.xpath("(//div[@class='btn btn-primary'][contains(.,'akceptuję')])[2]"));
//        clickWhenReady(By.xpath("(//button[@class='btn btn-primary'][contains(.,'Potwierdź')])[2]"));
//        elementNotDisplayed(By.xpath("(//button[@class='btn btn-primary'][contains(.,'Potwierdź')])[2]"));
//        Thread.sleep(1000);
//
//
//        //login
//        clickWhenReady(By.xpath("//a[@class='btn btn-login btn-account-header br-0'][contains(.,'Zaloguj się')]"));
//
//        getWhenVisible(By.xpath("//input[@name='loginModal-username']")).sendKeys(login);
//        WebElement passwordInput = driver.findElement(By.xpath("//input[@type='password']"));
//        passwordInput.sendKeys(password);
//        passwordInput.sendKeys(Keys.ENTER);
//
//        Thread.sleep(1000);
//        clickWhenReady(By.xpath("(//button[@class='btn btn-primary'][contains(.,'Zamknij')])[2]"));
//        elementNotDisplayed(By.xpath("(//button[@class='btn btn-primary'][contains(.,'Zamknij')])[2]"));
//
//        //go to lotto
//        Thread.sleep(1000);
//        clickWhenReady(By.xpath("//a[contains(.,'Gry LOTTO')]"));
//        Thread.sleep(500);
//        clickWhenReady(By.xpath("/html/body/div[2]/div[2]/div[4]/div/div/div/div/nav/div[2]/ul/li[3]/div/ul/li[1]/a"));
//
//        //fill up form (random numbers OFF, only one coupon, 'Graj z plusem' OFF)
//        clickWhenReady(By.xpath("//label[contains(@data-switch-on,'TAK')]"));
//        clickWhenReady(By.xpath("//label[@for='plusSwitch']"));
//        clickWhenReady(By.xpath("(//span[contains(.,'zakład')])[3]"));
//        Thread.sleep(1000);
//        clickWhenReady(By.xpath("(//span[contains(.,'zakład')])[2]"));
//
//        //input numbers
//        getWhenVisible(By.xpath("//input[@name='boards[0][primarySelections][0]']")).sendKeys(String.valueOf(num1));
//        getWhenVisible(By.xpath("//input[@name='boards[0][primarySelections][1]']")).sendKeys(String.valueOf(num2));
//        getWhenVisible(By.xpath("//input[@name='boards[0][primarySelections][2]']")).sendKeys(String.valueOf(num3));
//        getWhenVisible(By.xpath("//input[@name='boards[0][primarySelections][3]']")).sendKeys(String.valueOf(num4));
//        getWhenVisible(By.xpath("//input[@name='boards[0][primarySelections][4]']")).sendKeys(String.valueOf(num5));
//        getWhenVisible(By.xpath("//input[@name='boards[0][primarySelections][5]']")).sendKeys(String.valueOf(num6));
//
//        //confirm
//        clickWhenReady(By.xpath("//button[@class='btn btn-primary'][contains(.,'POTWIERDŹ I ZAPŁAĆ')]"));
//        elementNotDisplayed(By.xpath("//button[@class='btn btn-primary'][contains(.,'POTWIERDŹ I ZAPŁAĆ')]"));
//        System.out.println("check :D");
////        clickWhenReady(By.xpath("//a[@class='btn btn-primary confirmationModal-confirmLink'][contains(.,'POTWIERDZAM I PŁACĘ')]"));
////        elementNotDisplayed(By.xpath("//a[@class='btn btn-primary confirmationModal-confirmLink'][contains(.,'POTWIERDZAM I PŁACĘ')]"));
//
//        //get coupon number and chosen numbers
//        Thread.sleep(500);
//        //getting text like:
//        //Numer kuponu:
//        //I600000-10153-1294526-1-10
//        WebElement couponNumberElement = getWhenVisible(By.xpath("//*[@id=\"confirmationItem1\"]/div/div/div[2]/div[1]"));
//        String couponNumber = couponNumberElement.getText();
//        System.out.println(couponNumber);
//
//        //getting text like:
//        //5, 11, 22, 35, 42, 7
//        WebElement chosenNumbersElement1 = getWhenVisible(By.xpath("//*[@id=\"confirmationItem1\"]/div/div/div[1]/div/span[2]/i"));
//        WebElement chosenNumbersElement2 = getWhenVisible(By.xpath("//*[@id=\"confirmationItem1\"]/div/div/div[1]/div/span[3]/i"));
//        WebElement chosenNumbersElement3 = getWhenVisible(By.xpath("//*[@id=\"confirmationItem1\"]/div/div/div[1]/div/span[4]/i"));
//        WebElement chosenNumbersElement4 = getWhenVisible(By.xpath("//*[@id=\"confirmationItem1\"]/div/div/div[1]/div/span[5]/i"));
//        WebElement chosenNumbersElement5 = getWhenVisible(By.xpath("//*[@id=\"confirmationItem1\"]/div/div/div[1]/div/span[6]/i"));
//        WebElement chosenNumbersElement6 = getWhenVisible(By.xpath("//*[@id=\"confirmationItem1\"]/div/div/div[1]/div/span[7]/i"));
//        String chosenNumber1 = chosenNumbersElement1.getText();
//        String chosenNumber2 = chosenNumbersElement2.getText();
//        String chosenNumber3 = chosenNumbersElement3.getText();
//        String chosenNumber4 = chosenNumbersElement4.getText();
//        String chosenNumber5 = chosenNumbersElement5.getText();
//        String chosenNumber6 = chosenNumbersElement6.getText();
//        System.out.println(chosenNumber1 + ", " + chosenNumber2 + ", " + chosenNumber3 + ", " + chosenNumber4 + ", " + chosenNumber5 + ", " + chosenNumber6);
//
//    }
//
//    @Test
//    public void EuroJackpot() throws InterruptedException {
//
//        //go to lotto webside
//        driver.get("https://gry.lotto.pl/pl/home.html");
//        Thread.sleep(500);
//
//        //pop ups
//        clickWhenReady(By.xpath("(//div[@class='btn btn-primary'][contains(.,'akceptuję')])[2]"));
//        elementNotDisplayed(By.xpath("(//div[@class='btn btn-primary'][contains(.,'akceptuję')])[2]"));
//        clickWhenReady(By.xpath("(//button[@class='btn btn-primary'][contains(.,'Potwierdź')])[2]"));
//        elementNotDisplayed(By.xpath("(//button[@class='btn btn-primary'][contains(.,'Potwierdź')])[2]"));
//        Thread.sleep(1000);
//
//
//        //login
//        clickWhenReady(By.xpath("//a[@class='btn btn-login btn-account-header br-0'][contains(.,'Zaloguj się')]"));
//
//        getWhenVisible(By.xpath("//input[@name='loginModal-username']")).sendKeys(login);
//        WebElement passwordInput = driver.findElement(By.xpath("//input[@type='password']"));
//        passwordInput.sendKeys(password);
//        passwordInput.sendKeys(Keys.ENTER);
//
//        Thread.sleep(1000);
//        clickWhenReady(By.xpath("(//button[@class='btn btn-primary'][contains(.,'Zamknij')])[2]"));
//        elementNotDisplayed(By.xpath("(//button[@class='btn btn-primary'][contains(.,'Zamknij')])[2]"));
//
//        //go to lotto
//        Thread.sleep(1000);
//        clickWhenReady(By.xpath("//a[contains(.,'Gry LOTTO')]"));
//        Thread.sleep(500);
//        clickWhenReady(By.xpath("/html/body/div[2]/div[2]/div[4]/div/div/div/div/nav/div[2]/ul/li[3]/div/ul/li[2]/a"));
//
//        //fill up form (random numbers OFF, only one coupon)
//        clickWhenReady(By.xpath("//label[contains(@data-switch-on,'TAK')]"));
//        clickWhenReady(By.xpath("(//span[contains(.,'zakład')])[2]"));
//        Thread.sleep(500);
//
//        //input numbers
//        getWhenVisible(By.xpath("//input[@name='boards[0][primarySelections][0]']")).sendKeys(String.valueOf(num1));
//        getWhenVisible(By.xpath("//input[@name='boards[0][primarySelections][1]']")).sendKeys(String.valueOf(num2));
//        getWhenVisible(By.xpath("//input[@name='boards[0][primarySelections][2]']")).sendKeys(String.valueOf(num3));
//        getWhenVisible(By.xpath("//input[@name='boards[0][primarySelections][3]']")).sendKeys(String.valueOf(num4));
//        getWhenVisible(By.xpath("//input[@name='boards[0][primarySelections][4]']")).sendKeys(String.valueOf(num5));
//        getWhenVisible(By.xpath("//input[@name='boards[0][secondarySelections][0]']")).sendKeys(String.valueOf(numSecondary1));
//        getWhenVisible(By.xpath("//input[@name='boards[0][secondarySelections][1]']")).sendKeys(String.valueOf(numSecondary2));
//
//        //confirm
//        clickWhenReady(By.xpath("//button[@class='btn btn-primary'][contains(.,'POTWIERDŹ I ZAPŁAĆ')]"));
//        elementNotDisplayed(By.xpath("//button[@class='btn btn-primary'][contains(.,'POTWIERDŹ I ZAPŁAĆ')]"));
//        System.out.println("check :D");
////        clickWhenReady(By.xpath("//a[@class='btn btn-primary confirmationModal-confirmLink'][contains(.,'POTWIERDZAM I PŁACĘ')]"));
////        elementNotDisplayed(By.xpath("//a[@class='btn btn-primary confirmationModal-confirmLink'][contains(.,'POTWIERDZAM I PŁACĘ')]"));
//
//        //get coupon number and chosen numbers
//        Thread.sleep(500);
//        //getting text like:
//        //Numer kuponu:
//        //I600000-10153-1294526-1-10
//        WebElement couponNumberElement = getWhenVisible(By.xpath("//*[@id=\"confirmationItem1\"]/div/div/div[2]/div[1]"));
//        String couponNumber = couponNumberElement.getText();
//        System.out.println(couponNumber);
//
//        //getting text like:
//        //5, 11, 22, 35, 42, 7, 8
//        WebElement chosenNumbersElement1 = getWhenVisible(By.xpath("//*[@id=\"confirmationItem1\"]/div/div/div[1]/div/span[2]/i"));
//        WebElement chosenNumbersElement2 = getWhenVisible(By.xpath("//*[@id=\"confirmationItem1\"]/div/div/div[1]/div/span[3]/i"));
//        WebElement chosenNumbersElement3 = getWhenVisible(By.xpath("//*[@id=\"confirmationItem1\"]/div/div/div[1]/div/span[4]/i"));
//        WebElement chosenNumbersElement4 = getWhenVisible(By.xpath("//*[@id=\"confirmationItem1\"]/div/div/div[1]/div/span[5]/i"));
//        WebElement chosenNumbersElement5 = getWhenVisible(By.xpath("//*[@id=\"confirmationItem1\"]/div/div/div[1]/div/span[6]/i"));
//        WebElement chosenNumbersSecondary1 = getWhenVisible(By.xpath("//*[@id=\"confirmationItem1\"]/div/div/div[1]/div/span[7]/i"));
//        WebElement chosenNumbersSecondary2 = getWhenVisible(By.xpath("//*[@id=\"confirmationItem1\"]/div/div/div[1]/div/span[8]/i"));
//        String chosenNumber1 = chosenNumbersElement1.getText();
//        String chosenNumber2 = chosenNumbersElement2.getText();
//        String chosenNumber3 = chosenNumbersElement3.getText();
//        String chosenNumber4 = chosenNumbersElement4.getText();
//        String chosenNumber5 = chosenNumbersElement5.getText();
//        String chosenNumberSecondary1 = chosenNumbersSecondary1.getText();
//        String chosenNumberSecondary2 = chosenNumbersSecondary2.getText();
//        System.out.println(chosenNumber1 + ", " + chosenNumber2 + ", " + chosenNumber3 + ", " + chosenNumber4 + ", " + chosenNumber5 + ", " + chosenNumberSecondary1 + ", " + chosenNumberSecondary2);
//
//    }

    @AfterMethod
    public static void afterSuite() {
        driver.quit();
    }
}

