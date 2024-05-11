package driver.impl;

import exceptions.DriverNotSupportedException;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeWebDriver implements IDriver {
    @Override
    public WebDriver newDriver(MutableCapabilities capabilities) throws DriverNotSupportedException {
        downloadLocalWebDriver(DriverManagerType.CHROME);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.merge(capabilities);
        return new ChromeDriver(chromeOptions);
    }
}
