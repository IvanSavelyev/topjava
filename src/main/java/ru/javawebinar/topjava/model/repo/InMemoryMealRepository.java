package ru.javawebinar.topjava.model.repo;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = getLogger(InMemoryMealRepository.class);
    private ConcurrentMap<Integer, Meal> mealsMap = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    public InMemoryMealRepository() {
        log.debug("Creating meal repository");
        MealsUtil.meals.forEach(this::add);
    }

    @Override
    public Collection<Meal> getAll() {
        return mealsMap.values();
    }

    @Override
    public Meal get(int id) {
        return mealsMap.get(id);
    }

    @Override
    public Meal add(Meal meal) {
        if (meal.getId() == null) {
            meal.setId(counter.incrementAndGet());
            mealsMap.putIfAbsent(counter.get(), meal);
            return null;
        } else {
            mealsMap.computeIfPresent(meal.getId(), (key, value) -> meal);
            return meal;
        }
    }

    @Override
    public void delete(int id) {
        mealsMap.remove(id);
    }
}
