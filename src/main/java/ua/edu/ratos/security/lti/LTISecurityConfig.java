package ua.edu.ratos.security.lti;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
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
import ua.edu.ratos.dao.entity.lms.LMSOrigin;
import ua.edu.ratos.dao.repository.lms.LMSOriginRepository;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * LTI v1.1.1 security config
 */
@Slf4j
@Order(1)
@EnableWebSecurity
public class LTISecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String FALLBACK_LTI_PATH = "/lti/1p0/launch";

    private final String profile;

    private final String ltiLaunchPath;

    private final LTIConsumerDetailsService ltiConsumerDetailsService;

    private final LTIAuthenticationHandler ltiAuthenticationHandler;

    private final LMSOriginRepository lmsOriginRepository;


    @Autowired
    public LTISecurityConfig(final LTIConsumerDetailsService ltiConsumerDetailsService,
                             final LTIAuthenticationHandler ltiAuthenticationHandler,
                             final LMSOriginRepository lmsOriginRepository,
                             @NonNull final @Value("${spring.profiles.active}") String profile,
                             @NonNull final @Value("${ratos.lti.launch_path}") String ltiLaunchPath) {
        this.profile = profile;
        if (ltiLaunchPath==null || ltiLaunchPath.isEmpty()) {
            log.warn("LTI launch path is not set, fallback to default one");
            this.ltiLaunchPath = FALLBACK_LTI_PATH;
        } else {
            this.ltiLaunchPath = ltiLaunchPath;
        }
        this.ltiConsumerDetailsService = ltiConsumerDetailsService;
        this.ltiAuthenticationHandler = ltiAuthenticationHandler;
        this.lmsOriginRepository = lmsOriginRepository;
    }

    // CORS bean
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        List<String> allowedOrigins;
        if ("dev".equals(profile) || "demo".equals(profile))  {
            allowedOrigins = Collections.singletonList("*");
        } else {
            allowedOrigins = lmsOriginRepository.findAll()
                    .stream().map(LMSOrigin::getLink).collect(Collectors.toList());
        }
        configuration.setAllowedOrigins(allowedOrigins);
        configuration.setAllowedMethods(Collections.singletonList("POST"));
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
        ProtectedResourceProcessingFilter filter = new LTIAwareProtectedResourceProcessingFilter();
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
    public FilterRegistrationBean<ProtectedResourceProcessingFilter> ltiAwareProtectedResourceProcessingFilterRegistration() {
        FilterRegistrationBean<ProtectedResourceProcessingFilter> registration = new FilterRegistrationBean<>();
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
            .cors().disable()
            .antMatcher(ltiLaunchPath)
            .addFilterBefore(ltiAwareProtectedResourceProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
            .authorizeRequests()
            .anyRequest().hasAnyRole("LTI","LMS-USER")
            .and()
            .headers()
                .frameOptions().disable();
    }

}
