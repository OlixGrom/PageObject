package helpers;

import data.BrowserTypeData;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.Locale;

public class OptionsBrowser {

    private MutableCapabilities capabilities;

    public OptionsBrowser(String browserType){
        switch (BrowserTypeData.valueOf(browserType.toUpperCase(Locale.ROOT))) {
            case CHROME: {
                this.capabilities = getChromeOptions();
                break;
            }
            case FIREFOX: {
                this.capabilities = getFirefoxOptions();
                break;
            }
            default:
                this.capabilities = getChromeOptions();
                break;
        }
    }

    public MutableCapabilities getOptions(){
        return this.capabilities;
    }

    private MutableCapabilities getChromeOptions(){
        MutableCapabilities capabilities = new MutableCapabilities();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--no-first-run");
        chromeOptions.addArguments("--homepage=about:blank");
        chromeOptions.addArguments("--ignore-certificate-errors");
        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        return capabilities;
    }

    private MutableCapabilities getFirefoxOptions(){
        MutableCapabilities capabilities = new MutableCapabilities();
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addArguments("--start-maximized");
        firefoxOptions.addArguments("--no-sandbox");
        firefoxOptions.addArguments("--no-first-run");
        firefoxOptions.addArguments("--homepage=about:blank");
        firefoxOptions.addArguments("--ignore-certificate-errors");
        capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, firefoxOptions);
        return capabilities;
    }
}
