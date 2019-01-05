package ua.edu.ratos.security;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ua.edu.ratos.security.lti.LTIAwareAccessDeniedHandler;
import ua.edu.ratos.security.lti.LTIAwareUsernamePasswordAuthenticationFilter;
import ua.edu.ratos.security.lti.LTISecurityUtils;

/**
 * Global security config, LTI aware
 */
@Order(2)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final String profile;

    private final AuthenticatedUserDetailsService authenticatedUserDetailsService;

    private final LTISecurityUtils ltiSecurityUtils;

    @Autowired
    public SecurityConfig(final AuthenticatedUserDetailsService authenticatedUserDetailsService,
                          final LTISecurityUtils ltiSecurityUtils,
                          @NonNull final @Value("${spring.profiles.active}") String profile) {
        this.profile = profile;
        this.authenticatedUserDetailsService = authenticatedUserDetailsService;
        this.ltiSecurityUtils = ltiSecurityUtils;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // LTI Pre- bean
    @Bean
    public LTIAwareUsernamePasswordAuthenticationFilter ltiAwareUsernamePasswordAuthenticationFilter() throws Exception {
        LTIAwareUsernamePasswordAuthenticationFilter filter = new LTIAwareUsernamePasswordAuthenticationFilter(ltiSecurityUtils);
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler("/login?error"));
        return filter;
    }

    @Bean
    public FilterRegistrationBean<LTIAwareUsernamePasswordAuthenticationFilter> ltiAwareUsernamePasswordAuthenticationFilterRegistration() throws Exception {
        FilterRegistrationBean<LTIAwareUsernamePasswordAuthenticationFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(ltiAwareUsernamePasswordAuthenticationFilter());
        registration.addUrlPatterns("/login");
        registration.setName("ltiAwareUsernamePasswordAuthenticationFilter");
        registration.setOrder(1);
        registration.setEnabled(false);
        return registration;
    }

    // LTI access denied handler bean
    @Bean
    public AccessDeniedHandler ltiAwareAccessDeniedHandler() {
        return new LTIAwareAccessDeniedHandler(ltiSecurityUtils);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .addFilterBefore(ltiAwareUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .authorizeRequests()
            .antMatchers("/login*", "/access-denied*").permitAll()
            .antMatchers("/sign-up*").hasAnyRole("LTI", "LAB-ASSISTANT", "INSTRUCTOR", "DEP-ADMIN", "FAC-ADMIN", "ORG-ADMIN", "GLOBAL-ADMIN")
            .antMatchers("/student/**").hasAnyRole("STUDENT", "LAB-ASSISTANT", "INSTRUCTOR", "DEP-ADMIN", "FAC-ADMIN", "ORG-ADMIN", "GLOBAL-ADMIN")
            .antMatchers("/lab/**").hasAnyRole("LAB-ASSISTANT", "INSTRUCTOR", "DEP-ADMIN", "FAC-ADMIN", "ORG-ADMIN", "GLOBAL-ADMIN")
            .antMatchers("/instructor/**").hasAnyRole("INSTRUCTOR", "DEP-ADMIN", "FAC-ADMIN", "ORG-ADMIN", "GLOBAL-ADMIN")
            .antMatchers("/dep-admin/**").hasAnyRole("DEP-ADMIN", "FAC-ADMIN", "ORG-ADMIN", "GLOBAL-ADMIN")
            .antMatchers("/fac-admin/**").hasAnyRole("FAC-ADMIN", "ORG-ADMIN", "GLOBAL-ADMIN")
            .antMatchers("/org-admin/**").hasAnyRole("ORG-ADMIN", "GLOBAL-ADMIN")
            .antMatchers("/global-admin/**").hasRole("GLOBAL-ADMIN")
            .antMatchers("/lms/**").hasRole("LMS-USER")
                .and()
                .formLogin()
            .and()
                .headers()
                .frameOptions().disable()
            .and()
                .exceptionHandling().accessDeniedHandler(ltiAwareAccessDeniedHandler());
    }

    @Override
    public UserDetailsService userDetailsServiceBean() {
        return authenticatedUserDetailsService;
    }

    @Override
    @SuppressWarnings("SpellCheckingInspection")
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceBean()).passwordEncoder(passwordEncoder());
        if ("dev".equals(profile)) {
            auth.inMemoryAuthentication()
                    .withUser("student").password("{noop}dT09Rx06").roles("LTI", "STUDENT")
                    .and()
                    .withUser("instructor").password("{noop}dT09Rx06").roles("INSTRUCTOR")
                    .and()
                    .withUser("admin").password("{noop}dT09Rx06").roles("GLOBAL-ADMIN");
        }
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/static/**");
    }
}
