package com.example.testproject.service;

import com.example.testproject.model.Role;
import com.example.testproject.model.User;
import com.example.testproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service // 비즈니스 로직 작성
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User save(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setEnabled(true);
        Role role = new Role();
        // 원래는 role_user를 검색해서 가져와야한다. roleRepository 만들어서
        role.setId(1l);
        user.getRoles().add(role);
        return userRepository.save(user);
    }
}
