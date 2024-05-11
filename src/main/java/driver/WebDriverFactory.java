package driver;

import data.BrowserTypeData;
import driver.impl.ChromeWebDriver;
import driver.impl.FireFoxWebDriver;
import exceptions.DriverNotSupportedException;
import helpers.CheckEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;

import java.util.Locale;

public class WebDriverFactory {
    private final static Logger logger = LogManager.getLogger(WebDriverFactory.class);
    public static WebDriver create(String webDriverName, MutableCapabilities capabilities) throws DriverNotSupportedException {
        logger.info("Start browser " + webDriverName);
        CheckEnum checkEnum = new CheckEnum();

        if (!checkEnum.checkValueInEnum(BrowserTypeData.class,webDriverName)){
            throw new DriverNotSupportedException(webDriverName);
        }

        switch (BrowserTypeData.valueOf(webDriverName.toUpperCase(Locale.ROOT))) {
            case CHROME: {
                return new ChromeWebDriver().newDriver(capabilities);
            }
            case FIREFOX: {
                return new FireFoxWebDriver().newDriver(capabilities);
            }
            default:
                throw new DriverNotSupportedException(webDriverName);
        }
    }
}
