package TestOthers;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AribaUploadCatalog {

    public static WebDriver driver;

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

    String filePath = "C:\\Users\\CP24\\Desktop\\example_cif30.cif";
    String catalogName = "katalog 2";

    @BeforeClass
    public static void setUp() {

//        FirefoxProfile profile = new FirefoxProfile();
//        profile.setPreference("geo.enabled", true);
//        profile.setPreference("geo.provider.use_corelocation", true);
//        profile.setPreference("geo.prompt.testing", true);
//        profile.setPreference("geo.prompt.testing.allow", true);
//        FirefoxOptions firefoxOptions = new FirefoxOptions();
//        firefoxOptions.setProfile(profile);
////        firefoxOptions.setHeadless(true);
//        driver = new FirefoxDriver(firefoxOptions);

        driver = new ChromeDriver();
        driver.manage().window().maximize();

    }

    @Test
    public void UploadCatalog() throws InterruptedException {

        //zaloguj się
        driver.get("https://service.ariba.com/Supplier.aw");
        getWhenVisible(By.xpath("//input[@name='UserName']")).sendKeys("czestochowa@nowaelektro.pl");
        getWhenVisible(By.xpath("//input[@name='Password']")).sendKeys("czestochowa128239");
        getWhenVisible(By.xpath("//input[@name='Password']")).sendKeys(Keys.ENTER);

        //przejdz do dodawania katalogu
        clickWhenReady(By.xpath("//a[contains(.,'Katalogi')]"));
        clickWhenReady(By.xpath("//a[contains(.,'" + catalogName + "')]"));
        clickWhenReady(By.xpath("//a[contains(.,'Zawartość')]"));
        clickWhenReady(By.xpath("//button[@tabindex='0'][contains(@id,'bjc19d')][contains(.,'Wyślij plik katalogu')]"));

        //prześlij plik katalogu
        WebElement uploadFile = getWhenVisible(By.xpath("//input[@name='fileInput']"));
        uploadFile.sendKeys(filePath);
        clickWhenReady(By.xpath("//button[contains(.,'Sprawdź i opublikuj')]"));

    }

    @Test
    public void DownloadCatalog() throws InterruptedException {

        //zaloguj się
        driver.get("https://service.ariba.com/Supplier.aw");
        getWhenVisible(By.xpath("//input[@name='UserName']")).sendKeys("czestochowa@nowaelektro.pl");
        getWhenVisible(By.xpath("//input[@name='Password']")).sendKeys("czestochowa128239");
        getWhenVisible(By.xpath("//input[@name='Password']")).sendKeys(Keys.ENTER);

        //przejdz do dodawania katalogu
        clickWhenReady(By.xpath("//a[contains(.,'Katalogi')]"));
        clickWhenReady(By.xpath("//a[contains(.,'" + catalogName + "')]"));
        clickWhenReady(By.xpath("//a[contains(.,'Zawartość')]"));
        clickWhenReady(By.xpath("//button[@tabindex='0'][contains(@id,'ucdl')][contains(.,'Pobierz plik katalogu')]"));


        //pobierz plik katalogu
        clickWhenReady(By.xpath("//button[contains(.,'Pobierz w formacie cXML')]"));

    }

    @AfterClass
    public static void afterSuite() {
        driver.quit();
    }
}

