package ru.netology.web;

import com.github.javafaker.CreditCardType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.format.DateTimeFormatter;
import java.time.Duration;
import java.time.*;
import java.util.Locale;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

import com.github.javafaker.Faker;

class CardDeliveryTest {

    private Faker faker;

    @BeforeEach
    void setUpAll() {
        faker = new Faker(new Locale("ru"));
    }

    @Test
    void shouldHappyPath() {

        String name = faker.name().fullName();
        String phone = faker.phoneNumber().phoneNumber();
        String city = faker.address().city();
        String dateMeeting = LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        open("http://localhost:9999");
        $(" [data-test-id='city'] input").setValue(city);
        $(" [data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $(" [data-test-id='date'] input").setValue(dateMeeting);
        $("[data-test-id='name'] input").setValue(name);
        $("[name=\"phone\"]").setValue(phone);
        $("[data-test-id=\"agreement\"]").click();
        $$("[type='button']").findBy(cssClass("button")).click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification'] > .notification__content")
                .shouldHave(text("Встреча успешно запланирована на " + dateMeeting));

        // повторная попытка с другой датой
        String dateMeeting2 = LocalDate.now().plusDays(6).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $(" [data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $(" [data-test-id='date'] input").setValue(dateMeeting2);
        $$("[type='button']").findBy(cssClass("button")).click();
        $(withText("Перепланировать?")).shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content > button").click();
        $("[data-test-id='success-notification'] > .notification__content")
                .shouldHave(text("Встреча успешно запланирована на " + dateMeeting2));

    }
}
