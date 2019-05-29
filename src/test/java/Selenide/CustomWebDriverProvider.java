package Selenide;

import com.codeborne.selenide.WebDriverProvider;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

public class CustomWebDriverProvider implements WebDriverProvider {
    @Override
    public WebDriver createDriver(DesiredCapabilities capabilities) {
        FirefoxProfile profile = new FirefoxProfile(new File("/home/test/MozzillaProf/"));
        profile.setAssumeUntrustedCertificateIssuer(false);

        capabilities.setCapability(FirefoxDriver.PROFILE, profile);

        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.merge(capabilities);

        return new FirefoxDriver(firefoxOptions);
    }
}
