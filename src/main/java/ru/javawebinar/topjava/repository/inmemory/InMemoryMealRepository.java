package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);
    private final Integer USER = 1, ADMIN = 2;

    {
        MealsUtil.meals.forEach(meal -> save(meal, USER));
        //MealsUtil.meals.forEach(this::save);
        MealsUtil.anotherMeals.forEach(meal -> save(meal, ADMIN));
    }

    @Override
    public Meal save(Meal meal,  int userId) {
        if (meal.isNew()) {
            meal.setUserId(userId);
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return /*repository.get(meal.getId()).isNobodysMeal() ? null :*/ repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id,  int userId) {
        return repository.get(id).getUserId().equals(userId) && repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        return repository.get(id).getUserId().equals(userId) ? repository.get(id) : null;
    }

    @Override
    //public Collection<Meal> getAll(String sortDirect, Predicate<Meal> filter) {
    public Collection<Meal> getAll(int userId){
        return repository.values().stream().
                sorted(Comparator.comparing(Meal::getDateTime, Comparator.nullsLast(Comparator.reverseOrder()))).
                collect(Collectors.toList());
    }
}

