package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.Set;

@Controller
public class MainController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @RequestMapping("/")
    public String index() {



        // manually create some users and roles for testing

        // create a user Role
        Role userRole = new Role();
        userRole.setRole("USER");
        roleRepository.save(userRole);

        // create admin Role
        Role adminRole = new Role();
        adminRole.setRole("ADMIN");
        roleRepository.save(adminRole);

        Set<Role> myRoles = new HashSet<>();
        myRoles.add(userRole);
        myRoles.add(adminRole);

        // create user with username 'jim'
        User user = new User();
        user.setEmail("email@abc.com");
        user.setEnabled(true);
        user.setFirstName("JimUser");
        user.setLastName("Jimmerson");
        user.setPassword("pass");
        user.setUsername("jim");


        // !!!!
        // FOR THE LOVE OF EVERYTHING HOLY!!!! IT SEEMS YOU MUST ATTACH A COLLECTION OF ROLES HERE, DO NOT ADD INDIVIDUALLY!!!!
        user.setRoles(myRoles);

//        user.setRoles();

        userRepository.save(user);


        // I do not believe Role is what I want to add here...???
//        user.addRole(userRole);
//        userRepository.save(user);
//
//        SSUserDetailsService ssUserDetailsService = new SSUserDetailsService(userRepository);
//
//        // this returns a spring security super special User, not the same as my User
//        UserDetails userDetails = ssUserDetailsService.loadUserByUsername(user.getUsername());
//
//        System.out.println("in controller userDetails.getUsername: " + userDetails.getUsername());

//        userRepository.save(userDetails);


//        userRole.addUser(user);
//        user.addRole(userRole);
//        userRepository.save(user);
//        user.addRole(userRole);
//        userRepository.save(user);

//        userRole.addUser(user);
//        roleRepository.save(userRole);

//
//        // create user with username 'bob'
//        User user2 = new User();
//        user2.setEmail("bobsemail@abc.com");
//        user2.setEnabled(true);
//        user2.setFirstName("BobUser");
//        user2.setLastName("Fergulson");
//        user2.setPassword("pass");
//        user2.setUsername("bob");
////        user2.addRole(userRole);
//        userRepository.save(user2);
//
//        // create user with username 'admin'
//        User user3 = new User();
//        user3.setEmail("admin@abc.com");
//        user3.setEnabled(true);
//        user3.setFirstName("AdminFirstName");
//        user3.setLastName("AdminLastName");
//        user3.setPassword("pass");
//        user3.setUsername("admin");
////        user3.addRole(userRole);
////        user3.addRole(adminRole);
//        userRepository.save(user3);
//
//




        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }


    @RequestMapping("/secure")
    public String secure(){
        return "secure";
    }


}
