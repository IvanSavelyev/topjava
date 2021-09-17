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
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        //Map<LocalDateTime, Long> caloriesSumInDay = meals.stream().collect(Collectors.groupingBy(UserMeal::getDateTime, Collectors.counting()));
        //caloriesSumInDay.forEach((key, value) -> System.out.println(key + "--" + value));
        //List<UserMeal> meals = new ArrayList<>();
        List<UserMealWithExcess> mealsFilteredByCycle = filteredByCycles(meals, LocalTime.of(5, 0), LocalTime.of(15, 0), 400);
        List<UserMealWithExcess> mealsFilteredByStreams = filteredByStreams(meals, LocalTime.of(5, 0), LocalTime.of(15, 0), 600);

        System.out.println("Cycle Filter");
        mealsFilteredByCycle.forEach(System.out::println);
        System.out.println("-------------");
        System.out.println("Stream Filter");
        mealsFilteredByStreams.forEach(System.out::println);

        //System.out.println(TimeUtil.isBetweenHalfOpen(meals.get(0).getDateTime().toLocalTime(), LocalTime.of(11, 0), LocalTime.of(12, 0)));
        //System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        //caloriesSumInDay.forEach((key, value) -> System.out.println(key + "--" + value));
        //Map<LocalDate, Integer> caloriesSumInDay =  meals.stream().collect(
        //Collectors.groupingBy(UserMeal::getDateTime)).entrySet(Collectors.g Collectors.summingInt(UserMeal::getCalories));


    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        if(!meals.isEmpty()){
            Map<LocalDate, Integer> caloriesSumInDay = new HashMap<>();
            for (UserMeal meal : meals)
                caloriesSumInDay.put(meal.getDateTime().toLocalDate(), caloriesSumInDay.getOrDefault(meal.getDateTime().toLocalDate(), 0 ) + meal.getCalories());


            List<UserMealWithExcess> userMealWithExcessList = new ArrayList<>();
            for (UserMeal meal : meals) {
                if(TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime) && meal.getCalories() > caloriesPerDay)
                    userMealWithExcessList.add(
                            new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), caloriesSumInDay.get(meal.getDateTime().toLocalDate()) > caloriesPerDay)
                    );
            }
            return userMealWithExcessList;
        }
        else
            throw new IllegalArgumentException();
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        if(!meals.isEmpty()){
            Map<LocalDate, Integer> caloriesSumInDay = meals.stream().
                    collect(Collectors.groupingBy(userMeal -> userMeal.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));
            //List<UserMealWithExcess> userMealWithExcessList = new ArrayList<>();
            return meals.stream().filter(userMeal -> TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime)).
                    map(userMeal -> new UserMealWithExcess(userMeal.getDateTime(),userMeal.getDescription(), userMeal.getCalories(), caloriesSumInDay.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay)).
                    collect(Collectors.toList());
        }
        else
            throw new IllegalArgumentException();
    }

}
