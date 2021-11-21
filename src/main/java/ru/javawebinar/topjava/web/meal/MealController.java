package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.List;

@Controller
public class MealController {
    private static final Logger log = LoggerFactory.getLogger(MealController.class);

    private final MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

//    public Meal get(int id) {
//        int userId = SecurityUtil.authUserId();
//        log.info("get meal {} for user {}", id, userId);
//        return mealService.get(id, userId);
//    }
//
//    public void delete(int id) {
//        int userId = SecurityUtil.authUserId();
//        log.info("delete meal {} for user {}", id, userId);
//        mealService.delete(id, userId);
//    }

    @GetMapping("/update")
    public String editMeal(Model model, @RequestParam("id") String mealId) {
        model.addAttribute("meal", mealService.get(Integer.parseInt(mealId), SecurityUtil.authUserId()));
        return "mealForm";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") String mealId) {
        mealService.delete(Integer.parseInt(mealId), SecurityUtil.authUserId());
        return "redirect:/meals";
    }

    @PostMapping("/update")
    public String addNewMeal(@ModelAttribute("newMeal") Meal newMeal) {
        mealService.update(newMeal, SecurityUtil.authUserId());
        return "redirect:/meals";
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


//    public Meal create(Meal meal) {
//        int userId = SecurityUtil.authUserId();
//        checkNew(meal);
//        log.info("create {} for user {}", meal, userId);
//        return mealService.create(meal, userId);
//    }
//
//    public void update(Meal meal, int id) {
//        int userId = SecurityUtil.authUserId();
//        assureIdConsistent(meal, id);
//        log.info("update {} for user {}", meal, userId);
//        mealService.update(meal, userId);
//    }
//
//    /**
//     * <ol>Filter separately
//     * <li>by date</li>
//     * <li>by time for every date</li>
//     * </ol>
//     */
//    public List<MealTo> getBetween(@Nullable LocalDate startDate, @Nullable LocalTime startTime,
//                                   @Nullable LocalDate endDate, @Nullable LocalTime endTime) {
//        int userId = SecurityUtil.authUserId();
//        log.info("getBetween dates({} - {}) time({} - {}) for user {}", startDate, endDate, startTime, endTime, userId);
//
//        List<Meal> mealsDateFiltered = mealService.getBetweenInclusive(startDate, endDate, userId);
//        return MealsUtil.getFilteredTos(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
//    }
}