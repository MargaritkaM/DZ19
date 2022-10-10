import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryTest {

    @BeforeEach
    void setUp() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void shouldPositiveTestDateWithCssSelectors() {
        String planningDate = generateDate(4);
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input.input__control").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Мусатова-Иванова Маргарита");
        $("[data-test-id=phone] input").setValue("+79169044591");
        $("[data-test-id='agreement']").click();
        $("div>button").click();
        $("[data-test-id='notification']").should(visible, Duration.ofSeconds(15));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }
    @Test
    void shouldNegativeTestDateWithCssSelectors() {
        String planningDate = generateDate(2);
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input.input__control").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Мусатова Маргарита");
        $("[data-test-id=phone] input").setValue("+79169044591");
        $("[data-test-id='agreement']").click();
        $("div>button").click();
        $("[data-test-id='date'] span.input__sub")
                .shouldHave(Condition.text("Заказ на выбранную дату невозможен"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldNegativeTestCityWithCssSelectors() {
        String planningDate = generateDate(4);
        $("[data-test-id=city] input").setValue("Бутово");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input.input__control").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Мусатова Маргарита");
        $("[data-test-id=phone] input").setValue("+79169044591");
        $("[data-test-id='agreement']").click();
        $("div>button").click();
        $("[data-test-id=city] span.input__sub")
                .shouldHave(Condition.text("Доставка в выбранный город недоступна"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldNegativeTestNameWithCssSelectors() {
        String planningDate = generateDate(4);
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input.input__control").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Musatova Margarita");
        $("[data-test-id=phone] input").setValue("+79169044591");
        $("[data-test-id='agreement']").click();
        $("div>button").click();
        $("[data-test-id='name'] span.input__sub")
                .shouldHave(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldNegativeTestPhoneWithCssSelectors() {
        String planningDate = generateDate(4);
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input.input__control").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Иванова Татьяна");
        $("[data-test-id=phone] input").setValue("+7(916)9044591");
        $("[data-test-id='agreement']").click();
        $("div>button").click();
        $("[data-test-id='phone'] span.input__sub")
                .shouldHave(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldNegativeTestAgreementWithCssSelectors() {
        String planningDate = generateDate(4);
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input.input__control").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Иванова Татьяна");
        $("[data-test-id=phone] input").setValue("+79169044591");
        $("div>button").click();
        $(".input_invalid[data-test-id='agreement']")
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldPositiveTestWithXpath() {
        String planningDate = generateDate(4);
        $x(".//span[@data-test-id='city']//input").setValue("Самара");
        $x(".//input[@placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE);
        $x(".//input[@placeholder='Дата встречи']").setValue(planningDate);
        $x(".//span[@data-test-id='name']//input").setValue("Мусатова-Трофимова Маргарита");
        $x(".//span[@data-test-id='phone']//input").setValue("+79169044591");
        $x(".//label[@data-test-id='agreement']").click();
        $x(".//div/button").click();
        $x(".//div[@data-test-id='notification']").should(visible, Duration.ofSeconds(15));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldNegativeTestDateWithXpath() {
        String planningDate = generateDate(1);
        $x(".//span[@data-test-id='city']//input").setValue("Самара");
        $x(".//input[@placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE);
        $x(".//input[@placeholder='Дата встречи']").setValue(planningDate);
        $x(".//span[@data-test-id='name']//input").setValue("Мусатова Маргарита");
        $x(".//span[@data-test-id='phone']//input").setValue("+79169044591");
        $x(".//label[@data-test-id='agreement']").click();
        $x(".//div/button").click();
        $x("//span[contains(text(), 'Заказ на выбранную дату невозможен')]")
                .shouldHave(Condition.text("Заказ на выбранную дату невозможен"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldNegativeTestCityWithXpath() {
        String planningDate = generateDate(3);
        $x(".//span[@data-test-id='city']//input").setValue("Егорьевск");
        $x(".//input[@placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE);
        $x(".//input[@placeholder='Дата встречи']").setValue(planningDate);
        $x(".//span[@data-test-id='name']//input").setValue("Мусатова Маргарита");
        $x(".//span[@data-test-id='phone']//input").setValue("+79169044591");
        $x(".//label[@data-test-id='agreement']").click();
        $x(".//div/button").click();
        $x("//span[contains(text(), 'Доставка в выбранный город недоступна')]")
                .shouldHave(Condition.text("Доставка в выбранный город недоступна"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldNegativeTestNameWithXpath() {
        String planningDate = generateDate(3);
        $x(".//span[@data-test-id='city']//input").setValue("Самара");
        $x(".//input[@placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE);
        $x(".//input[@placeholder='Дата встречи']").setValue(planningDate);
        $x(".//span[@data-test-id='name']//input").setValue("Ivan Ivanovich");
        $x(".//span[@data-test-id='phone']//input").setValue("+79169044591");
        $x(".//label[@data-test-id='agreement']").click();
        $x(".//div/button").click();
        $x("//span[contains(text(), 'Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.')]")
                .shouldHave(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldNegativeTestPhoneWithXpath() {
        String planningDate = generateDate(3);
        $x(".//span[@data-test-id='city']//input").setValue("Самара");
        $x(".//input[@placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE);
        $x(".//input[@placeholder='Дата встречи']").setValue(planningDate);
        $x(".//span[@data-test-id='name']//input").setValue("Куницына Маргарита");
        $x(".//span[@data-test-id='phone']//input").setValue("+7(916)904-45-91");
        $x(".//label[@data-test-id='agreement']").click();
        $x(".//div/button").click();
        $x("//span[contains(text(), 'Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.')]")
                .shouldHave(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldNegativeTestAgreementWithXpath() {
        String planningDate = generateDate(3);
        $x(".//span[@data-test-id='city']//input").setValue("Самара");
        $x(".//input[@placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE);
        $x(".//input[@placeholder='Дата встречи']").setValue(planningDate);
        $x(".//span[@data-test-id='name']//input").setValue("Куницына Маргарита");
        $x(".//span[@data-test-id='phone']//input").setValue("+79169044591");
        $x(".//div/button").click();
        $x(".//label['input__invalid']")
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldPositiveTestWithCssSelectorThreeCityLetter() {
        String planningDate = generateDate(4);
        $("[data-test-id=city] input").sendKeys("Мос");
        $(byText("Москва")).click();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input.input__control").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Мусатова Маргарита");
        $("[data-test-id=phone] input").setValue("+79169044591");
        $("[data-test-id='agreement']").click();
        $("div>button").click();
        $("[data-test-id='notification']").should(visible, Duration.ofSeconds(15));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldPositiveTestWithCssSelectorChoiceData() {
        String planningDate = generateDate(14);
        $("[data-test-id=city] input").sendKeys("Мос");
        $(byText("Москва")).click();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input.input__control").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Мусатова Маргарита");
        $("[data-test-id=phone] input").setValue("+79169044591");
        $("[data-test-id='agreement']").click();
        $("div>button").click();
        $("[data-test-id='notification']").should(visible, Duration.ofSeconds(15));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }
}
