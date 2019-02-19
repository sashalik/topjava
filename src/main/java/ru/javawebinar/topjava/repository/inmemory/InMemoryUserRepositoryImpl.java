package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.UserUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private Map<Integer, User> userRepository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);

    {
        UserUtil.USERS.forEach(this::save);
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);

        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            userRepository.put(user.getId(), user);
            return user;
        }
        // treat case: update, but absent in storage
        return userRepository.computeIfPresent(user.getId(), (id, oldUser) -> user);

        //return user;
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.values().stream().filter(user -> user.getEmail().equals(email)).findFirst().get();
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        userRepository.remove(id);
        return true;
    }

    @Override
    public User get(int id) {
        return userRepository.get(id);
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(userRepository.values()).stream().sorted(Comparator.comparing(user -> user.getName() )).collect(Collectors.toList());
    }
}
