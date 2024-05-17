import com.github.javafaker.Faker;
import components.AuthModalComponent;
import components.MainMenuComponent;
import components.PersonalBlockComponent;
import data.*;
import driver.OptionsBrowser;
import driver.WebDriverFactory;
import exceptions.DriverNotSupportedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import pages.MainPage;
import pages.ProfilePage;

import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

public class TestPageObject {
    private final static Logger logger = LogManager.getLogger(TestPageObject.class);
    private final String fname = "Имя";
    private final String fname_latin = "Name";
    private final String lname = "Фамилия";
    private final String lname_latin = "LastName";
    private final String blog_name = "BlogName";
    private final String vkText = "vkText";
    private final String okText = "okText";
    private final String companyName = "Company";
    private final String workName = "QA engineer";
    private final String country = "Россия";
    private final String city = "Омск";
    private final LocalDate dateBirth = LocalDate.of(1986, 10, 25);
    private final String browserType = System.getProperty("browser", "remote").toUpperCase();
    protected Faker fakerEnglish, fakerRussian;
    private WebDriver driver;

    public static LocalDate convertToLocalDate(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    @BeforeEach
    public void init() throws DriverNotSupportedException, MalformedURLException {
        System.out.println("! browserType " + browserType);
        this.driver = new WebDriverFactory().create(this.browserType, new OptionsBrowser(this.browserType).getOptions());
        this.driver.manage().window().maximize();
    }

    @AfterEach
    public void close() {
        if (this.driver != null) {
            this.driver.close();
            this.driver.quit();
        }
    }

    @Test
    void checkProfile() throws DriverNotSupportedException, InterruptedException, MalformedURLException {
        fakerRussian = new Faker(Locale.forLanguageTag("ru"));
        fakerEnglish = new Faker(Locale.forLanguageTag("en"));
        String fname = fakerRussian.name().firstName();
        String fname_latin = fakerEnglish.name().firstName();
        String lname = fakerRussian.name().lastName();
        String lname_latin = fakerEnglish.name().lastName();
        String blog_name = fakerEnglish.funnyName().name();
        String vkText = "vkText";
        String okText = "okText";
        String companyName = fakerRussian.company().name();
        String workName = fakerRussian.job().title();
        String country = "Россия";
        String city = "Омск";
        LocalDate dateBirth = convertToLocalDate(fakerRussian.date().birthday(20, 30));

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

        //Войти в личный кабинет - персональные данные
        new ProfilePage(driver).openProfile();

        //В разделе "О себе" заполнить все поля "Личные данные" и добавить не менее двух контактов
        //Нажать сохранить
        new PersonalBlockComponent(driver)
                .setFieldValue("fname", fname)
                .setFieldValue("fname_latin", fname_latin)
                .setFieldValue("lname", lname)
                .setFieldValue("lname_latin", lname_latin)
                .setFieldValue("blog_name", blog_name)
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

        //Войти в личный кабинет - персональные данные
        new ProfilePage(driver).openProfile();

        new PersonalBlockComponent(driver)
                .checkFieldValue("lname", lname)
                .checkFieldValue("lname_latin", lname_latin)
                .checkFieldValue("fname", fname)
                .checkFieldValue("fname_latin", fname_latin)
                .checkFieldValue("blog_name", blog_name)
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
