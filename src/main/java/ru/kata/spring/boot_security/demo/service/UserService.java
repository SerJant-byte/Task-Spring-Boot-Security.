package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
        User addUser( User user, Set<String> role);
        List<User> getUsers();
        User updateUser(String name,
                        String password,
                        Long id,
                        Set<String> role);
        void deleteUser(Long id);
        User getUser(Long id);
    }

