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
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class WebDriverFactory {

    private final static String remoteGridURL = System.getProperty("remote.url", "http://193.104.57.173/wd/hub");
    private final static Logger logger = LogManager.getLogger(WebDriverFactory.class);

    public static WebDriver create(String webDriverName, MutableCapabilities capabilities) throws DriverNotSupportedException, MalformedURLException {
        logger.info("Start browser " + webDriverName);
        CheckEnum checkEnum = new CheckEnum();

        if (!checkEnum.checkValueInEnum(BrowserTypeData.class, webDriverName)) {
            throw new DriverNotSupportedException(webDriverName);
        }

        switch (BrowserTypeData.valueOf(webDriverName.toUpperCase(Locale.ROOT))) {
            case CHROME: {
                return new ChromeWebDriver().newDriver(capabilities);
            }
            case FIREFOX: {
                return new FireFoxWebDriver().newDriver(capabilities);
            }
            case REMOTE: {
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setCapability(CapabilityType.BROWSER_NAME, "chrome");
                chromeOptions.setCapability(CapabilityType.BROWSER_VERSION, "124.0");
                Map<String, Object> selenoidOptions = new HashMap<>();
                selenoidOptions.put("enableVNC", true);
                selenoidOptions.put("sessionTimeout", "15m");
                chromeOptions.setCapability("selenoid:options", selenoidOptions);
                return new RemoteWebDriver(new URL(remoteGridURL), chromeOptions);
            }
            default:
                throw new DriverNotSupportedException(webDriverName);
        }
    }
}
