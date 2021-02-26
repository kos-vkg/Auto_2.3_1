package ru.netology.web;

import com.github.javafaker.Faker;
import java.util.Locale;
import java.time.format.DateTimeFormatter;
import java.time.*;

public class DataGenerator {
    static Faker faker = new Faker(new Locale("ru"));

   public static  String generateDate(int plusDays){
     return  LocalDate.now().plusDays(plusDays).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
   }

    public static UserData generateUser(int plusDays) {
        UserData user = new UserData(faker.name().fullName()
                , faker.phoneNumber().phoneNumber(), faker.address().city(),generateDate(plusDays));
        // исключение необслуживаемых городов см.Issue
         if (user.getCity().equals("Новокузнецк") || user.getCity().equals("Сочи")
                || user.getCity().equals("Магнитогорск")) {
            System.out.println(user.getCity());
            user = DataGenerator.generateUser(plusDays);
        }
        return user;
    }
}
