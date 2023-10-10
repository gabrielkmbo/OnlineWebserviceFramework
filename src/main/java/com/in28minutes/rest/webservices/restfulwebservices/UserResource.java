package com.in28minutes.rest.webservices.restfulwebservices;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserResource {

    private UserDaoService service;
    public UserResource(UserDaoService service)
    {
        this.service = service;
    }
    //GET /users
    @GetMapping(path = "/users")
    public List<User> retrieveAllUsers()
    {
        return service.findAll();
    }

    @GetMapping(path = "/users/{id}")
    public User retrieveUser(@PathVariable int id)
    {
        return service.findOne(id);
    }

    //POST /users
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user)
    {
        User savedUser = service.save(user);
        // /users/4 => /users/{id}, user.getId
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(null).build();
    }

    @DeleteMapping(path = "/users/{id}")
    public void deleteUser(@PathVariable int id)
    {
        service.deleteById(id);
    }
}
