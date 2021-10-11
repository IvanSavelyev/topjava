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
        log.debug("Creating meal repository");
        MealsUtil.meals.forEach(this::add);
    }

    @Override
    public Collection<Meal> getAll() {
        return mealsMap.values();
    }

    @Override
    public Meal edit(Integer id) {
        return mealsMap.get(id);
    }

    @Override
    public Meal add(Meal meal) {
        if (meal.getId() == null) {
            meal.setId(cnt.incrementAndGet());
            mealsMap.putIfAbsent(cnt.get(), meal);
        } else {
            mealsMap.computeIfPresent(meal.getId(), (key, value) -> meal);
        }
        return meal;
    }

    @Override
    public void delete(Integer id) {
        mealsMap.remove(id);
    }
}
