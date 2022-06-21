package com.spring.rest_webservices.restfulwebservices.controller;

import com.spring.rest_webservices.restfulwebservices.dao.UserDao;
import com.spring.rest_webservices.restfulwebservices.entity.User;
import com.spring.rest_webservices.restfulwebservices.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserDao userDao;

    @GetMapping(path = "/find-all")
    public List<User> findAll() {
        return userDao.findAll();
    }

    /*
    EntityModel will return the link that contains details
    of other user as well along with the specified user details
     */
    @GetMapping(path = "/{id}")
    public EntityModel<User> findUserById(@PathVariable Integer id) {
        User user = userDao.getById(id);
        if (user == null) {
            throw new UserNotFoundException("No user found with given id: " + id);
        }

        EntityModel<User> userEntityModel = EntityModel.of(user);

        /*
        this will build link for get all users
         */
        WebMvcLinkBuilder linkToUsers =
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findAll());
        userEntityModel.add(linkToUsers.withRel("all-users"));
        return userEntityModel;
    }

    @PostMapping(path = "/add")
    public ResponseEntity<Object> addUser(@Valid @RequestBody User user) {
        User savedUser = userDao.save(user);

        /* to return the location where new user created
        this will basically return the id os newly created user
        in header
         */
        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("/{id}").
                buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(path = "delete/{id}")
    public void deleteUserById(@PathVariable Integer id) {
        User user = userDao.deleteUser(id);
        if (user == null) {
            throw new UserNotFoundException("No user found with given id: " + id);
        }
    }
}
