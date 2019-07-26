package ua.edu.ratos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
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

    private final AuthenticatedUserDetailsService authenticatedUserDetailsService;

    private final LTISecurityUtils ltiSecurityUtils;

    @Autowired
    public SecurityConfig(final AuthenticatedUserDetailsService authenticatedUserDetailsService,
                          final LTISecurityUtils ltiSecurityUtils) {
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

    // Process a case when already authenticated user lacks authority
    // to reach protected with hither level security resource.
    // LTI-aware impl.
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
            .antMatchers("/login*", "/sign-up*", "/access-denied*").permitAll()
            .antMatchers("/actuator/**", "/self-registration/**").permitAll()
            .antMatchers("/student/**","/info/**").hasAnyRole("LMS-USER", "STUDENT", "LAB-ASSISTANT", "INSTRUCTOR", "DEP-ADMIN", "FAC-ADMIN", "ORG-ADMIN", "GLOBAL-ADMIN")
            .antMatchers("/department/**").hasAnyRole("LAB-ASSISTANT","INSTRUCTOR", "DEP-ADMIN", "GLOBAL-ADMIN")
            .antMatchers("/instructor/**").hasAnyRole("INSTRUCTOR", "DEP-ADMIN", "GLOBAL-ADMIN")
            .antMatchers("/dep-admin/**").hasAnyRole("DEP-ADMIN", "FAC-ADMIN", "ORG-ADMIN", "GLOBAL-ADMIN")
            .antMatchers("/fac-admin/**").hasAnyRole("FAC-ADMIN", "ORG-ADMIN", "GLOBAL-ADMIN")
            .antMatchers("/org-admin/**").hasAnyRole("ORG-ADMIN", "GLOBAL-ADMIN")
            .antMatchers("/global-admin/**").hasRole("GLOBAL-ADMIN")
            .antMatchers("/lti/**").hasAnyRole("LTI")
            .antMatchers("/lms/**").hasRole("LMS-USER")
                .and()
                .formLogin()
                    .loginPage("/login")
                .and()
                .httpBasic() // Disable for production
            .and()
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

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/static/**");
    }
}
