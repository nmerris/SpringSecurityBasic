package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

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
        System.out.println("============================= UserService.saveUser, just got user.username: " + user.getUsername());

//        user.setRoles(new HashSet<>(Arrays.asList(roleRepository.findByRole("ROLE_USER"))));
        user.addRole(roleRepository.findByRole("ROLE_USER"));
        // overrode toString in Role, so should print out role string here
        System.out.println("=================== adding role to user: " + roleRepository.findByRole("ROLE_USER"));
        user.setEnabled(true);
        userRepository.save(user);
        System.out.println("=================== just saved user to repo");

    }

    public void saveAdmin(User user) {
        System.out.println("============================= UserService.saveAdmin, just got user.username: " + user.getUsername());

//        user.setRoles(new HashSet<>(Arrays.asList(roleRepository.findByRole("ROLE_USER"))));
        user.addRole(roleRepository.findByRole("ROLE_ADMIN"));
        // overrode toString in Role, so should print out role string here
        System.out.println("=================== adding role to user: " + roleRepository.findByRole("ROLE_ADMIN"));
        user.setEnabled(true);
        userRepository.save(user);
        System.out.println("=================== just saved user to repo");

    }

}
