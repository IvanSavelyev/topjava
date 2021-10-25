package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void delete() throws Exception {
        service.delete(MEAL_USER_ID, USER_ID);
        assertMatch(service.getAll(USER_ID), MEAL_6, MEAL_5, MEAL_4, MEAL_3, MEAL_2);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        service.delete(0, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteAnother() throws Exception {
        service.delete(MEAL_USER_ID, ADMIN_ID);
    }

    @Test
    public void create() throws Exception {
        Meal newMeal = MealTestData.getNew();
        Meal createFromService = service.create(newMeal, USER_ID);
        newMeal.setId(createFromService.getId());
        assertMatch(newMeal, createFromService);
        assertMatch(service.getAll(USER_ID), createFromService, MEAL_6, MEAL_5, MEAL_4, MEAL_3, MEAL_2, MEAL_1);
    }

    @Test
    public void get() throws Exception {
        Meal actual = service.get(MEAL_USER_ID, USER_ID);
        assertMatch(actual, MEAL_1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(0, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getAnother() throws Exception {
        service.get(MEAL_USER_ID, ADMIN_ID);
    }

    @Test
    public void update() throws Exception {
        Meal updated = MealTestData.getUpdate();
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL_USER_ID, USER_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() throws Exception {
        service.update(MEAL_1, ADMIN_ID);
    }

    @Test
    public void getAll() throws Exception {
        assertMatch(service.getAll(USER_ID), MEALS);
    }

    @Test
    public void getBetween() throws Exception {
        assertMatch(service.getBetweenInclusive(
                LocalDate.of(2020, Month.JANUARY, 30),
                LocalDate.of(2020, Month.JANUARY, 30), USER_ID), MEAL_3, MEAL_2, MEAL_1);
    }

    @Test
    public void duplicateDateTimeCreate() throws Exception {
        Meal duple = getNew();
        duple.setDateTime(service.get(MEAL_USER_ID, USER_ID).getDateTime());
        assertThrows(DataAccessException.class, () ->
                service.create(duple, USER_ID));
    }
}
