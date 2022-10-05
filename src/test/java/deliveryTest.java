import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class deliveryTest {


    @Test
    void PositiveTestWithCssSelectors() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input.input__control").setValue("05.10.2022");
        $("[data-test-id=name] input").setValue("Мусатова Маргарита");
        $("[data-test-id=phone] input").setValue("+79169044591");
        $("[data-test-id='agreement']").click();
        $("div>button").click();
        $("[data-test-id='notification']").should(visible, Duration.ofSeconds(15));
    }
}
