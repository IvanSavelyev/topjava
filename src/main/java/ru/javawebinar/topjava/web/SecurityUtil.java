package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.util.UserUtil;

public class SecurityUtil {
    private static final Logger log = LoggerFactory.getLogger(SecurityUtil.class);

    public static int id;

    public static int authUserId() {
        return id;
    }

    public static int authUserCaloriesPerDay() {
        return UserUtil.DEFAULT_CALORIES_PER_DAY;
    }

    public static void setAuthUserId(int userId) {
        id = userId;
    }
}