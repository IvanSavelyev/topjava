package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static int MEAL_USER_ID = START_SEQ + 2;
    public static int MEAL_ADMIN_ID = START_SEQ + 8;


    public static final Meal MEAL_1 = new Meal(MEAL_USER_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0, 0), "Завтрак", 500);
    public static final Meal MEAL_2 = new Meal(MEAL_USER_ID + 1, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0, 0), "Обед", 1000);
    public static final Meal MEAL_3 = new Meal(MEAL_USER_ID + 2, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0, 0), "Ужин", 500);
    public static final Meal MEAL_4 = new Meal(MEAL_USER_ID + 3, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0, 1), "Завтрак", 500);
    public static final Meal MEAL_5 = new Meal(MEAL_USER_ID + 4, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0, 1), "Обед", 1000);
    public static final Meal MEAL_6 = new Meal(MEAL_USER_ID + 5, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0, 1), "Ужин", 510);

    public static final Meal ADMIN_MEAL_1 = new Meal(MEAL_ADMIN_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0, 0), "Админ Завтрак", 510);
    public static final Meal ADMIN_MEAL_2 = new Meal(MEAL_ADMIN_ID + 1, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0, 0), "Админ Обед", 1500);
    public static final Meal ADMIN_MEAL_3 = new Meal(MEAL_ADMIN_ID + 2, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0, 0), "Админ Ужин", 510);
    public static final Meal ADMIN_MEAL_4 = new Meal(MEAL_ADMIN_ID + 3, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0, 1), "Админ Завтрак", 1500);
    public static final Meal ADMIN_MEAL_5 = new Meal(MEAL_ADMIN_ID + 4, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0, 1), "Админ Обед", 510);
    public static final Meal ADMIN_MEAL_6 = new Meal(MEAL_ADMIN_ID + 5, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0, 1), "Админ Ужин", 1500);

    public static final List<Meal> MEALS = Arrays.asList(MEAL_6, MEAL_5, MEAL_4, MEAL_3, MEAL_2, MEAL_1);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2020, Month.APRIL, 1, 10, 10, 10), "Created meal", 1000);
    }

    public static Meal getUpdate() {
        return new Meal(MEAL_USER_ID, MEAL_1.getDateTime(), "Updated meal", 1000);
    }
}
