package components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainMenuComponent extends AbsBaseComponent {
    private final String buttonEnterLocator = "//button[text()='Войти']";

    public MainMenuComponent(WebDriver driver) {
        super(driver);
    }

    public void clickEnter() {
        $(By.xpath(buttonEnterLocator)).click();
    }
}
