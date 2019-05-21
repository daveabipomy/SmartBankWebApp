package com.example.springsecurity.config;

import com.example.springsecurity.service.impl.LogInServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

@Autowired
public LogInServiceImp logInServiceImp;

    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    public SecurityConfiguration(AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/index","/").permitAll()
                .antMatchers("/about","/service","/location","/contact","/customer","/testhomepage").permitAll()
                .antMatchers("/afterLogin/manager/**","/manager/**").hasAuthority("Manager")
                .antMatchers("/afterLogin/customer/**","/customer/**").hasAuthority("Customer")
                .antMatchers("/afterLogin/teller/**","/teller/**").hasAuthority("Teller")
                .anyRequest().authenticated()
                .and()

                .formLogin()

                .loginPage("/login")
                .loginProcessingUrl("/login")

                .successHandler(authenticationSuccessHandler)

                .usernameParameter("userName")
                .passwordParameter("password")
                .and().logout()
                .logoutUrl("/app-logout")
                .logoutSuccessUrl("/index")
                .and().exceptionHandling()
                .accessDeniedPage("/error");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        auth.userDetailsService(logInServiceImp).passwordEncoder(bCryptPasswordEncoder);
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**","/templates/**");
    }




















//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    @Autowired
//    private DataSource dataSource;
//
//    @Value("${spring.queries.users-query}")
//    private String usersQuery;
//
//    @Value("${spring.queries.roles-query}")
//    private String rolesQuery;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth)
//            throws Exception {
//        auth.
//                jdbcAuthentication()
//                .usersByUsernameQuery(usersQuery)
//                .authoritiesByUsernameQuery(rolesQuery)
//                .dataSource(dataSource)
//                .passwordEncoder(bCryptPasswordEncoder);
//        System.out.println("roleeeeeeeeee "+usersQuery);
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        http.
//                authorizeRequests()
//                .antMatchers("/").permitAll()
//                .antMatchers("/login").permitAll()
//                .antMatchers("/printCard").permitAll()
//        .antMatchers("/index").permitAll()
//
//                .antMatchers("/about").permitAll()
//                .antMatchers("/register").permitAll()
//                .antMatchers("/contact").permitAll()
//                .antMatchers("/location").permitAll()
//                .antMatchers("/service").permitAll()
//                .antMatchers("/transaction").permitAll()
//                .antMatchers("/test").permitAll()
//                .antMatchers("/customer").permitAll()
//                .antMatchers("/registration").permitAll()
//                .antMatchers("/manager/**").hasAnyRole("Manager").anyRequest()
//                .authenticated().and()
//                .csrf().disable()
//                .formLogin()
//                .loginPage("/login").failureUrl("/login?error=true")
//                .defaultSuccessUrl("/afterLogin/manager")
//                .usernameParameter("email")
//                .passwordParameter("password")
//                .and().logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .logoutSuccessUrl("/index").and().exceptionHandling()
//                .accessDeniedPage("/access-denied");
//    }
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web
//                .ignoring()
//                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
//    }

}
