package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
public class MainController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserService userService;



    @RequestMapping("/")
    public String index(Model model) {

        model.addAttribute("message", "Welcome to Spring Security Test app");

        return "index";
    }


    // displays a form to register a new user, passes a new User to the form
    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("newUser", new User());
        return "registration";
    }

    // the selected role comes in as a request param from the form, can't be null because User is preselected
    @PostMapping("/register")
    public String processRegistration(@RequestParam(value = "selectedRole") String role,
                                      @Valid @ModelAttribute("newUser") User user,
                                      BindingResult bindingResult,
                                      Model model) {

        System.out.println("####################### /register POST... incoming role String is: " + role);

        // always add the incoming user back to the model
        model.addAttribute("newUser", user);

        if(bindingResult.hasErrors()) {
            return "registration";
        }
        else {
            if(role.equals("ROLE_USER")) {
                userService.saveUser(user);
                model.addAttribute("message", "ROLE_USER account successfully created!");
            }
            else {
                userService.saveAdmin(user);
                model.addAttribute("message", "ROLE_ADMIN account successfully created!");
            }
        }

        // need this to compile, should never happen
        return "index";

    }


    @GetMapping("/userpage")
    public String userPage() {
        return "userpage";
    }




    // don't hit this route more than once per session, testing only!!
    @RequestMapping("/setuprolesandusers")
    public String manualSetup(Model model) {


        // clear the tables
        // first: delete all users,
        // then can delete all roles
        userRepository.deleteAll();
        roleRepository.deleteAll();


        // manually create some users and roles for testing

        // create a user Role
        Role userRole = new Role();
        userRole.setRole("ROLE_USER");
        roleRepository.save(userRole);

        // create admin Role
        Role adminRole = new Role();
        adminRole.setRole("ROLE_ADMIN");
        roleRepository.save(adminRole);



        // create user with username 'jim'
        User user = new User();
        user.setEmail("email@abc.com");
        user.setEnabled(true);
        user.setFirstName("JimUser");
        user.setLastName("Jimmerson");
        user.setPassword("pass");
        user.setUsername("jim");
        user.addRole(userRole); // just contains a normal 'user'
        userRepository.save(user);


        // create user with username 'bob'
        User user2 = new User();
        user2.setEmail("bobsemail@abc.com");
        user2.setEnabled(true);
        user2.setFirstName("BobUser");
        user2.setLastName("Fergulson");
        user2.setPassword("pass");
        user2.setUsername("bob");
        user2.addRole(userRole); // just contains a normal 'user'
        userRepository.save(user2);

        // create user with username 'admin'
        User user3 = new User();
        user3.setEmail("admin@abc.com");
        user3.setEnabled(true);
        user3.setFirstName("AdminFirstName");
        user3.setLastName("AdminLastName");
        user3.setPassword("pass");
        user3.setUsername("admin");

        // make a set of roles now, admin and user, and attach it to admin user
        HashSet<Role> myRoles = new HashSet<>();
        myRoles.add(userRole);
        myRoles.add(adminRole);

        user3.addRoles(myRoles);
        userRepository.save(user3);

        model.addAttribute("message", "just added 3 users and 2 roles!");

        return "index";
    }


    // this gives us the user details (?) data access object?
    @RequestMapping("/login")
    public String login(/*Principal principal*/){
        return "login";
    }


    @RequestMapping("/secure")
    public String secure(){
        return "secure";
    }


}
