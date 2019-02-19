package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> mealMap = new ConcurrentHashMap<>();
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(MEAL -> save(MEAL, 1));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            mealMap.put(meal.getId(), meal);
            repository.putIfAbsent(userId, mealMap);
            return meal;
        }
        // treat case: update, but absent in storage
        return repository.get(userId).computeIfAbsent(meal.getId(), k -> meal);
    }

    @Override
    public void delete(int id, int userId) {
        repository.get(userId).remove(id);
    }

    @Override
    public Meal get(int id, int userId) {
        return repository.get(userId).get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.get(userId).values().stream().sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList());
    }

    @Override
    public List<MealTo> getAll() {
        return null;
    }

    @Override
    public List<MealTo> getUserMeal(int userId) {
        Map<LocalDate, Integer> sumCaloriesLocalDate = repository.get(userId).values().stream()
                .collect(Collectors.groupingBy(Meal::getDate, Collectors.reducing(0, Meal::getCalories, Integer::sum)));

        return repository.get(userId).values().stream()
                .map(meal -> new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), (sumCaloriesLocalDate.get(meal.getDate()) > authUserCaloriesPerDay())))
                .collect(Collectors.toList());
    }

    @Override
    public List<MealTo> getFilterUserMeal(int userId, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime){
        Map<LocalDate, Integer> sumCaloriesLocalDate = repository.get(userId).values().stream()
                .filter(meal -> DateTimeUtil.isBetweenDate(meal.getDate(), startDate, endDate))
                .collect(Collectors.groupingBy(Meal::getDate, Collectors.reducing(0, Meal::getCalories, Integer::sum)));

        return repository.get(userId).values().stream()
                .filter(meal -> DateTimeUtil.isBetweenTime(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), (sumCaloriesLocalDate.get(meal.getDate()) > authUserCaloriesPerDay())))
                .collect(Collectors.toList());
    }
}

