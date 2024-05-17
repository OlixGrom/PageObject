package waiters;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Waiters {
    private final WebDriver driver;
    private final int waiterTimeOut = Integer.parseInt(System.getProperty("waiter.timeout", "5"));

    public Waiters(WebDriver driver) {
        this.driver = driver;
    }

    public boolean waitForCondition(ExpectedCondition condition) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(waiterTimeOut)).until(condition);
            return true;
        } catch (TimeoutException ignored) {
            return false;
        }
    }

    public boolean waitForElementVisible(By locator) {
        return waitForCondition(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public boolean waitForElementToBeClickable(By locator) {
        return waitForCondition(ExpectedConditions.elementToBeClickable(locator));
    }

    public boolean waitForElementToBeAttributeIsDisabled(By locator) {
        return waitForCondition(ExpectedConditions.attributeToBe(
                locator,
                "disabled",
                "disabled"
        ));
    }

    public boolean waitForElementWithoutAttributeDisabled(By locator) {
        return waitForCondition(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
}
