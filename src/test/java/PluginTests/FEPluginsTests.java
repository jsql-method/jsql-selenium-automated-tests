package PluginTests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class FEPluginsTests {

    public static void main(String[] args) throws InterruptedException {
        Configuration.browser = "firefox";
        Configuration.timeout = 15000;

//        Configuration.headless = true;

        /**      Vue      */
        open("https://test-vue.jsql.it");
        getWebDriver().manage().window().maximize();
        waitTime();
        int tableSize = $$(By.xpath("/html/body/div/table/tbody/tr")).size();
        int successElements = $$(By.xpath("(//td[@class='success'])")).size();
        if (tableSize == successElements) {
            System.out.println("Vue : Success");
        } else {
            System.out.println("Vue : Fail");
        }

        /**      React      */
        open("https://test-react.jsql.it");
        waitTime();
        int tableSize2 = $$(By.xpath("/html/body/div/table/tbody/tr")).size();
        int successElements2 = $$(By.xpath("(//td[@class='success'])")).size();
        if (tableSize2 == successElements2) {
            System.out.println("React : Success");
        } else {
            System.out.println("React : Fail");
        }

        /**      Angular 1      */
        open("https://test-angular1.jsql.it");
        waitTime();
        int tableSize3 = $$(By.xpath("/html/body/div/div/div/table/tbody/tr")).size();
        int successElements3 = $$(By.xpath("(//td[@class='success'])")).size();
        if (tableSize3 == successElements3) {
            System.out.println("Angular 1 : Success");
        } else {
            System.out.println("Angular 1 : Fail");
        }

        /**      Angular 7      */
        open("https://test-angular7.jsql.it");
        waitTime();
        String resultText = $x("//b").getText();
        if (resultText.equals("Result test: error")) {
            System.out.println("Angular 7 : Fail");
        } else {
            System.out.println("Angular 7 : Success");
        }


        /**      JS      */
        open("https://test-js.jsql.it/");
        waitTime();
        int tableSize4 = $$(By.xpath("/html/body/table/tbody/tr")).size();
        int successElements4 = $$(By.xpath("(//td[@class='success'])")).size();
        if (tableSize4 == successElements4) {
            System.out.println("JS : Success");
        } else {
            System.out.println("JS : Fail");
        }


        /**      JQuery      */
        open("https://test-js.jsql.it/");
        waitTime();
        int tableSize5 = $$(By.xpath("/html/body/table/tbody/tr")).size();
        int successElements5 = $$(By.xpath("(//td[@class='success'])")).size();
        if (tableSize5 == successElements5) {
            System.out.println("JQuery : Success");
        } else {
            System.out.println("JQuery : Fail");
        }

    }

    private static void waitTime() throws InterruptedException {
        Thread.sleep(3000);
    }
}
