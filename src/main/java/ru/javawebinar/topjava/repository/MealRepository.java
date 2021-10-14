package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface MealRepository {
    // null if updated meal do not belong to userId - done
    Meal save(Meal meal, int userId);

    // false if meal do not belong to userId - done
    boolean delete(int id,  int userId);

    // null if meal do not belong to userId - done
    Meal get(int id, int userId);

    // ORDERED dateTime desc - done
    Collection<Meal> getAll(int userId);

  //  public <T extends Comparable<? super T>> List<MealTo> getFilterBy(T start, T stop, int userId, int caloriesPerDay);
}
