package com.spring.rest_webservices.restfulwebservices.controller;

import com.spring.rest_webservices.restfulwebservices.entity.Post;
import com.spring.rest_webservices.restfulwebservices.entity.User;
import com.spring.rest_webservices.restfulwebservices.exception.UserNotFoundException;
import com.spring.rest_webservices.restfulwebservices.repository.PostRepository;
import com.spring.rest_webservices.restfulwebservices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/post")
public class PostController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping(path = "/user/{id}")
    public List<Post> getPostsOfUser(@PathVariable Integer id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("No user found with given id: " + id);
        }

        return userOptional.get().getPosts();
    }

    @PostMapping(path = "/add/user/{id}")
    public ResponseEntity<Object> addPostForUser(@RequestParam Integer id, @Valid @RequestBody Post post) {
        Optional<User> userOptional = userRepository.findById(id);

        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("No user found with given id: " + id);
        }

        User user = userOptional.get();

        post.setUser(user);
        postRepository.save(post);
        /* to return the location where new user created
        this will basically return the id os newly created user
        in header
         */
        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("/{id}").
                buildAndExpand(post.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
}
