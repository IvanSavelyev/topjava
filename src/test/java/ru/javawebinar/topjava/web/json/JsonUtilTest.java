package ru.javawebinar.topjava.web.json;

import org.junit.jupiter.api.Test;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;

import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;

class JsonUtilTest {

    @Test
    void readWriteValue() {
        String json = JsonUtil.writeValue(adminMeal1);
        System.out.println(json);
        Meal meal = JsonUtil.readValue(json, Meal.class);
        MEAL_MATCHER.assertMatch(meal, adminMeal1);
    }

    @Test
    void readWriteValues() {
        String json = JsonUtil.writeValue(userMeals);
        System.out.println(json);
        List<Meal> meals = JsonUtil.readValues(json, Meal.class);
        MEAL_MATCHER.assertMatch(meals, MealTestData.userMeals);
    }
}