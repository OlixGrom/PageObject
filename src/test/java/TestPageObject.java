import components.AuthModalComponent;
import components.MainMenuComponent;
import components.PersonalBlockComponent;
import data.*;
import driver.WebDriverFactory;
import exceptions.DriverNotSupportedException;
import helpers.OptionsBrowser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import pages.MainPage;
import pages.ProfilePage;

import java.time.LocalDate;

public class TestPageObject {
    private final static Logger logger = LogManager.getLogger(TestPageObject.class);
    private final String fname = "Имя";
    private final String fname_latin = "Name";
    private final String lname = "Фамилия";
    private final String lname_latin = "LastName";
    private final String blog_name = "BlogName";
    //private final String dateBirth = "25.10.1986";
    LocalDate dateBirth = LocalDate.of(1986, 10, 25);
    private final String vkText = "vkText";
    private final String okText = "okText";
    private final String companyName = "Company";
    private final String workName = "QA engineer";
    private final String country = "Россия";
    private final String city = "Омск";

    private WebDriver driver;
    private final String browserType = System.getProperty("browser", "chrome").toUpperCase();

    @BeforeEach
    public void init() throws DriverNotSupportedException {
        this.driver = new WebDriverFactory().create(this.browserType, new OptionsBrowser(this.browserType).getOptions());
        this.driver.manage().window().maximize();
        //this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterEach
    public void close() {
        if (this.driver != null) {
            this.driver.close();
            this.driver.quit();
        }
    }


    @Test
    void checkProfile() throws DriverNotSupportedException, InterruptedException {
        //Открыть https://otus.ru
        new MainPage(driver).open("/");

        //Авторизоваться на сайте
        new MainMenuComponent(driver).clickEnter();

        new AuthModalComponent(driver)
                .clickEmail()
                .setEmail()
                .clickPassword()
                .setPassword()
                .clickEnter();

        //Войти в личный кабинет - персональные данные
        new ProfilePage(driver).openProfile();

        //В разделе "О себе" заполнить все поля "Личные данные" и добавить не менее двух контактов
        //Нажать сохранить

        new PersonalBlockComponent(driver)
                .setFName(fname)
                .setFNameLatin(fname_latin)
                .setLName(lname)
                .setLNameLatin(lname_latin)
                .setBlogName(blog_name)
                .setCalendar(dateBirth)
                .setCountry(CountryData.RUSSIA)
                .setCity(CityOfRussiaData.OMSK.getDescription())
                .setLevellanguage(LanguageLevelData.BEGINNER)
                .setReadyToRelocate(false)
                .setWorkSchedule(WorkScheduleData.FULL)
                .setContactOK(okText)
                .setContactVK(vkText)
                .setGender(GenderData.FEMALE)
                .setCompany(companyName)
                .setWork(workName)
                .clickSave();


        //Закрыть браузер и открыть новый
        close();
        init();

        //Открыть https://otus.ru
        new MainPage(driver).open("/");

        //Авторизоваться на сайте
        new MainMenuComponent(driver)
                .clickEnter();

        new AuthModalComponent(driver)
                .clickEmail()
                .setEmail()
                .clickPassword()
                .setPassword()
                .clickEnter();

        //Войти в личный кабинет
        new ProfilePage(driver).openProfile();

        new PersonalBlockComponent(driver)
                .checkLName(lname)
                .checkLNameLatin(lname_latin)
                .checkFName(fname)
                .checkFNameLatin(fname_latin)
                .checkBlockName(blog_name)
                .checkCalendar(dateBirth)
                .checkCountry(country)
                .checkCity(city)
                .checkLevel(LanguageLevelData.BEGINNER)
                .checkLReadyToRelocate(false)
                .checkWorkSchedule(WorkScheduleData.FULL)
                .checkOk(okText)
                .checkVk(vkText)
                .checkGender(GenderData.FEMALE)
                .checkCompany(companyName)
                .checkWork(workName);
    }
}
