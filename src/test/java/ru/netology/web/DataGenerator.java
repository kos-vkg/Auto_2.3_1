package ru.netology.web;

import com.github.javafaker.Faker;

import java.util.Locale;
import java.time.format.DateTimeFormatter;
import java.time.*;

public class DataGenerator {
    static Faker faker = new Faker(new Locale("ru"));
     // массив необслуживаемых городов
    public static final String[] ARR_CITY = {"Новокузнецк", "Магнитогорск", "Сочи", "Тольятти"};

    public static String generateDate(int plusDays) {
        return LocalDate.now().plusDays(plusDays).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static boolean findCity(String genCity) {
        for (String city : ARR_CITY) {
            if (genCity.equals(city)) return true;
        }
        return false;
    }

    public static UserData generateUser(int plusDays) {
        UserData user = new UserData(faker.name().fullName()
                , faker.phoneNumber().phoneNumber(), faker.address().city(), generateDate(plusDays));
     // исключение необслуживаемых городов и буквы ё см.Issue(временно, до исправления ошибки)
        while (findCity(user.getCity()) || (user.getName().indexOf("ё") >= 0)) {
            user = DataGenerator.generateUser(plusDays);
        }
        return user;
    }

}
