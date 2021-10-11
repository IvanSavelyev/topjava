package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.model.repo.MealRepository;
import ru.javawebinar.topjava.model.repo.impl.MealRepositoryImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@WebServlet
public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    public MealRepository mealRepository;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to meals");
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        String id = req.getParameter("id");
        switch (action == null ? "all" : action) {
            case "delete":
                log.debug("servlet delete action");
                mealRepository.deleteMeal(id);
                resp.sendRedirect("meals");
                break;
            case "add":
                log.debug("servlet add action");
                req.getRequestDispatcher("/addMeal.jsp").forward(req, resp);
            case "edit":
                log.debug("servlet edit action");
                req.setAttribute("meal", mealRepository.editMeal(id));
                req.getRequestDispatcher("/addMeal.jsp").forward(req, resp);
                break;
            case "all":
                log.debug("servlet add action");
            default:
                req.setAttribute("meals", MealsUtil.filteredByStreams(mealRepository.getAllMeals(), null, null, MealsUtil.CALORIES_IN_DAYS));
                req.getRequestDispatcher("/meals.jsp").forward(req, resp);
                break;
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("servlet post method");
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id");
        Meal meal = new Meal(
                id.equals("") ? -1 : Integer.parseInt(id),
                LocalDateTime.parse(req.getParameter("dateTime")),
                req.getParameter("description"),
                Integer.parseInt(req.getParameter("calories")));
        mealRepository.addMeal(meal);
        resp.sendRedirect("meals");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }

    @Override
    public void init() throws ServletException {
        log.debug("init servlet fields");
        mealRepository = new MealRepositoryImpl();
    }
}
