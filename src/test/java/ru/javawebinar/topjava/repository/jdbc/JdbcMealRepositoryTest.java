package ru.javawebinar.topjava.repository.jdbc;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class JdbcMealRepositoryTest {

    @Autowired
    private JdbcMealRepository jdbcMealRepository;

    @Test
    public void save() {
        Meal meal = getNew();
        jdbcMealRepository.save(meal, USER_ID);
        Assert.assertNotNull(jdbcMealRepository.get(START_SEQ + 14, USER_ID));
    }

    @Test
    public void delete() {
        Assert.assertTrue(jdbcMealRepository.delete(MEAL_USER_ID, USER_ID));
    }

    @Test
    public void get() {
        Meal meal = jdbcMealRepository.get(MEAL_USER_ID, USER_ID);
        Assert.assertNotNull(meal);
    }

    @Test
    public void getAll() {
        Assert.assertArrayEquals(jdbcMealRepository.getAll(USER_ID).toArray(), MEALS.toArray());
    }

    @Test
    public void getBetweenHalfOpen() {
        Assert.assertArrayEquals(jdbcMealRepository.getBetweenHalfOpen(
                LocalDate.of(2020, Month.JANUARY, 30).atTime(LocalTime.MIN),
                LocalDate.of(2020, Month.JANUARY, 31).atTime(LocalTime.MAX), USER_ID).toArray(), MEALS.toArray());
    }
}