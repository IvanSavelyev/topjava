package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        if(!meals.isEmpty()){
            Map<LocalDate, Integer> caloriesSumInDay = new HashMap<>();
            int calories = 0;
            //First variant
            //-----------------------------------------------------
            for (int n = 0; n < meals.size() - 1; ++n){
                if(meals.get(n).getDateTime().toLocalDate().equals(meals.get(n + 1).getDateTime().toLocalDate())){
                    calories += meals.get(n).getCalories();
                    if(n == meals.size() - 2) {
                        caloriesSumInDay.put(meals.get(n).getDateTime().toLocalDate(), calories += meals.get(n + 1).getCalories());
                        calories = 0;
                    }
                }
                else{
                    calories += meals.get(n).getCalories();
                    caloriesSumInDay.put(meals.get(n).getDateTime().toLocalDate(), calories);
                    if(n == meals.size() - 2){
                        caloriesSumInDay.put(meals.get(n + 1).getDateTime().toLocalDate(), meals.get(n + 1).getCalories());
                    }
                    calories = 0;
                }
            }
            System.out.println("---------First variant--------");
            caloriesSumInDay.forEach((key, value) -> System.out.println(key + "--" + value));
            caloriesSumInDay.clear();
            //-----------------------------------------------------
            //Second variant
            for (UserMeal meal : meals){
                caloriesSumInDay.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum);
            }
            System.out.println("---------Second variant--------");
            caloriesSumInDay.forEach((key, value) -> System.out.println(key + "--" + value));
            caloriesSumInDay.clear();
            //-----------------------------------------------------
            //Third variant
            for (UserMeal meal : meals){
                caloriesSumInDay.put(meal.getDateTime().toLocalDate(), caloriesSumInDay.getOrDefault(meal.getDateTime().toLocalDate(), 0 ) + meal.getCalories());
            }
            System.out.println("---------Third variant--------");
            caloriesSumInDay.forEach((key, value) -> System.out.println(key + "--" + value));
            //-----------------------------------------------------
/*            //Fourth variant
            for (UserMeal meal : meals){
                calories = caloriesSumInDay.containsKey(meal.getDateTime().toLocalDate()) ? caloriesSumInDay.get(meal.getDateTime().toLocalDate()) : 0;
                calories += meal.getCalories();
                caloriesSumInDay.put(meal.getDateTime().toLocalDate(), calories);
            }
            System.out.println("---------Fourth variant--------");
            caloriesSumInDay.forEach((key, value) -> System.out.println(key + "--" + value));
            //Fourth = Third
*/
            List<UserMealWithExcess> userMealWithExcessList = new ArrayList<>();
            for (UserMeal meal : meals) {
                if(TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime) && meal.getCalories() > caloriesPerDay)
                    userMealWithExcessList.add(
                            new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), caloriesSumInDay.get(meal.getDateTime().toLocalDate()) > caloriesPerDay)
                    );
            }
            return userMealWithExcessList;
        }
        else {
            System.err.println("IllegalArgumentException");
            throw new IllegalArgumentException();
        }
        //Time complexity: - O(N + N) = O(N)
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        if(!meals.isEmpty()){
            Map<LocalDate, Integer> caloriesSumInDay = meals.stream().
                    collect(Collectors.groupingBy(userMeal -> userMeal.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));

            return meals.stream()
                    .filter(userMeal -> TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                    .map(userMeal -> new UserMealWithExcess(userMeal.getDateTime(),userMeal.getDescription(), userMeal.getCalories(), caloriesSumInDay.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay))
                    .collect(Collectors.toList());
            //Stream filtering uses iteration internally
            //Time complexity: - O(N + N) = O(N)
        }
        else{
            System.err.println("IllegalArgumentException");
            throw new IllegalArgumentException();
        }
    }
}
