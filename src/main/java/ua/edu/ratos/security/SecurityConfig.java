package ua.edu.ratos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ua.edu.ratos.security.lti.*;

/**
 * Global security config, LTI aware
 */
@Order(2)
@EnableWebSecurity
public class SecurityConfig {

    @Order(4)
    @Configuration
    public static class MainConfig extends WebSecurityConfigurerAdapter{

        @Autowired
        private AuthenticatedUserDetailsService authenticatedUserDetailsService;

        @Bean
        public PasswordEncoder passwordEncoder() {
            return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        }


        // LTI-aware: process a case when already authenticated user lacks authority
        // to reach protected with hither level security resource.
        @Bean
        public AccessDeniedHandler ltiAwareAccessDeniedHandler() {
            return new LTIAwareAccessDeniedHandler();
        }


        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .csrf().disable()
                .cors().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/login*").permitAll()
                .antMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .antMatchers("/sign-up*", "/access-denied*").permitAll()
                .antMatchers("/actuator/**", "/self-registration/**").permitAll()
                .antMatchers("/**/*.js", "/**/*.css", "/**/*.map", "/**/*.png").permitAll()
                .antMatchers("/user/**", "/student/**", "/session/**", "/info/**").hasAnyRole("LMS-USER", "STUDENT", "LAB-ASSISTANT", "INSTRUCTOR", "DEP-ADMIN", "FAC-ADMIN", "ORG-ADMIN", "GLOBAL-ADMIN")
                .antMatchers("/department/**").hasAnyRole("LAB-ASSISTANT","INSTRUCTOR", "DEP-ADMIN", "FAC-ADMIN", "ORG-ADMIN", "GLOBAL-ADMIN")
                .antMatchers("/instructor/**").hasAnyRole("INSTRUCTOR", "DEP-ADMIN", "FAC-ADMIN", "ORG-ADMIN", "GLOBAL-ADMIN")
                .antMatchers("/dep-admin/**").hasAnyRole("DEP-ADMIN", "FAC-ADMIN", "ORG-ADMIN", "GLOBAL-ADMIN")
                .antMatchers("/fac-admin/**").hasAnyRole("FAC-ADMIN", "ORG-ADMIN", "GLOBAL-ADMIN")
                .antMatchers("/org-admin/**").hasAnyRole("ORG-ADMIN", "GLOBAL-ADMIN")
                .antMatchers("/global-admin/**").hasRole("GLOBAL-ADMIN")
                .antMatchers("/lti/**").hasAnyRole("LTI")
                .antMatchers("/lms/**").hasRole("LMS-USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                .and()
                .rememberMe()
                    .key("e-ratos")
                    .userDetailsService(authenticatedUserDetailsService)
                .and()
                //.httpBasic().and()
                .headers()
                    .frameOptions().disable()
                .and()
                .exceptionHandling()
                    .accessDeniedHandler(ltiAwareAccessDeniedHandler());
        }

        @Override
        public UserDetailsService userDetailsServiceBean() {
            return authenticatedUserDetailsService;
        }
    }


    @Order(3)
    @Configuration
    public static class SessionConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        private AuthenticatedUserDetailsService authenticatedUserDetailsService;

        @Autowired
        private LTIAwarePreUsernamePasswordAuthenticationFilter ltiAwarePreUsernamePasswordAuthenticationFilter;

        @Autowired
        private LTIAwareAuthenticationSuccessHandler ltiAwareAccessSuccessHandler;

        @Autowired
        private LTIAwareAuthenticationFailureHandler ltiAwareAuthenticationFailureHandler;

        @Bean
        public FilterRegistrationBean<LTIAwarePreUsernamePasswordAuthenticationFilter> ltiPreUPFilterRegistration() throws Exception {
            FilterRegistrationBean<LTIAwarePreUsernamePasswordAuthenticationFilter> registration = new FilterRegistrationBean<>();
            registration.setFilter(ltiAwarePreUsernamePasswordAuthenticationFilter);
            registration.addUrlPatterns("/login*");
            registration.setName("ltiAwarePreUsernamePasswordAuthenticationFilter");
            registration.setOrder(1);
            registration.setEnabled(false);
            return registration;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception{
            auth.userDetailsService(userDetailsServiceBean());
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .csrf().disable()
                .cors().disable()
                .requestMatchers()
                .antMatchers(HttpMethod.POST, "/login*")
                .and()
                .addFilterBefore(ltiAwarePreUsernamePasswordAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login*").permitAll()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .successHandler(ltiAwareAccessSuccessHandler)
                    .failureHandler(ltiAwareAuthenticationFailureHandler)
                .and()
                .rememberMe()
                    .key("e-ratos")
                    .userDetailsService(authenticatedUserDetailsService);
        }

        @Override
        public UserDetailsService userDetailsServiceBean() {
            return this.authenticatedUserDetailsService;
        }
    }
}
