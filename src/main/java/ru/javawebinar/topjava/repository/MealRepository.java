package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

public interface MealRepository {
    Meal save(Meal meal, int userId);

    void delete(int id, int userId);

    Meal get(int id, int userId);

    Collection<Meal> getAll(int userId);

    List<MealTo> getAll();

    List<MealTo> getUserMeal(int userId);

    List<MealTo> getFilterUserMeal(int userId, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime);
}
