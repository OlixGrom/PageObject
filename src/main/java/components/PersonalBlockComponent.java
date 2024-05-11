package components;

import data.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonalBlockComponent extends AbsBaseComponent {
    private final String fname = "input[name='fname']";
    private final String fnameLatin = "input[name='fname_latin']";
    private final String lname = "input[name='lname']";
    private final String lnameLatin = "input[name='lname_latin']";
    private final String blogName = "input[name='blog_name']";
    private final String dateBirth = "input[name='date_of_birth']";
    private final String phone = "input[class='input input_full'][placeholder='Телефон']";
    private final String vkCText = "//input[@value='vk']/../../following-sibling::input";
    private final String okCText = "//input[@value='ok']/../../following-sibling::input";
    private final String buttonSave = "button[title='Сохранить и продолжить']";
    //private final String buttonSelect = "button[class='lk-cv-block__action lk-cv-block__action_md-no-spacing js-formset-add js-lk-cv-custom-select-add']";
    private final String buttonSelect = "button.js-lk-cv-custom-select-add";
    private final String company = "input[id='id_company']";
    private final String work = "input[id='id_work']";
    private final String level = "//input[@name='english_level']/..";

    public PersonalBlockComponent(WebDriver driver) {
        super(driver);
    }

    private static void scrollToElement(WebDriver webDriver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    private void enterText(WebElement webElement, String text) {
        webElement.clear();
        webElement.sendKeys(text);
    }

    private String getTextElementByValue(By by) {
        return $(by).getAttribute("value");
    }

    private String getTextElement(By by) {
        return $(by).getText();
    }

    private boolean isElementExists(By by) {
        boolean isExists = true;
        try {
            $(by);
        } catch (NoSuchElementException e) {
            isExists = false;
        }
        return isExists;
    }

    public PersonalBlockComponent clickSave() {
        $(By.cssSelector(buttonSave)).click();
        return this;
    }

    public PersonalBlockComponent setFName(String fname) {
        enterText($(By.cssSelector(this.fname)), fname);
        return this;
    }

    public PersonalBlockComponent setFNameLatin(String fNameLatin) {
        enterText($(By.cssSelector(this.fnameLatin)), fNameLatin);
        return this;
    }

    public PersonalBlockComponent setLName(String lName) {
        enterText($(By.cssSelector(this.lname)), lName);
        return this;
    }

    public PersonalBlockComponent setLNameLatin(String lNameLatin) {
        enterText($(By.cssSelector(this.lnameLatin)), lNameLatin);
        return this;
    }

    public PersonalBlockComponent setBlogName(String blogName) {
        enterText($(By.cssSelector(this.blogName)), blogName);
        return this;
    }

    public PersonalBlockComponent setCalendar(LocalDate dateBirth) {
        //String[] listData = dateBirth.split("\\.");
        $(By.cssSelector(this.dateBirth)).click();
        $(By.cssSelector("div[data-view='days picker'] ul li[data-view='month current']")).click();
        $(By.cssSelector("div[data-view='months picker'] ul li[data-view='year current']")).click();
        boolean isFound = false;

        while (!isFound) {
            if (isElementExists(By.xpath("//div[@data-view='years picker']//ul[@data-view='years']//li[text()='"+dateBirth.getYear()+"']"))) {
                isFound = true;
                $(By.xpath("//div[@data-view='years picker']//ul[@data-view='years']//li[text()='" + dateBirth.getYear() + "']")).click();
            } else {
                $(By.cssSelector("div[data-view='years picker'] ul li[data-view='years prev']")).click();
            }
        }

        String mounth = MonthData.getDescriptionBySymbol(Integer.toString(dateBirth.getMonthValue()));
        $(By.xpath("//div[@data-view='months picker']//ul[@data-view='months']//li[text()='" + mounth + "']")).click();
        $(By.xpath("//div[@data-view='days picker']//ul[@data-view='days']//li[text()='" + dateBirth.getDayOfMonth() + "']")).click();
        return this;
    }

    public PersonalBlockComponent setCompany(String companyName) {
        enterText($(By.cssSelector(company)), companyName);
        return this;
    }

    public PersonalBlockComponent setWork(String workName) {
        enterText($(By.cssSelector(work)), workName);
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
            waiters.waitForElementToBeClickable(By.xpath(level));
            $(By.xpath(level)).click();
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
        if (!isElementExists((By.xpath(this.okCText)))) {
            $(By.cssSelector(buttonSelect)).click();
            $(By.cssSelector("span[class='placeholder']")).click();
            $(By.xpath("//input[@value=''][@class='js-custom-select-input ']/../following-sibling::div/div/button[@title='OK']")).click();
            enterText($(By.xpath(this.okCText)), okText);
        } else {
            enterText($(By.xpath(this.okCText)), okText);
        }
        return this;
    }

    public PersonalBlockComponent setContactVK(String vkText) {
        if (!isElementExists((By.xpath(this.vkCText)))) {
            $(By.cssSelector(buttonSelect)).click();
            $(By.cssSelector("span[class='placeholder']")).click();
            $(By.xpath("//input[@value=''][@class='js-custom-select-input ']/../following-sibling::div/div/button[@title='VK']")).click();
            enterText($(By.xpath(this.vkCText)), vkText);
        } else {
            enterText($(By.xpath(this.vkCText)), vkText);
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

    public PersonalBlockComponent checkLNameLatin(String lname_latin) {
        String actual = getTextElementByValue((By.cssSelector(this.lnameLatin)));
        assertThat(actual)
                .as(String.format("Error. The LNameLatin was expected %s, but the actual %s", lname_latin, actual))
                .isEqualTo(lname_latin);
        return this;
    }

    public PersonalBlockComponent checkLName(String lname) {
        String actual = getTextElementByValue((By.cssSelector(this.lname)));
        assertThat(actual)
                .as(String.format("Error. The LName was expected %s, but the actual %s", lname, actual))
                .isEqualTo(lname);
        return this;
    }

    public PersonalBlockComponent checkFName(String fname) {
        String actual = getTextElementByValue((By.cssSelector(this.fname)));
        assertThat(actual)
                .as(String.format("Error. The FName was expected %s, but the actual %s", fname, actual))
                .isEqualTo(fname);
        return this;
    }

    public PersonalBlockComponent checkFNameLatin(String fname_latin) {
        String actual = getTextElementByValue((By.cssSelector(this.fnameLatin)));
        assertThat(actual)
                .as(String.format("Error. The FNameLatin was expected %s, but the actual %s", fname_latin, actual))
                .isEqualTo(fname_latin);
        return this;
    }

    public PersonalBlockComponent checkBlockName(String blogName) {
        String actual = getTextElementByValue((By.cssSelector(this.blogName)));
        assertThat(actual)
                .as(String.format("Error. The BlockName was expected %s, but the actual %s", blogName, actual))
                .isEqualTo(blogName);
        return this;
    }

    public PersonalBlockComponent checkCompany(String company) {
        String actual = getTextElementByValue((By.cssSelector(this.company)));
        assertThat(actual)
                .as(String.format("Error. The Company was expected %s, but the actual %s", company, actual))
                .isEqualTo(company);
        return this;
    }

    public PersonalBlockComponent checkWork(String work) {
        String actual = getTextElementByValue((By.cssSelector(this.work)));
        assertThat(actual)
                .as(String.format("Error. The Work was expected %s, but the actual %s", work, actual))
                .isEqualTo(work);
        return this;
    }

    public PersonalBlockComponent checkOk(String okText) {
        String actual = isElementExists(By.xpath(this.okCText)) ? getTextElementByValue((By.xpath(this.okCText))) : "";
        assertThat(actual)
                .as(String.format("Error. The Oktext was expected %s, but the actual %s", okText, actual))
                .isEqualTo(okText);
        return this;
    }

    public PersonalBlockComponent checkVk(String VkText) {
        String actual = isElementExists(By.xpath(this.vkCText)) ? getTextElementByValue((By.xpath(this.vkCText))) : "";
        assertThat(actual)
                .as(String.format("Error. The Vktext was expected %s, but the actual %s", VkText, actual))
                .isEqualTo(VkText);
        return this;
    }
}
