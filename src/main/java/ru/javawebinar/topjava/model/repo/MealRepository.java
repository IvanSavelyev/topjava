package ru.javawebinar.topjava.model.repo;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface MealRepository {

    Collection<Meal> getAllMeals();

    Meal editMeal(String id);

    void addMeal(Meal meal);

    void deleteMeal(String id);

}
