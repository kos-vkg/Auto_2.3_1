package ru.netology.web;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import java.time.Duration;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

class CardDeliveryTest {

    @Test
    void shouldHappyPath() {
        //String dateMeeting = LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        UserData user = DataGenerator.generateUser(4);
        open("http://localhost:9999");
        $(" [data-test-id='city'] input").setValue(user.getCity());
        $(" [data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $(" [data-test-id='date'] input").setValue(user.getDate());
        $("[data-test-id='name'] input").setValue(user.getName());
        $("[name=\"phone\"]").setValue(user.getPhone());
        $("[data-test-id=\"agreement\"]").click();
        $$("[type='button']").findBy(cssClass("button")).click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification'] > .notification__content")
                .shouldHave(text("Встреча успешно запланирована на " + user.getDate()));

        // повторная попытка с другой датой
        $(" [data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $(" [data-test-id='date'] input").setValue(DataGenerator.generateDate(6));
        $$("[type='button']").findBy(cssClass("button")).click();
        $(withText("Перепланировать?")).shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content > button").click();
        $("[data-test-id='success-notification'] > .notification__content")
                .shouldHave(text("Встреча успешно запланирована на " + DataGenerator.generateDate(6)));
    }
}
