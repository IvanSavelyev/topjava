package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.function.Predicate;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private ConfigurableApplicationContext applicationContext;
    private MealRestController mealRestController;

    @Override
    public void init() {
        applicationContext = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        mealRestController = applicationContext.getBean(MealRestController.class);
    }

    @Override
    public void destroy() {
        applicationContext.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")),
                SecurityUtil.authUserId()
        );
        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        if (meal.isNew())
            mealRestController.create(meal);
        else
            mealRestController.update(meal, meal.getId());
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                mealRestController.delete(id);
                response.sendRedirect("meals");
                break;
            case "filter":
                String startDate = request.getParameter("startDate");
                String stopDate = request.getParameter("stopDate");
                String startTime = request.getParameter("startTime");
                String stopTime = request.getParameter("stopTime");
                //request.setAttribute("meals", mealRestController.getMealsInTime());
                /*
                Predicate<Meal> timeFilter = getFilterState(request);
                request.setAttribute("meals", MealsUtil.filterByPredicate(
                        mealRestController.getAll(),
                        SecurityUtil.authUserCaloriesPerDay(),
                        timeFilter));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
                 */
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mealRestController.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                request.setAttribute("meals", mealRestController.getAll());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

    private Predicate<Meal> getFilterState(HttpServletRequest request) {
        String startDate = request.getParameter("startDate");
        String stopDate = request.getParameter("stopDate");
        String startTime = request.getParameter("startTime");
        String stopTime = request.getParameter("stopTime");
        if ((!startDate.equals("") && !stopDate.equals("")) && (startTime.equals("") && stopTime.equals(""))) {
            return meal -> DateTimeUtil.isBetweenHalfOpen(
                    meal.getDateTime(),
                    LocalDate.parse(startDate).atTime(LocalTime.MIN),
                    LocalDate.parse(stopDate).atTime(LocalTime.MAX));
        } else if (!startDate.equals("") && !stopDate.equals("") && !startTime.equals("") && !stopTime.equals("")) {
            return meal -> DateTimeUtil.isBetweenHalfOpen(
                    meal.getDateTime(),
                    LocalDate.parse(startDate).atTime(LocalTime.parse(startTime)),
                    LocalDate.parse(stopDate).atTime(LocalTime.parse(stopTime).plusNanos(1)));
        } else if ((startDate.equals("") && stopDate.equals("")) && (!startTime.equals("") && !stopTime.equals(""))) {
            return meal -> DateTimeUtil.isBetweenHalfOpen(
                    meal.getTime(),
                    LocalTime.parse(startTime),
                    LocalTime.parse(stopTime).plusNanos(1));
        } else if ((!startDate.equals("") || !stopDate.equals("")) && ((!startTime.equals("") && !stopTime.equals("")))) {
            return meal -> DateTimeUtil.isBetweenHalfOpen(
                    meal.getDateTime(),
                    LocalDate.parse(!startDate.equals("") ? startDate : stopDate).atTime(LocalTime.parse(startTime)),
                    LocalDate.parse(startDate).atTime(LocalTime.parse(stopTime)));
        } else {
            return meal -> true;
        }
    }
}
