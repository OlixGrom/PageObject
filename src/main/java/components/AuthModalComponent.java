package components;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AuthModalComponent extends AbsBaseComponent {
    private final static Logger logger = LogManager.getLogger(AuthModalComponent.class);
    private final String prefixLocator = "//div[.";
    private final String emailLocator = "/input[@name='email']";
    private final String passwordLocator = "/input[@type='password']";
    private final String buttonEnterLocator = "//button[./*[text()='Войти']]";

    public AuthModalComponent(WebDriver driver) {
        super(driver);
    }

    public AuthModalComponent clickEmail() {
        logger.info("Click in email ");
        $(By.xpath(prefixLocator + emailLocator + "]")).click();
        return this;
    }

    public AuthModalComponent clickPassword() {
        logger.info("Click in password");
        $(By.xpath(prefixLocator + passwordLocator + "]")).click();
        return this;
    }

    public AuthModalComponent setEmail() {
        logger.info("Enter data: email");
        $(By.xpath("/" + emailLocator)).sendKeys(System.getProperty("email", "TestOlix@yandex.ru"));
        return this;
    }

    public AuthModalComponent setPassword() {
        logger.info("Enter data: password");
        $(By.xpath("/" + passwordLocator)).sendKeys(System.getProperty("pass", "pHjLd?PO1"));
        return this;
    }

    public AuthModalComponent clickEnter() {
        logger.info("Click in button enter");
        $(By.xpath(buttonEnterLocator)).click();
        return this;
    }
}
