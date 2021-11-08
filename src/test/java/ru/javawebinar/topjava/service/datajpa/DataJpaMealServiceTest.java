package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.UserTestData.USER_MATCHER;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaMealServiceTest extends AbstractMealServiceTest {

    @Test
    public void getWithUser() {
        Meal meal = service.getWithUser(MEAL1_ID, USER_ID);
        MEAL_MATCHER.assertMatch(meal, meal1);
        USER_MATCHER.assertMatch(meal.getUser(), UserTestData.user);
    }

    @Test
    public void getNotFoundUser() {
        assertThrows(NotFoundException.class, () -> service.getWithUser(1, USER_ID));
    }
}
