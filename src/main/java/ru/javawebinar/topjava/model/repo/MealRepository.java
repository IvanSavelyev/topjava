package ru.javawebinar.topjava.model.repo;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface MealRepository {

    Collection<Meal> getAll();

    Meal edit(Integer id);

    Meal add(Meal meal);

    void delete(Integer id);
}
