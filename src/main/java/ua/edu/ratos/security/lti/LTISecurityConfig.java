package ua.edu.ratos.security.lti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth.provider.OAuthProcessingFilterEntryPoint;
import org.springframework.security.oauth.provider.filter.ProtectedResourceProcessingFilter;
import org.springframework.security.oauth.provider.nonce.InMemoryNonceServices;
import org.springframework.security.oauth.provider.token.InMemoryProviderTokenServices;
import org.springframework.security.oauth.provider.token.OAuthProviderTokenServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import ua.edu.ratos.dao.repository.lms.LMSOriginRepository;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * LTI v1.1.1 security config
 */
@Order(1)
@EnableWebSecurity
public class LTISecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment environment;

    @Value("${ratos.lti.1p0.properties.launch.path}")
    private String ltiLaunchPath;

    @Autowired
    private LMSOriginRepository lmsOriginRepository;

    @Autowired
    private LTIConsumerDetailsService ltiConsumerDetailsService;

    @Autowired
    private LTIAuthenticationHandler ltiAuthenticationHandler;

    @Autowired
    private LTISecurityUtils ltiSecurityUtils;

    // CORS bean
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        List<String> allowedOrigins;
        if ("dev".equals(this.environment.getProperty("spring.profiles.active")))  {
            allowedOrigins = Arrays.asList("*");
        } else {
            allowedOrigins = lmsOriginRepository.findAll()
                    .stream().map(o -> o.getLink()).collect(Collectors.toList());
        }
        configuration.setAllowedOrigins(allowedOrigins);
        configuration.setAllowedMethods(Arrays.asList("POST"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/lti/**/launch", configuration);
        return source;
    }

    // OAuth/LTI 1.0 beans
    @Bean
    public OAuthProviderTokenServices oauthProviderTokenServices() {
        return new InMemoryProviderTokenServices();
    }

    @Bean
    public ProtectedResourceProcessingFilter ltiAwareProtectedResourceProcessingFilter() {
        ProtectedResourceProcessingFilter filter =
                new LTIAwareProtectedResourceProcessingFilter(ltiSecurityUtils);
        filter.setConsumerDetailsService(ltiConsumerDetailsService);
        filter.setAuthHandler(ltiAuthenticationHandler);
        filter.setTokenServices(oauthProviderTokenServices());
        filter.setAuthenticationEntryPoint(new OAuthProcessingFilterEntryPoint());
        filter.setNonceServices(new InMemoryNonceServices());
        // Fails if OAuth params are not included
        filter.setIgnoreMissingCredentials(false);
        return filter;
    }

    @Bean
    public FilterRegistrationBean ltiAwareProtectedResourceProcessingFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(ltiAwareProtectedResourceProcessingFilter());
        registration.addUrlPatterns(ltiLaunchPath);
        registration.setName("ltiAwareProtectedResourceProcessingFilter");
        registration.setOrder(1);
        // Use it only in the security chain
        registration.setEnabled(false);
        return registration;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .cors()
            .and()
            .antMatcher(ltiLaunchPath)
            .addFilterBefore(ltiAwareProtectedResourceProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
            .authorizeRequests()
            .anyRequest().hasRole("LTI")
            .and()
            .headers()
                .frameOptions().disable();
    }

}
