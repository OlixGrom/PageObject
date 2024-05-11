package driver.impl;

import exceptions.DriverNotSupportedException;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;

public interface IDriver {
    WebDriver newDriver(MutableCapabilities capabilities) throws DriverNotSupportedException;

    default void downloadLocalWebDriver(DriverManagerType driverType) throws DriverNotSupportedException {
        WebDriverManager.getInstance(driverType).setup();
    }
}
