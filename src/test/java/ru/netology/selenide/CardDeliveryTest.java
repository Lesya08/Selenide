package ru.netology.selenide;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class CardDeliveryTest {
    private String generateData(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void successfulCompilationTest() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Казань");
        String planingData = generateData(4, "dd.MM.yyyy");
        $("[data-test-id='data'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='data'] input").setValue(planingData);
        $("[data-test-id='name'] input").setValue("Петров-Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+78004000000");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldBe(Condition.exactText("Встреча успешно забронирована на " + planingData));
    }
}
