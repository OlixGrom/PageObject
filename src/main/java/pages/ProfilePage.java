package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProfilePage extends AbsBasePage{
    private final static String PROFILE_URL = System.getProperty("profile.url","/lk/biography/personal/");
    public ProfilePage(WebDriver driver) {
        super(driver);
    }

    public void openProfile() {
        waiters.waitForElementVisible(By.cssSelector("section div.sc-1og4wiw-0-Component:nth-child(2) span"));
        super.open(PROFILE_URL);
    }

}
