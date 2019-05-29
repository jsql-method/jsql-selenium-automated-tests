package PluginTests;

import com.codeborne.selenide.Configuration;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;


import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static junit.framework.TestCase.fail;

public class FEPluginsTestJUnit {


    @BeforeClass
    public static void setUp() {
        Configuration.browser = "firefox";
        Configuration.timeout = 15000;
        Configuration.headless = false;
    }

    @Test
    public void Vue() throws InterruptedException {
        open("https://test-vue.jsql.it");
        maximalizeWindow();
        waitTime();
        int tableSize = $$(By.xpath("/html/body/div/table/tbody/tr")).size();
        int successElements = $$(By.xpath("(//td[@class='success'])")).size();
        if (tableSize == successElements) {
            System.out.println("Vue : Success");
        } else {
            System.out.println("Vue : Fail");
            fail();
        }
    }

    @Test
    public void React() throws InterruptedException {
        open("https://test-react.jsql.it");
        maximalizeWindow();
        waitTime();
        int tableSize2 = $$(By.xpath("/html/body/div/table/tbody/tr")).size();
        int successElements2 = $$(By.xpath("(//td[@class='success'])")).size();
        if (tableSize2 == successElements2) {
            System.out.println("React : Success");
        } else {
            System.out.println("React : Fail");
            fail();
        }
    }

    @Test
    public void Angular1() throws InterruptedException {
        open("https://test-angular1.jsql.it");
        maximalizeWindow();
        waitTime();
        int tableSize3 = $$(By.xpath("/html/body/div/div/div/table/tbody/tr")).size();
        int successElements3 = $$(By.xpath("(//td[@class='success'])")).size();
        if (tableSize3 == successElements3) {
            System.out.println("Angular 1 : Success");
        } else {
            System.out.println("Angular 1 : Fail");
            fail();
        }
    }

    @Test
    public void Angular7() throws InterruptedException {
        open("https://test-angular7.jsql.it");
        maximalizeWindow();
        waitTime();
        String resultText = $x("//b").getText();
        if (resultText.equals("Result test: error")) {
            System.out.println("Angular 7 : Fail");
            fail();
        } else {
            System.out.println("Angular 7 : Success");
        }
    }

    @Test
    public void JS() throws InterruptedException {
        open("https://test-js.jsql.it/");
        maximalizeWindow();
        waitTime();
        int tableSize4 = $$(By.xpath("/html/body/table/tbody/tr")).size();
        int successElements4 = $$(By.xpath("(//td[@class='success'])")).size();
        if (tableSize4 == successElements4) {
            System.out.println("JS : Success");
        } else {
            System.out.println("JS : Fail");
            fail();
        }
    }

    @Test
    public void JQuery() throws InterruptedException {
        open("https://test-js.jsql.it/");
        maximalizeWindow();
        waitTime();
        int tableSize5 = $$(By.xpath("/html/body/table/tbody/tr")).size();
        int successElements5 = $$(By.xpath("(//td[@class='success'])")).size();
        if (tableSize5 == successElements5) {
            System.out.println("JQuery : Success");
        } else {
            System.out.println("JQuery : Fail");
            fail();
        }
    }

    public static void waitTime() throws InterruptedException {
        Thread.sleep(3000);
    }

    public static void maximalizeWindow() {
        getWebDriver().manage().window().maximize();
    }
}
