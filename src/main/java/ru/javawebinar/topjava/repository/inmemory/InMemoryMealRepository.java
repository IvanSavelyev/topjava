package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.UserUtil;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);

    private Map<Integer, Map<Integer, Meal>> usersMealsMap = new ConcurrentHashMap<>();

    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, UserUtil.USER_ID));
        MealsUtil.anotherMeals.forEach(meal -> save(meal, UserUtil.ADMIN_ID));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Map<Integer, Meal> userMealsMap = usersMealsMap.computeIfAbsent(userId, key -> new ConcurrentHashMap<>());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            userMealsMap.put(meal.getId(), meal);
            log.info("save meal {} with userId {}", meal, userId);
            return meal;
        } else {
            log.info("edit meal {} with userId {}", meal, userId);
            return userMealsMap.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> userMealMap = usersMealsMap.get(userId);
        log.info("delete mealId {} with userId {}", id, userId);
        return userMealMap != null && userMealMap.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> userMealMap = usersMealsMap.get(userId);
        log.info("get mealId {} with userId {}", id, userId);
        return userMealMap != null ? userMealMap.get(id) : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("Get all meals with userId {}", userId);
        return getByPredicate(userId, meal -> true);
    }

    public List<Meal> getByPredicate(int userId, Predicate<Meal> filter) {
        log.info("Get meals with userId {} with condition {}", userId, filter);
        Map<Integer, Meal> userMealMap = usersMealsMap.computeIfAbsent(userId, key -> new ConcurrentHashMap<>());
        if (!userMealMap.isEmpty()) {
            return userMealMap.values().stream()
                    .filter(filter)
                    .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Meal> getInDate(int userId, LocalDateTime startDate, LocalDateTime stopDate) {
        return getByPredicate(userId, meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDateTime(), startDate, stopDate));
    }
}