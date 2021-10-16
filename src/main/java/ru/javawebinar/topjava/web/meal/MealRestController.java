package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(MealRestController.class);

    private final MealService service;

    public MealRestController(MealService service){
        log.info("Creating MealRestController with {}", service);
        this.service = service;
    }

    public Meal get(int id){
        log.info("Get meal id {} for auth user {}", id, SecurityUtil.authUserId());
        return service.get(id, SecurityUtil.authUserId());
    }

    public void delete(int id) {
        log.info("Delete meal id {} for auth user {}", id, SecurityUtil.authUserId());
        service.delete(id, SecurityUtil.authUserId());
    }

    public Collection<MealTo> getAll() {
        log.info("Get all meals for auth user {}", SecurityUtil.authUserId());
        return MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()),  SecurityUtil.authUserCaloriesPerDay());
    }

    public Meal create(Meal meal) {
        log.info("Create meal {} for auth user {}", meal, SecurityUtil.authUserId());
        checkNew(meal);
        return service.create(meal, SecurityUtil.authUserId());
    }

    public void update(Meal meal, int id) {
        log.info("Update meal {} with id {} for auth user {}", meal, id, SecurityUtil.authUserId());
        assureIdConsistent(meal, id);
        service.update(meal, SecurityUtil.authUserId());
    }

    public Collection<MealTo> getMealsInTime(int userId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime){
        log.info("Get meals with userId {} from date {} to {} and time from {} to {}", userId, startDate, endDate, startTime, endTime);
        List<Meal> mealsFilerByDate = (List<Meal>) service.getMealsInTime(userId, startDate, endDate);
        List<Meal> mealsFilerByTime = (List<Meal>) service.getMealsInTime(userId, startDate, endDate);
        return service.getMealsInTime(userId, start, end);
    }
}