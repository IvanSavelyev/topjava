package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        log.info("Init UserService with repository {}", repository);
        this.repository = repository;
    }

    public User create(User user) {
        log.info("Create user {}", user);
        return repository.save(user);
    }

    public void delete(int id) {
        log.info("Delete user with id {}", id);
        checkNotFoundWithId(repository.delete(id), id);
    }

    public User get(int id) {
        log.info("Getting user with id {}", id);
        return checkNotFoundWithId(repository.get(id), id);
    }

    public User getByEmail(String email) {
        log.info("Get user with email {}", email);
        return checkNotFound(repository.getByEmail(email), "email=" + email);
    }

    public List<User> getAll() {
        log.info("Get all users");
        return repository.getAll();
    }

    public void update(User user) {
        log.info("Update user {}", user);
        checkNotFoundWithId(repository.save(user), user.getId());
    }
}