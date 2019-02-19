package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.model.Role.ROLE_USER;

public class UserUtil {
    public static final List<User> USERS = Arrays.asList(
            new User("Alex", "alex@rambler.ru", "111", ROLE_USER, ROLE_USER),
            new User("Ivan", "ivan@rambler.ru", "111", ROLE_USER, ROLE_USER),
            new User("Serega", "serega@rambler.ru", "111", ROLE_USER, ROLE_USER),
            new User("Vera", "vera@rambler.ru", "111", ROLE_USER, ROLE_USER),
            new User("Kesha", "kesha@rambler.ru", "111", ROLE_USER, ROLE_USER)
    );
}
