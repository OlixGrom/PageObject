package components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainMenuComponent extends AbsBaseComponent{
    public MainMenuComponent(WebDriver driver) {
        super(driver);
    }

    private String buttonEnter = "//button[text()='Войти']";

    public void clickEnter(){
        driver.findElement(By.xpath(buttonEnter)).click();
    }
}
