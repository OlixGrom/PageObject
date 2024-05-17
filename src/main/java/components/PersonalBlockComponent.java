package components;

import data.CountryData;
import data.GenderData;
import data.LanguageLevelData;
import data.WorkScheduleData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonalBlockComponent extends AbsBaseComponent {
    private static final String INPUT_TEMPLATE_NAME = "input[name='%s']";
    private final String phone = "input[class='input input_full'][placeholder='Телефон']";
    private final String vkCTextLocator = "//input[@value='vk']/../../following-sibling::input";
    private final String okCTextLocator = "//input[@value='ok']/../../following-sibling::input";
    private final String buttonSaveSelector = "button[title='Сохранить и продолжить']";
    private final String buttonSelectSelector = "button.js-lk-cv-custom-select-add";
    private final String companySelector = "input[id='id_company']";
    private final String workSelector = "input[id='id_work']";
    private final String levelLocator = "//input[@name='english_level']/..";

    public PersonalBlockComponent(WebDriver driver) {
        super(driver);
    }

    private static String getLocator(String fieldName) {
        return String.format(INPUT_TEMPLATE_NAME, fieldName);
    }

    private void enterText(WebElement webElement, String text) {
        webElement.clear();
        webElement.sendKeys(text);
    }

    public PersonalBlockComponent checkFieldValue(String fieldName, String value) {
        String actual = getTextElementByValue((By.cssSelector(getLocator(fieldName))));
        assertThat(actual)
                .as(String.format("Error. The LName was expected %s, but the actual %s", actual, value))
                .isEqualTo(value);
        return this;
    }

    public PersonalBlockComponent setFieldValue(String fieldName, String value) {
        enterText($(By.cssSelector(getLocator(fieldName))), value);
        return this;
    }

    private String getTextElementByValue(By by) {
        return $(by).getAttribute("value");
    }

    private String getTextElement(By by) {
        return $(by).getText();
    }

    public PersonalBlockComponent clickSave() {
        $(By.cssSelector(buttonSaveSelector)).click();
        return this;
    }

    public PersonalBlockComponent setCalendar(LocalDate dateBirth) {
        $(By.cssSelector(getLocator("date_of_birth"))).click();
        $(By.cssSelector("div[data-view='days picker'] ul li[data-view='month current']")).click();
        $(By.cssSelector("div[data-view='months picker'] ul li[data-view='year current']")).click();
        boolean isFound = false;

        while (!isFound) {
            if (isElementExists(By.xpath("//div[@data-view='years picker']//ul[@data-view='years']//li[text()='" + dateBirth.getYear() + "']"))) {
                isFound = true;
                $(By.xpath("//div[@data-view='years picker']//ul[@data-view='years']//li[text()='" + dateBirth.getYear() + "']")).click();
            } else {
                $(By.cssSelector("div[data-view='years picker'] ul li[data-view='years prev']")).click();
            }
        }

        String mounth = dateBirth.format(DateTimeFormatter.ofPattern("MMM", Locale.ENGLISH));
//        String mounth = MonthData.getDescriptionBySymbol(Integer.toString(dateBirth.getMonthValue()));
        $(By.xpath("//div[@data-view='months picker']//ul[@data-view='months']//li[text()='" + mounth + "']")).click();
        $(By.xpath("//div[@data-view='days picker']//ul[@data-view='days']//li[text()='" + dateBirth.getDayOfMonth() + "']")).click();
        return this;
    }

    public PersonalBlockComponent setCompany(String companyName) {
        enterText($(By.cssSelector(companySelector)), companyName);
        return this;
    }

    public PersonalBlockComponent setWork(String workName) {
        enterText($(By.cssSelector(workSelector)), workName);
        return this;
    }

    public PersonalBlockComponent setCountry(CountryData countryName) {
        //проверяем что не открыто
        String listCountry = "//input[@name='country']/../../div[@class='lk-cv-block__select-options js-custom-select-options-container']";
        if (!isElementExists(By.xpath(listCountry)))
            //waiters.waitForElementToBeClickable(By.xpath("//input[@name='country']/.."));
            $(By.xpath("//input[@name='country']/..")).click();
        waiters.waitForElementVisible(By.cssSelector("button[title='" + countryName.getDescription() + "']"));
        $(By.cssSelector("button[title='" + countryName.getDescription() + "']")).click();
        return this;
    }

    public PersonalBlockComponent setCity(String cityName) {
        waiters.waitForElementToBeAttributeIsDisabled(By.cssSelector("input[name='city']"));
        waiters.waitForElementWithoutAttributeDisabled(By.cssSelector("input[name='city'][disabled]"));
        String listCity = "//input[@name='city']/../../div[@class='lk-cv-block__select-options js-custom-select-options-container']";
        if (!isElementExists(By.xpath(listCity)))
            $(By.xpath("//input[@name='city']/..")).click();
        waiters.waitForElementVisible(By.cssSelector("button[title='" + cityName + "']"));
        $(By.cssSelector("button[title='" + cityName + "']")).click();
        return this;
    }

    public PersonalBlockComponent setLevellanguage(LanguageLevelData languageLevelData) throws InterruptedException {
        String listLevel = "//input[@name='english_level']/../../div[class='lk-cv-block__select-options js-custom-select-options-container']";
        if (!isElementExists(By.xpath(listLevel))) {
            waiters.waitForElementToBeClickable(By.xpath(levelLocator));
            $(By.xpath(levelLocator)).click();
        }

        for (LanguageLevelData levelData : LanguageLevelData.values()) {
            waiters.waitForElementVisible(By.cssSelector("button[title='" + levelData.getDescription() + "']"));
        }

        $(By.cssSelector("button[title='" + languageLevelData.getDescription() + "']")).click();
        return this;
    }

    public PersonalBlockComponent setReadyToRelocate(boolean isReady) {
        if (isReady) {
            $(By.xpath("//input[@type='radio'][@value='True']/..")).click();
        } else {
            $(By.xpath("//input[@type='radio'][@value='False']/..")).click();
        }
        return this;
    }

    public PersonalBlockComponent setWorkSchedule(WorkScheduleData workScheduleData) {
        String checkbox = "//input[@type='checkbox'][@title='" + workScheduleData.getDescription() + "']";
        if (!isElementExists(By.xpath(checkbox + "[@checked]/..")))
            $(By.xpath(checkbox + "/..")).click();
        return this;
    }

    public PersonalBlockComponent setContactOK(String okText) {
        if (!isElementExists((By.xpath(this.okCTextLocator)))) {
            $(By.cssSelector(buttonSelectSelector)).click();
            $(By.cssSelector("span[class='placeholder']")).click();
            $(By.xpath("//input[@value=''][@class='js-custom-select-input ']/../following-sibling::div/div/button[@title='OK']")).click();
            enterText($(By.xpath(this.okCTextLocator)), okText);
        } else {
            enterText($(By.xpath(this.okCTextLocator)), okText);
        }
        return this;
    }

    public PersonalBlockComponent setContactVK(String vkText) {
        if (!isElementExists((By.xpath(this.vkCTextLocator)))) {
            $(By.cssSelector(buttonSelectSelector)).click();
            $(By.cssSelector("span[class='placeholder']")).click();
            $(By.xpath("//input[@value=''][@class='js-custom-select-input ']/../following-sibling::div/div/button[@title='VK']")).click();
            enterText($(By.xpath(this.vkCTextLocator)), vkText);
        } else {
            enterText($(By.xpath(this.vkCTextLocator)), vkText);
        }
        return this;
    }

    public PersonalBlockComponent setGender(GenderData gender) {
        WebElement id_gender = $(By.id("id_gender"));
        Select select = new Select(id_gender);
        select.selectByValue(GenderData.getSymbolByGender(gender));
        return this;
    }

    public PersonalBlockComponent checkGender(GenderData genderData) {
        Select select = new Select($(By.id("id_gender")));
        String actual = select.getFirstSelectedOption().getText();
        assertThat(actual)
                .as(String.format("Error. The Gender was expected %s, but the actual %s", genderData.getDescription(), actual))
                .isEqualTo(genderData.getDescription());
        return this;
    }

    public PersonalBlockComponent checkWorkSchedule(WorkScheduleData workScheduleData) {
        String checkbox = "//input[@type='checkbox'][@title='" + workScheduleData.getDescription() + "']";
        boolean actual = isElementExists(By.xpath(checkbox + "[@checked]/.."));
        assertThat(actual)
                .as(String.format("Error. The WorkSchedule was expected %s, but the actual %s", true, actual))
                .isEqualTo(true);
        return this;
    }

    public PersonalBlockComponent checkLReadyToRelocate(boolean isReady) {
        boolean actual;
        if (isReady) {
            actual = isElementExists(By.xpath("//input[@type='radio'][@value='True'][@checked]/.."));
        } else {
            actual = !isElementExists(By.xpath("//input[@type='radio'][@value='False'][@checked]/.."));
        }
        assertThat(actual)
                .as(String.format("Error. The ReadyToRelocate was expected %s, but the actual %s", isReady, actual))
                .isEqualTo(isReady);
        return this;
    }

    public PersonalBlockComponent checkLevel(LanguageLevelData level) {
        String actual = getTextElement((By.xpath("//input[@name='english_level']//following::div[1]")));
        assertThat(actual)
                .as(String.format("Error. The Level was expected %s, but the actual %s", level.getDescription(), actual))
                .isEqualTo(level.getDescription());
        return this;
    }

    public PersonalBlockComponent checkCity(String city) {
        String actual = getTextElement((By.xpath("//input[@name='city']//following::div[1]")));
        assertThat(actual)
                .as(String.format("Error. The City was expected %s, but the actual %s", city, actual))
                .isEqualTo(city);
        return this;
    }

    public PersonalBlockComponent checkCountry(String country) {
        String actual = getTextElement((By.xpath("//input[@name='country']//following::div[1]")));
        assertThat(actual)
                .as(String.format("Error. The Country was expected %s, but the actual %s", country, actual))
                .isEqualTo(country);
        return this;
    }

    public PersonalBlockComponent checkCalendar(LocalDate calendar) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateText = calendar.format(format);

        String actual = getTextElementByValue((By.cssSelector("[name='date_of_birth']")));
        assertThat(actual)
                .as(String.format("Error. The Calendar was expected %s, but the actual %s", dateText, actual))
                .isEqualTo(dateText);
        return this;
    }

    public PersonalBlockComponent checkCompany(String company) {
        String actual = getTextElementByValue((By.cssSelector(this.companySelector)));
        assertThat(actual)
                .as(String.format("Error. The Company was expected %s, but the actual %s", company, actual))
                .isEqualTo(company);
        return this;
    }

    public PersonalBlockComponent checkWork(String work) {
        String actual = getTextElementByValue((By.cssSelector(this.workSelector)));
        assertThat(actual)
                .as(String.format("Error. The Work was expected %s, but the actual %s", work, actual))
                .isEqualTo(work);
        return this;
    }

    public PersonalBlockComponent checkOk(String okText) {
        String actual = isElementExists(By.xpath(this.okCTextLocator)) ? getTextElementByValue((By.xpath(this.okCTextLocator))) : "";
        assertThat(actual)
                .as(String.format("Error. The Oktext was expected %s, but the actual %s", okText, actual))
                .isEqualTo(okText);
        return this;
    }

    public PersonalBlockComponent checkVk(String VkText) {
        String actual = isElementExists(By.xpath(this.vkCTextLocator)) ? getTextElementByValue((By.xpath(this.vkCTextLocator))) : "";
        assertThat(actual)
                .as(String.format("Error. The Vktext was expected %s, but the actual %s", VkText, actual))
                .isEqualTo(VkText);
        return this;
    }
}
