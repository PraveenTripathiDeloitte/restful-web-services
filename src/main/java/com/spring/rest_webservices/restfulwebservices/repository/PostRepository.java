package com.spring.rest_webservices.restfulwebservices.repository;

import com.spring.rest_webservices.restfulwebservices.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
}
