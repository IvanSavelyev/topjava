package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.function.Predicate;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {
    private static final Logger log = LoggerFactory.getLogger(MealService.class);

    private final MealRepository repository;

    public MealService(MealRepository repository)
    {
        log.info("Init MealService with repository {}", repository);
        this.repository = repository;
    }

    public Meal create(Meal meal, int userId) {
        log.info("Create meal {} with {} with userId", meal, userId);
        return repository.save(meal, userId);
    }

    public void delete(int id, int userId) {
        log.info("Delete meal id {} with userId {}", id, userId);
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public Meal get(int id, int userId) {
        log.info("Getting meal id {} with userId {}", id, userId);
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public Collection<Meal> getAll(int userId) {
        log.info("Get all meals with userId {}", userId);
        return repository.getAll(userId);
    }

    public void update(Meal meal, int userId) {
        log.info("Update meal {} with userId {}", meal, userId);
        checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }

    public <T> Collection<Meal> getMealsInTime(int userId, T start, T end){
        log.info("Get meals with userId {} from {} to {}", userId, start, end);
        return repository.getInTime(userId, start, end);
    }
}