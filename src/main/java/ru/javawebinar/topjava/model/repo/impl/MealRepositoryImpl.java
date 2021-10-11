package ru.javawebinar.topjava.model.repo.impl;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.repo.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class MealRepositoryImpl implements MealRepository {
    private static final Logger log = getLogger(MealRepositoryImpl.class);
    private ConcurrentMap<Integer, Meal> mealsMap = new ConcurrentHashMap<>();
    private AtomicInteger cnt = new AtomicInteger(0);

    public MealRepositoryImpl() {
        log.debug("Creating " + this);
        MealsUtil.meals.forEach(this::addMeal);
    }


    @Override
    public Collection<Meal> getAllMeals() {
        return mealsMap.values();
    }

    @Override
    public Meal editMeal(String id) {
        return mealsMap.get(Integer.parseInt(id));
    }

    @Override
    public void addMeal(Meal meal) {
        if (meal.getId() == -1) {
            meal.setId(cnt.incrementAndGet());
            mealsMap.putIfAbsent(cnt.get(), meal);
        } else {
            mealsMap.put(meal.getId(), meal);
        }

    }

    @Override
    public void deleteMeal(String id) {
        mealsMap.remove(Integer.parseInt(id));
    }
}
