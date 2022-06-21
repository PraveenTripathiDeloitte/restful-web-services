package com.spring.rest_webservices.restfulwebservices.controller;

import com.spring.rest_webservices.restfulwebservices.dao.UserDao;
import com.spring.rest_webservices.restfulwebservices.entity.User;
import com.spring.rest_webservices.restfulwebservices.exception.UserNotFoundException;
import com.spring.rest_webservices.restfulwebservices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("jpa/user")
public class UserControllerJPA {

    @Autowired
    UserDao userDao;

    @Autowired
    UserRepository userRepository;

    @GetMapping(path = "/find-all")
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /*
    EntityModel will return the link that contains details
    of other user as well along with the specified user details
     */
    @GetMapping(path = "/{id}")
    public EntityModel<User> findUserById(@PathVariable Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException("No user found with given id: " + id);
        }

        EntityModel<User> userEntityModel = EntityModel.of(user.get());

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
        User savedUser = userRepository.save(user);

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
        userRepository.deleteById(id);

    }
}
