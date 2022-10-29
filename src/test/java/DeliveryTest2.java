import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryTest2 {

    public String searchByDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));

    }

    @Test
    void shouldPositiveTestNearestData() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");

        String planningDate = searchByDate(4, "dd.MM.yyyy");
        String weekDay = searchByDate(4, "d");
        String thisMonth = searchByDate(0, "M");
        String weekMonth = searchByDate(0, "M");

        $("[data-test-id=city] input").sendKeys("Мос");
        $(byText("Москва")).click();

        $$(".icon-button_size_m").first().click();
        if (thisMonth.equals(weekMonth)) {
            $$(".calendar__day").find((exactText(weekDay))).click();
        } else {
            $("[data-step='1']").click();
            $$(".calendar__day").find((exactText(weekDay))).click();
        }

        $("[data-test-id=name] input").setValue("Мусатова-Иванова Маргарита");
        $("[data-test-id=phone] input").setValue("+79169044591");
        $("[data-test-id='agreement']").click();
        $("div>button").click();

        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

}
