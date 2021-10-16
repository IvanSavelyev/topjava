package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.UserUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private Map<Integer, Map<Integer, Meal>> userMealsMap = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, UserUtil.USER_ID));
        MealsUtil.anotherMeals.forEach(meal -> save(meal, UserUtil.ADMIN_ID));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        //При первом обращении инициализирует мапу еды для userId, если есть то возвращает мапу;
        userMealsMap.computeIfAbsent(userId, ConcurrentHashMap::new);
        if (meal.isNew()) {
            meal.setUserId(userId);
            meal.setId(counter.incrementAndGet());
            userMealsMap.get(userId).put(meal.getId(), meal);
            log.info("save meal {} with userId {}", meal, userId);
            return meal;
        } else {
            log.info("edit meal {} with userId {}", meal, userId);
            return userMealsMap.get(userId).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete mealId {} with userId {}", id, userId);
        return userMealsMap.get(userId) != null && userMealsMap.get(userId).remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get mealId {} with userId {}", id, userId);
        return userMealsMap.get(userId).get(id).getUserId().equals(userId) ? userMealsMap.get(userId).get(id) : null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        log.info("Get all meals with userId {}", userId);
        return getByPredicate(userId, meal -> true);
    }

    public <T> List<Meal> getInTime(int userId, T start, T end) {
        log.info("Get meals with userId {} from {} to {}", userId, start, end);
        List<Meal> mealList = new ArrayList<>();
        Object object = start.getClass().getName();
        object = end.getClass();
        if(start.getClass().getName().equals(LocalDate.class.getName()) && end.getClass().getName().equals(LocalDate.class.getName()))
            return getByPredicate(userId, meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalDate(), (LocalDate)start, (LocalDate)end));
        else
            return getByPredicate(userId, meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), (LocalTime)start, (LocalTime)end));
    }

    private List<Meal> getByPredicate(int userId, Predicate<Meal> filter) {
        log.info("Get meals with userId {} with condition {}", userId, filter);
        return userMealsMap.get(userId).isEmpty() ? new ArrayList<>() :
                userMealsMap.get(userId).values().stream()
                        .filter(filter)
                        .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                        .collect(Collectors.toList());
    }
}