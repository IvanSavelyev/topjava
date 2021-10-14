package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository;
import ru.javawebinar.topjava.util.UserUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class UserServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    private UserRepository repository;

    @Override
    public void init() throws ServletException {
        repository = new InMemoryUserRepository();
        SecurityUtil.setAuthUserId(UserUtil.USER);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //log.debug("forward to users");
        //request.getRequestDispatcher("/users.jsp").forward(request, response);
        String action = request.getParameter("action");
        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                repository.delete(id);
                response.sendRedirect("users");
                break;
            case "create":
            case "update":
                if ("update".equals(action))
                    request.setAttribute("user", repository.get(getId(request)));
                request.getRequestDispatcher("/userForm.jsp").forward(request, response);
                break;
            case "select":
                SecurityUtil.setAuthUserId(getId(request));
                response.sendRedirect("meals");
                break;
            case "all":
            default:
                log.info("getAll");
                if(repository.getAll().isEmpty())
                    SecurityUtil.setAuthUserId(0);
                request.setAttribute("users", repository.getAll());
                request.setAttribute("auth", SecurityUtil.authUserId());
                request.getRequestDispatcher("/users.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        User user = new User(id.isEmpty() ? null : Integer.valueOf(id),
                request.getParameter("name"),
                request.getParameter("email"),
                request.getParameter("password")
        );
        //User("Admin", "email", "password", Role.ADMIN))
        log.info(user.isNew() ? "Create {}" : "Update {}", user);
        repository.save(user);
        response.sendRedirect("users");
    }
}
