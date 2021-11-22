package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping("/meals")
public class JspMealController extends AbstractMealController {
    private static final Logger log = LoggerFactory.getLogger(JspMealController.class);

    @GetMapping("/update")
    public String edit(Model model, @RequestParam("id") String mealId) {
        model.addAttribute("meal", super.get(Integer.parseInt(mealId)));
        return "mealForm";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") String mealId) {
        super.delete(Integer.parseInt(mealId));
        return "redirect:/meals";
    }

    @PostMapping("/create")
    public String create(@RequestParam("dateTime") String dateTime, @RequestParam("description") String description, @RequestParam("calories") String calories) {
        Meal meal = new Meal(LocalDateTime.parse(dateTime), description, Integer.parseInt(calories));
        super.create(meal);
        return "redirect:/meals";
    }

    @PostMapping("/update")
    public String update(@RequestParam("dateTime") String dateTime, @RequestParam("description") String description,
                         @RequestParam("calories") String calories, @RequestParam("id") String mealId) {
        Meal meal = new Meal(LocalDateTime.parse(dateTime), description, Integer.parseInt(calories));
        super.update(meal, Integer.parseInt(mealId));
        return "redirect:/meals";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), "", 1000));
        return "mealForm";
    }

    @GetMapping("/filter")
    public String filter(Model model,
                         @RequestParam(value = "startDate") String startDate, @RequestParam(value = "endDate") String endDate,
                         @RequestParam(value = "startTime") String startTime, @RequestParam(value = "endTime") String endTime) {

        if (startDate.isEmpty() && endDate.isEmpty() && startTime.isEmpty() && endTime.isEmpty()) {
            return "redirect:/meals";
        }

        model.addAttribute("meals", super.getBetween(
                parseLocalDate(startDate), parseLocalTime(startTime),
                parseLocalDate(endDate), parseLocalTime(endTime)));
        return "meals";
    }
}