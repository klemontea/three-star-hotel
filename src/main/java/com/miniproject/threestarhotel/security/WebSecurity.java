//package com.miniproject.threestarhotel.security;
//
//import com.miniproject.threestarhotel.repository.UserRepos;
//import com.miniproject.threestarhotel.service.UserService;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//
//@EnableWebSecurity
//public class WebSecurity extends WebSecurityConfigurerAdapter {
//
//    private final UserService userDetailsService;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//    private final UserRepos userRepos;
//
//    public WebSecurity(UserService userDetailsService,
//                       BCryptPasswordEncoder bCryptPasswordEncoder,
//                       UserRepos userRepository) {
//
//        this.userDetailsService = userDetailsService;
//        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//        this.userRepos = userRepository;
//    }
//
//    protected void configure(HttpSecurity http) throws Exception {
//
//        http.csrf().disable().authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/")
//                .permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .addFilter(new AuthorizationFilter(authenticationManager()));
//    }
//
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//        auth.userDetailsService(userDetailsService)
//                .passwordEncoder(bCryptPasswordEncoder);
//    }
//}
//
