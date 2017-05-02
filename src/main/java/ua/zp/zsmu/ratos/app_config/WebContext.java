package ua.zp.zsmu.ratos.app_config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;
import ua.zp.zsmu.ratos.app_config.convertors.SchemeConverter;
import ua.zp.zsmu.ratos.app_config.convertors.StudentConverter;

import java.util.List;

/**
 * Created by Andrey on 23.03.2017.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "ua.zp.zsmu.ratos.learning_session.controller" })
public class WebContext extends WebMvcConfigurerAdapter implements ApplicationContextAware {

        private ApplicationContext applicationContext;

        public void setApplicationContext(ApplicationContext applicationContext) {
                this.applicationContext = applicationContext;
        }

        @Override
        public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
                converters.add(new MappingJackson2HttpMessageConverter(new HibernateAwareObjectMapper()));
                super.configureMessageConverters(converters);
        }

        @Bean
        public ViewResolver viewResolver() {
                ThymeleafViewResolver resolver = new ThymeleafViewResolver();
                resolver.setTemplateEngine(templateEngine());
                resolver.setCharacterEncoding("UTF-8");
                resolver.setContentType("text/html; charset=UTF-8");
                return resolver;
        }

        @Bean
        public TemplateEngine templateEngine() {
                SpringTemplateEngine engine = new SpringTemplateEngine();
                engine.setEnableSpringELCompiler(true);
                engine.setTemplateResolver(templateResolver());
                return engine;
        }

        private ITemplateResolver templateResolver() {
                SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
                resolver.setApplicationContext(applicationContext);
                resolver.setPrefix("/WEB-INF/views/");
                resolver.setSuffix(".html");
                resolver.setCacheable(false);
                resolver.setTemplateMode(TemplateMode.HTML);
                resolver.setCharacterEncoding("UTF-8");
                return resolver;
        }

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
                //super.addResourceHandlers(registry);
                registry.addResourceHandler("/img/**").addResourceLocations("/WEB-INF/img/");
                registry.addResourceHandler("/css/**").addResourceLocations("/WEB-INF/css/");
                registry.addResourceHandler("/js/**").addResourceLocations("/WEB-INF/js/");
        }

        @Override
        public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
                //super.addArgumentResolvers(argumentResolvers);
                argumentResolvers.add(studentConverter());
        }

        @Bean
        public StudentConverter studentConverter() {
                return new StudentConverter();
        }

        @Override
        public void addFormatters(FormatterRegistry registry) {
                //super.addFormatters(registry);
                registry.addConverter(schemeConverter());

        }

        @Bean
        public SchemeConverter schemeConverter() {
               return new SchemeConverter();
        }
}
