import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;

public class deliveryTest {


    @Test
    void test (){
    Configuration.holdBrowserOpen=true;
    //оставляет браузер открытым
    open ("http://localhost:9999/");
//    $("[input__box]").setValue("Самара");
//    $("[placeholder=\"Город\"]").setValue("Москва");
//    $x(".//span[@data-test-id='city']//input").setValue("Самара");
$("[data-test-id=city] input.input__control").setValue("Москва");
    }

}
