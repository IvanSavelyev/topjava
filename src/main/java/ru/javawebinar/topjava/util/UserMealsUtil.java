package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),

                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410),

                new UserMeal(LocalDateTime.of(2020, Month.FEBRUARY, 1, 10, 0), "Ужин", 410),
                new UserMeal(LocalDateTime.of(2020, Month.FEBRUARY, 1, 13, 0), "Завтрак", 710),
                new UserMeal(LocalDateTime.of(2020, Month.FEBRUARY, 1, 20, 0), "Ужин", 333),

                new UserMeal(LocalDateTime.of(2020, Month.FEBRUARY, 2, 0, 0), "Еда на граничное значение", 50),
                new UserMeal(LocalDateTime.of(2020, Month.FEBRUARY, 2, 10, 0), "Завтрак", 1111),
                new UserMeal(LocalDateTime.of(2020, Month.FEBRUARY, 2, 13, 0), "Обед", 422),
                new UserMeal(LocalDateTime.of(2020, Month.FEBRUARY, 2, 20, 0), "Ужин", 424)
        );
        System.out.println("filteredByCycles method");
        List<UserMealWithExcess> mealsToCycles = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsToCycles.forEach(System.out::println);
        System.out.println("------------------------");
        System.out.println("filteredByStreams method");
        List<UserMealWithExcess> mealsToStreams = filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        assert mealsToStreams != null;
        mealsToStreams.forEach(System.out::println);

    }
    //Time complexity: - O(N + N) = O(N)
    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumInDay = new HashMap<>();
        for (UserMeal meal : meals)
            caloriesSumInDay.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum);

        List<UserMealWithExcess> userMealWithExcessList = new ArrayList<>();
        for (UserMeal meal : meals) {
            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                userMealWithExcessList.add(
                        new UserMealWithExcess( meal.getDateTime(), meal.getDescription(),
                                                meal.getCalories(), caloriesSumInDay.get(meal.getDateTime().toLocalDate()) > caloriesPerDay)
                );
        }
        return userMealWithExcessList;

    }
    //Stream filtering uses iteration internally
    //Time complexity: - O(N + N) = O(N)
    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumInDay = meals.stream()
                .collect(Collectors.groupingBy(userMeal -> userMeal.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));

        return meals.stream()
                .filter(userMeal -> TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                .map(userMeal ->
                        new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(),
                                userMeal.getCalories(), caloriesSumInDay.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());

    }
}


