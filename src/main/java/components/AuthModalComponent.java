package components;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AuthModalComponent extends AbsBaseComponent {
    private final static Logger logger = LogManager.getLogger(AuthModalComponent.class);

    public AuthModalComponent(WebDriver driver) {
        super(driver);
    }

    private final String prefix = "//div[.";
    private final String email = "/input[@name='email']";
    private final String password = "/input[@type='password']";

    private final String buttonEnter = "//button[./*[text()='Войти']]";

    public AuthModalComponent clickEmail() {
        logger.info("Click in email");
        $(By.xpath(prefix + email + "]")).click();
        return this;
    }

    public AuthModalComponent clickPassword() {
        logger.info("Click in password");
        $(By.xpath(prefix + password + "]")).click();
        return this;
    }

    public AuthModalComponent setEmail() {
        logger.info("Enter data: email");
        $(By.xpath("/" + email)).sendKeys(System.getProperty("email"));
        return this;
    }

    public AuthModalComponent setPassword() {
        logger.info("Enter data: password");
        $(By.xpath("/" + password)).sendKeys(System.getProperty("pass"));
        return this;
    }

    public AuthModalComponent clickEnter() {
        logger.info("Click in button enter");
        $(By.xpath(buttonEnter)).click();
        return this;
    }
}
