package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class MealsListInit {
    public static List<Meal> getListMealTo() {
        /*List<Meal> meals = Arrays.asList(
                new Meal(LocalDateTime.of(2016, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2016, Month.MAY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2016, Month.MAY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2016, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2016, Month.MAY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2016, Month.MAY, 31, 20, 0), "Ужин", 510)
        );*/

        List<Meal> meals = Arrays.asList(
                new Meal(LocalDateTime.of(2014, Month.AUGUST, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2014, Month.AUGUST, 30, 13, 0), "Обед", 100),
                new Meal(LocalDateTime.of(2014, Month.AUGUST, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2014, Month.AUGUST, 31, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2014, Month.AUGUST, 31, 13, 0), "Обед", 5000),
                new Meal(LocalDateTime.of(2014, Month.AUGUST, 31, 20, 0), "Ужин", 510)
        );

        return meals;


    }
}
