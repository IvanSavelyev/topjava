package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;
@Repository
@Profile(Profiles.POSTGRES_DB)
public class JdbcMealRepositoryPostgres extends AbstractJdbcMealRepository {

    public JdbcMealRepositoryPostgres(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(meal);
        if (meal.isNew()) {
            Number newId = insertMeal.executeAndReturnKey(parameterSource);
            meal.setId(newId.intValue());
        } else {
            if (namedParameterJdbcTemplate.update("UPDATE meals " +
                    "SET description=:description, calories=:calories, date_time=:date_time " +
                    "WHERE id=:id AND user_id=:user_id", parameterSource) == 0) {
                return null;
            }
        }
        return meal;
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return jdbcTemplate.query(
                "SELECT * FROM meals WHERE user_id=?  AND date_time >=  ? AND date_time < ? ORDER BY date_time DESC",
                ROW_MAPPER, userId, startDateTime, endDateTime);
    }
}

