package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import waiters.Waiters;

import javax.swing.*;

public abstract class AbsPageObject {
    protected WebDriver driver;
    protected Actions actions;
    protected Waiters waiters;

    public AbsPageObject(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(driver);
        this.waiters = new Waiters(driver);
    }
}
