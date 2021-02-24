package ru.netology.web;

import com.github.javafaker.Faker;

import java.util.Locale;

public class DataGenerator {
    static Faker faker = new Faker(new Locale("ru"));

    public static UserData generateUser() {
        return new  UserData(faker.name().fullName(), faker.phoneNumber().phoneNumber(), faker.address().city());
    }

}
