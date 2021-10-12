package ru.javawebinar.topjava.model.repo;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface MealRepository {

    Collection<Meal> getAll();

    Meal get(int id);

    Meal add(Meal meal);

    void delete(int id);
}
