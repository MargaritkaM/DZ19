import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class deliveryTest {

    @BeforeEach
    void setUp() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }
    @Test
    void PositiveTestWithCssSelectors() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input.input__control").setValue("05.10.2022");
        $("[data-test-id=name] input").setValue("Мусатова Маргарита");
        $("[data-test-id=phone] input").setValue("+79169044591");
        $("[data-test-id='agreement']").click();
        $("div>button").click();
        $("[data-test-id='notification']").should(visible, Duration.ofSeconds(15));
    }
    @Test
    void PositiveTestWithXpass (){
        $x(".//span[@data-test-id='city']//input").setValue("Самара");
        $x(".//input[@placeholder='Дата встречи']").doubleClick().sendKeys(Keys.DELETE);
        $x(".//input[@placeholder='Дата встречи']").setValue("10.10.2022");
        $x(".//span[@data-test-id='date']//input[@value]").setValue("10.10.2022");
        $x(".//span[@data-test-id='name']//input").setValue("Мусатова Маргарита");
        $x(".//span[@data-test-id='phone']//input").setValue("+79169044591");
        $x(".//label[@data-test-id='agreement']").click();
        $x(".//div/button").click();
        $x(".//div[@data-test-id='notification']").should(visible, Duration.ofSeconds(15));
    }
    @Test
    void PositiveTestWithCssSelectorThreeCityLettrs() {
        $("[data-test-id=city] input").sendKeys("Мос");
        $(byText("Москва")).click();
        $("[data-test-id=date] input.input__control").setValue("05.10.2022");
        $("[data-test-id=name] input").setValue("Мусатова Маргарита");
        $("[data-test-id=phone] input").setValue("+79169044591");
        $("[data-test-id='agreement']").click();
        $("div>button").click();
        $("[data-test-id='notification']").should(visible, Duration.ofSeconds(15));
    }
    @Test
    void PositiveTestWithCssSelectorChoiceData() {
        $("[data-test-id=city] input").setValue("Москва");
        $("span>button").click();
        $(byCssSelector("td[data-day='1665522000000']")).click();
        $("[data-test-id=name] input").setValue("Мусатова Маргарита");
        $("[data-test-id=phone] input").setValue("+79169044591");
        $("[data-test-id='agreement']").click();
        $("div>button").click();
        $("[data-test-id='notification']").should(visible, Duration.ofSeconds(15));
    }
}
