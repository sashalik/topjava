package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 300),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 5000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 100),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );

        System.out.println(getFilteredWithExceededStream(mealList, LocalTime.of(7, 0), LocalTime.of(15, 0), 2000));
        System.out.println(getFilteredWithExceededCycle(mealList, LocalTime.of(7, 0), LocalTime.of(15, 0), 2000));
    }

    public static List<UserMealWithExceed> getFilteredWithExceededStream(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> sumCaloriesLocalDate = mealList.stream()
                .collect(Collectors.groupingBy(UserMeal::getDate, Collectors.reducing(0, UserMeal::getCalories, Integer::sum)));

        return mealList.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), (sumCaloriesLocalDate.get(meal.getDate()) > caloriesPerDay)))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExceed> getFilteredWithExceededCycle(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> userMealWithExceeds = new ArrayList<>();
        Map<LocalDate, Integer> sumCaloriesLocalDate = new HashMap<>();
        Map<LocalDateTime, UserMeal> mapUserMeal     = new HashMap<>();

        for (UserMeal userMeal : mealList) {
            sumCaloriesLocalDate.merge(userMeal.getDate(), userMeal.getCalories(), Integer::sum);
            if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) mapUserMeal.put(userMeal.getDateTime(), userMeal);
        }

        for (Map.Entry<LocalDateTime, UserMeal> um : mapUserMeal.entrySet()){
            userMealWithExceeds.add(new UserMealWithExceed(um.getValue().getDateTime(), um.getValue().getDescription(), um.getValue().getCalories(), (sumCaloriesLocalDate.get(um.getValue().getDate()) > caloriesPerDay)));
        }

        return userMealWithExceeds;
    }
}
