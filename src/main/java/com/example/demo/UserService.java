package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    RoleRepository roleRepository;
    
    // autowire a constructor???
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Long countByEmail(String email) {
        return userRepository.countByEmail(email);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void saveUser(User user) {
        user.setRoles();
    }

}
