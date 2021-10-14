package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {
    private static final Logger log = LoggerFactory.getLogger(SecurityUtil.class);

    public static int ID;

    public SecurityUtil() {
        log.debug("Creating authUser equals 1");
        SecurityUtil.setAuthUserId(1);
    }

    public static int authUserId() {
        return ID;
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }

    public static void setAuthUserId(int userId) {
        ID = userId;
    }
}