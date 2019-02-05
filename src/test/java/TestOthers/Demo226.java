package TestOthers;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Demo226 {
    public static WebDriver driver;
    WebDriverWait wait = new WebDriverWait(driver, 10000);

    @BeforeClass
    public static void setUp() {
        //getDriver selenium

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        driver = new ChromeDriver(chromeOptions);
//        driver = new ChromeDriver();
//        driver.manage().window().maximize();

        //login into page
        driver.get("https://demo226-pl.yourtechnicaldomain.com/panel/main.php");
        driver.findElement(By.id("input_login")).sendKeys("dawsen413");
        driver.findElement(By.name("panel_password")).sendKeys("1bdffa02");
        driver.findElement(By.className("formbutton")).click();
    }

    @Test
    public void AddNewApplication() throws InterruptedException {
        //zmienne do formularza
        String name = "testowa nazwa";
        String description = "testy opis";
        String mainAddress = "testowy_adres.pl";
        String redirectAddress = "testowe_przekierowanie.pl";
        String clientId = "identyfikator";
        String clientSecret = "sekretnyKlucz";


        //przejdz do dodawania aplikacje
        Thread.sleep(500);
        driver.get("https://demo226-pl.yourtechnicaldomain.com/panel/action/applications");
        driver.findElement(By.id("add_application")).click();
        Thread.sleep(500);


        //nazwa
        driver.findElement(By.id("fg_name")).sendKeys(name);
        //opis
        driver.findElement(By.id("fg_description")).sendKeys(description);

        //Menu, w którym link do aplikacji będzie widoczny  --> wybrane marketing i integracje
        driver.findElement(By.id("fg_option_menu_marketing")).click();

        //zaznacz wszystkie
        driver.findElement(By.partialLinkText("zaznacz wszystkie")).click();

        //Adres główny aplikacji
        driver.findElement(By.id("fg_indexUrl")).sendKeys(mainAddress);

        //Adres, na który aplikacja zostanie przekierowana po poprawnej autoryzacji
        driver.findElement(By.id("fg_redirectUrl")).sendKeys(redirectAddress);

        //Unikalny identyfikator określony przez developera w kodzie aplikacji, która przekazuje go dalej jako parametr client_id do serwera autoryzacji
        driver.findElement(By.id("fg_clientId")).sendKeys(clientId);

        //Sekretny klucz (unikalny ciąg znaków) określany przez developera w kodzie aplikacji, która przekazuje go dalej jako parametr client_secret do serwera autoryzacji
        driver.findElement(By.id("fg_clientSecret")).sendKeys(clientSecret);

        driver.findElement(By.className("formbutton")).click();

        Thread.sleep(1000);
        driver.findElement(By.className("btn-primary")).click();
    }

    @Test
    public void AddNewCampain() throws InterruptedException {

        //zmienne do formularza
        String campainName = "testowa nazwa";
        String campainDescription = "testowy opis";
        String naglowek = "testowy dodatek 1 ";
        String editionHtml1 = "testowa edycja html ";
        String zamowienie = "testowy dodatek 2";
        String editionJS2 = "testowa edycja JS 2 ";
        String szczegoly = "testowy dodatek 3";
        String editionJS3 = "testowa edycja JS 3";

        //przejdz do strony dodatki i do dodawania kampani
        Thread.sleep(500);
        driver.get("https://demo226-pl.yourtechnicaldomain.com/panel/snippets.php");
        wait.until(ExpectedConditions.elementToBeClickable(By.className("glyphicon-plus")));
        driver.findElement(By.className("glyphicon-plus")).click();

        //Nazwa w panelu
        driver.findElement(By.id("fg_name")).sendKeys(campainName);

        //Opis w panelu
        driver.findElement(By.id("fg_description")).sendKeys(campainDescription);

        //zaznacz wszystkie
        driver.findElement(By.partialLinkText("zaznacz wszystkie")).click();

        //zatwierdz i powrot do kampani
        driver.findElement(By.className("formbutton")).click();
        Thread.sleep(1000);
        driver.findElement(By.partialLinkText("Powrót do listy kampanii")).click();
        Thread.sleep(500);


        //dodaj nowy dodatek  1
        driver.findElement(By.xpath("((//td[contains(.,'" + campainName + "')])[2]/following-sibling::*)[contains(.,'edytuj')][2]")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.className("glyphicon-plus")));
        driver.findElement(By.className("glyphicon-plus")).click();

        //tytuł dodatku
        driver.findElement(By.id("fg_title")).sendKeys(naglowek);

        //aktywny na tak
        driver.findElement(By.id("fg_label_active0")).click();

        //typ dodatku
        driver.findElement(By.id("fg_label_link_type0")).click();

        //edycja HTML
        driver.findElement(By.id("tableRowTextEditTabs_container_html_area_0")).sendKeys(editionHtml1);

        //wyświetlaj na podstronach
        driver.findElement(By.id("fg_label_site_select0")).click();

        //zatwierdz i powrot do kampani
        driver.findElement(By.className("formbutton")).click();
        Thread.sleep(1000);
        driver.findElement(By.partialLinkText("Powrót do listy dodatków HTML i JavaScript")).click();
        Thread.sleep(500);



        //dodaj nowy dodatek  2
        wait.until(ExpectedConditions.elementToBeClickable(By.className("glyphicon-plus")));
        driver.findElement(By.className("glyphicon-plus")).click();

        //tytuł dodatku
        driver.findElement(By.id("fg_title")).sendKeys(zamowienie);

        //aktywny na tak
        driver.findElement(By.id("fg_label_active0")).click();

        //typ dodatku
        driver.findElement(By.id("fg_label_link_type1")).click();

        //edycja HTML
        driver.findElement(By.id("tableRowTextEditTabs_container_html_area_0")).sendKeys(editionJS2);

        //wyświetlaj na podstronach
        driver.findElement(By.id("fg_label_site_select1")).click();
        driver.findElement(By.xpath("//label[@class='lbl '][contains(.,'Po złożeniu zamówienia')]")).click();

        //zatwierdz i powrot do kampani
        driver.findElement(By.className("formbutton")).click();
        Thread.sleep(1000);
        driver.findElement(By.partialLinkText("Powrót do listy dodatków HTML i JavaScript")).click();
        Thread.sleep(500);



        //dodaj nowy dodatek  3
        wait.until(ExpectedConditions.elementToBeClickable(By.className("glyphicon-plus")));
        driver.findElement(By.className("glyphicon-plus")).click();

        //tytuł dodatku
        driver.findElement(By.id("fg_title")).sendKeys(szczegoly);

        //aktywny na tak
        driver.findElement(By.id("fg_label_active0")).click();

        //typ dodatku
        driver.findElement(By.id("fg_label_link_type1")).click();

        //edycja HTML
        driver.findElement(By.id("tableRowTextEditTabs_container_html_area_0")).sendKeys(editionJS3);

        //wyświetlaj na podstronach
        driver.findElement(By.id("fg_label_site_select1")).click();
        driver.findElement(By.xpath("//label[@class='lbl '][contains(.,'Karta towaru')]")).click();

        //zatwierdz i powrot do kampani
        driver.findElement(By.className("formbutton")).click();
        Thread.sleep(1000);
        driver.findElement(By.partialLinkText("Powrót do listy kampanii")).click();
        Thread.sleep(500);

    }

    @AfterClass
    public static void afterSuite() {
        driver.quit();
    }

}
