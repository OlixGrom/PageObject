package components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageObject.AbsPageObject;

import java.util.List;

public abstract class AbsBaseComponent extends AbsPageObject{
    public AbsBaseComponent(WebDriver driver) {
        super(driver);
    }

    public WebElement $(By locator){
        return driver.findElement(locator);
    }
    public List<WebElement> $$(By locator){
        return driver.findElements(locator);
    }
}
