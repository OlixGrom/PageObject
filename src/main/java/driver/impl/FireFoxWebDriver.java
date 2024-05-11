package driver.impl;

import exceptions.DriverNotSupportedException;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class FireFoxWebDriver implements IDriver {
    @Override
    public WebDriver newDriver(MutableCapabilities capabilities) throws DriverNotSupportedException {
        downloadLocalWebDriver(DriverManagerType.FIREFOX);
        return new FirefoxDriver((FirefoxOptions) capabilities);
    }
}
