package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
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

import static org.slf4j.LoggerFactory.getLogger;

@WebServlet
public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    public MealRepository mealRepository;

    @Override
    public void init() throws ServletException {
        log.debug("init servlet fields");
        mealRepository = new MealRepositoryImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to meals");
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        switch (action == null ? "all" : action) {
            case "delete":
                log.debug("servlet delete action");
                mealRepository.delete(Integer.parseInt(req.getParameter("id")));
                resp.sendRedirect("meals");
                break;
            case "add":
                log.debug("servlet forward to addMeal form");
                req.getRequestDispatcher("/addMeal.jsp").forward(req, resp);
            case "edit":
                log.debug("servlet forward to addMeal form");
                req.setAttribute("meal", mealRepository.edit(Integer.parseInt(req.getParameter("id"))));
                req.getRequestDispatcher("/addMeal.jsp").forward(req, resp);
                break;
            case "all":
                log.debug("servlet get all meal");
            default:
                req.setAttribute("meals", MealsUtil.filteredByStreams(mealRepository.getAll(),null, null, MealsUtil.CALORIES_IN_DAYS));
                req.getRequestDispatcher("/meals.jsp").forward(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("servlet add/edit action (post method)");
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id");
        Meal meal = new Meal(
                id.equals("") ? null : Integer.parseInt(id),
                LocalDateTime.parse(req.getParameter("dateTime")),
                req.getParameter("description"),
                Integer.parseInt(req.getParameter("calories")));
        mealRepository.add(meal);
        resp.sendRedirect("meals");
    }
}
