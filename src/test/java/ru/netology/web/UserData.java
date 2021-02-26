package ru.netology.web;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class UserData {
   private final String name;
   private final String phone;
   private final String city;
   private final String date;
}
