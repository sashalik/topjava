package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MealsListInit {
    public static List<MealTo> getListMealTo(int caloriesPerDay) {
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

        Map<LocalDate, Integer> sumCaloriesLocalDate = meals.stream()
                .collect(Collectors.groupingBy(Meal::getDate, Collectors.reducing(0, Meal::getCalories, Integer::sum)));

        return meals.stream()
                .map(meal -> new MealTo(meal.getDateTime(), meal.getDescription(), meal.getCalories(), (sumCaloriesLocalDate.get(meal.getDate()) > caloriesPerDay)))
                .collect(Collectors.toList());

    }
}
