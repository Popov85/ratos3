package ua.edu.ratos.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import ua.edu.ratos.web.exception.RatosAsyncExceptionHandler;

@EnableAsync
@Configuration
@Profile({"prod", "dev", "demo"})
public class AsyncConfig extends AsyncConfigurerSupport {

    private RatosAsyncExceptionHandler ratosAsyncExceptionHandler;

    @Autowired
    public void setRatosAsyncExceptionHandler(RatosAsyncExceptionHandler ratosAsyncExceptionHandler) {
        this.ratosAsyncExceptionHandler = ratosAsyncExceptionHandler;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return this.ratosAsyncExceptionHandler;
    }
}
