package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
@RequestMapping("/meals")
public class MealController {
    private static final Logger log = LoggerFactory.getLogger(MealController.class);

    private final MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @GetMapping("/update")
    public String edit(Model model, @RequestParam("id") String mealId) {
        model.addAttribute("meal", mealService.get(Integer.parseInt(mealId), SecurityUtil.authUserId()));
        return "mealForm";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") String mealId) {
        mealService.delete(Integer.parseInt(mealId), SecurityUtil.authUserId());
        return "redirect:/meals";
    }

    @PostMapping("/create")
    public String create(@RequestParam("dateTime") String dateTime, @RequestParam("description") String description, @RequestParam("calories") String calories) {
        Meal meal = new Meal(LocalDateTime.parse(dateTime), description, Integer.parseInt(calories));
        mealService.create(meal, SecurityUtil.authUserId());
        return "redirect:/meals";
    }

    @PostMapping("/update")
    public String update(@RequestParam("dateTime") String dateTime, @RequestParam("description") String description,
                         @RequestParam("calories") String calories, @RequestParam("id") String mealId) {
        Meal meal = new Meal(LocalDateTime.parse(dateTime), description, Integer.parseInt(calories));
        meal.setId(Integer.parseInt(mealId));
        mealService.update(meal, SecurityUtil.authUserId());
        return "redirect:/meals";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));
        return "mealForm";
    }

    @GetMapping("/filter")
    public String filter(Model model,
                         @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
                         @RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime) {
        List<Meal> mealsDateFiltered = mealService.getBetweenInclusive(
                DateTimeUtil.parseLocalDate(startDate), DateTimeUtil.parseLocalDate(endDate), SecurityUtil.authUserId());
        model.addAttribute("meals",
                MealsUtil.getFilteredTos(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(),
                        DateTimeUtil.parseLocalTime(startTime), DateTimeUtil.parseLocalTime(endTime)));
        return "meals";
    }



}