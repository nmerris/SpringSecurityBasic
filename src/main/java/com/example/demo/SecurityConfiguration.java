package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private SSUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return new SSUserDetailsService(userRepository);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                // ROLE_ADMIN is NOT a special thing, it's just convention, the String here must exactly match
                // whatever string you use when you create Roles and save them to role repo
            .antMatchers("/secure").access("hasRole('ROLE_ADMIN')")
            .antMatchers("/").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
            .antMatchers("/", "/setuprolesandusers").permitAll() // so anyone can get to default route and my manual testing route
            .anyRequest().authenticated()
            .and()
            .formLogin().loginPage("/login").permitAll()
            .and()
            .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/login").permitAll().permitAll()
            .and()
            .httpBasic();

        http
            .csrf().disable();

        http
            .headers().frameOptions().disable();


//                .access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
//            .antMatchers("/admin").access("hasRole('ROLE_ADMIN')")
//            .anyRequest().authenticated()
//            .and().httpBasic();
//
//


        //        http.authorizeRequests()
//                .antMatchers("/").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
//                .antMatchers("/admin").access("hasRole('ROLE_ADMIN')")
//                .anyRequest().authenticated()
//                .and().formLogin().loginPage("/login").permitAll()
//                .and().httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(userDetailsServiceBean());

        // the SSUserDetailsService we are using NOW takes the place of this simpler in memory auth.. we need to do
        // it with SSUserDetailsService and all that jazz, to be able to keep our user and role data in dbs
//            .inMemoryAuthentication().
//            withUser("user").password("pass").roles("USER")
//
//            // to add more accounts, chain more
//            .and().withUser("userTwo").password("pass").roles("USER")
//
//            // add an admin user
//            .and().withUser("admin").password("pass").roles("ADMIN");


    }

}
