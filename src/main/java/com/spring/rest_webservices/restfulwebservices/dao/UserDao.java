package com.spring.rest_webservices.restfulwebservices.dao;

import com.spring.rest_webservices.restfulwebservices.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component
public class UserDao {

    private static int usersCount = 5;
    private static List<User> userList = new ArrayList<>();

    static {
        userList.add(new User(1, "Harry Potter", new Date()));
        userList.add(new User(2, "Hermione Granger", new Date()));
        userList.add(new User(3, "Professor Albus Dumbledore", new Date()));
        userList.add(new User(4, "Ron Weasley", new Date()));
        userList.add(new User(5, "Professor Severus Snape", new Date()));
    }

    public List<User> findAll() {
        return userList;
    }

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(++usersCount);
        }
        userList.add(user);
        return user;
    }

    public User getById(Integer id) {
        for (User user : userList) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    public User deleteUser(Integer id) {
        Iterator<User> iterator = userList.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getId() == id) {
                iterator.remove();
                return user;
            }
        }
        return null;

    }
}
