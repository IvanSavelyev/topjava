package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.User;

import java.util.List;

public interface UserRepository {
    // null if not found, when updated - done
    User save(User user);
    // false if not found - done
    boolean delete(int id);
    // null if not found - done
    User get(int id);
    // null if not found - done
    User getByEmail(String email);

    List<User> getAll();
}