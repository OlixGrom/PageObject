package pages;

import org.openqa.selenium.WebDriver;
import pageObject.AbsPageObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import waiters.Waiters;

import javax.swing.*;

public abstract class AbsBasePage extends AbsPageObject {
    private final static String BASE_URL = System.getProperty("base.url");
    private final static Logger logger = LogManager.getLogger(AbsBasePage.class);
    public AbsBasePage(WebDriver driver) {
        super(driver);
    }

    public void open(String path) {
        logger.info("Open " + BASE_URL + path);
        driver.get(BASE_URL + path);
    }
}
