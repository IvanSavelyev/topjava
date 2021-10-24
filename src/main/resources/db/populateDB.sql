DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id)
  VALUES    ('2020-01-30 10:00:00', 'Завтрак',      500,    100000),
            ('2020-01-30 13:00:00', 'Обед',         1000,   100000),
            ('2020-01-30 20:00:00', 'Ужин',         500,    100000),
            ('2020-01-31 10:00:01', 'Завтрак',      500,    100000),
            ('2020-01-31 13:00:01', 'Обед',         1000,   100000),
            ('2020-01-31 20:00:01', 'Ужин',         510,    100000),

            ('2020-01-30 10:00:00', 'Админ Завтрак',510,    100001),
            ('2020-01-30 13:00:00', 'Админ Обед',   1500,   100001),
            ('2020-01-30 20:00:00', 'Админ Ужин',   510,    100001),
            ('2020-01-31 10:00:01', 'Админ Завтрак',1500,   100001),
            ('2020-01-31 13:00:01', 'Админ Обед',   510,    100001),
            ('2020-01-31 20:00:01', 'Админ Ужин',   1500,   100001);
