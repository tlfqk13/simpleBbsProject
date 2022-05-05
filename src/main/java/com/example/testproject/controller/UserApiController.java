package com.example.testproject.controller;

import com.example.testproject.model.Board;
import com.example.testproject.model.User;
import com.example.testproject.model.User;
import com.example.testproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserApiController {
    @Autowired
    private UserRepository repository;

    @GetMapping("/users")
    List<User> all(@RequestParam(required = false) String method, @RequestParam(required = false) String text) {
        List<User> users = null;
        if("query".equals(method)){
            //http://localhost:9090/api/users?method=query&text=ahqltm36
           users = repository.findByUsernameQuery(text);
        } else if("nativeQuery".equals(method)){
            //http://localhost:9090/api/users?method=query&text=ahqltm36
            users = repository.findByUsernameNativeQuery(text);
        }else{
            users=repository.findAll();
        }
        return users;
    }

    @GetMapping("/users/{id}")
    User one(@PathVariable Long id){
        return repository.findById(id).orElse(null);
    }

    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    @PutMapping("/users/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable Long id) {

        return repository.findById(id)
                .map(user -> {
                    user.setBoards(newUser.getBoards());
                    for(Board board :user.getBoards()){
                        board.setUser(user);
                    }
                    return repository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return repository.save(newUser);
                });
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {
        repository.deleteById(id);
    }
}

