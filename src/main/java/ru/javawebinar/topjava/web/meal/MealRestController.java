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
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    private final MealService service;

    public MealRestController(MealService service) {
        log.info("Creating MealRestController with {}", service);
        this.service = service;
    }

    public Meal get(int id) {
        log.info("Get meal id {} for auth user {}", id, SecurityUtil.authUserId());
        return service.get(id, SecurityUtil.authUserId());
    }

    public void delete(int id) {
        log.info("Delete meal id {} for auth user {}", id, SecurityUtil.authUserId());
        service.delete(id, SecurityUtil.authUserId());
    }

    public List<MealTo> getAll() {
        log.info("Get all meals for auth user {}", SecurityUtil.authUserId());
        return MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay());
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

    public List<MealTo> getMealsInTime(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        startDate = startDate == null ? LocalDate.MIN : startDate;
        endDate = endDate == null ? LocalDate.MAX : endDate;
        startTime = startTime == null ? LocalTime.MIN : startTime;
        endTime = endTime == null ? LocalTime.MAX : endTime;
        log.info("Get meals with userId {} from date {} to {} and time from {} to {}", SecurityUtil.authUserId(), startDate, endDate, startTime, endTime);
        List<Meal> allMealsInDate = service.getInDate(SecurityUtil.authUserId(), startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX));
        return MealsUtil.getFilteredTos(allMealsInDate, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
    }
}