package starbattle.server.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterBeanConfig {

        @Bean
        public FilterRegistrationBean<ClientIdFilter> usernameFilterBean() {
            ClientIdFilter userFilter=new ClientIdFilter();
            final FilterRegistrationBean<ClientIdFilter> reg = new FilterRegistrationBean<>(userFilter);

            // ************************************************
            // Configure here which paths require authentication
            reg.addUrlPatterns("/example/one");
            reg.addUrlPatterns("/users");
            reg.addUrlPatterns("/users/{id}");
            // ************************************************

            reg.setOrder(1);   //defines filter execution order
            return reg;
        }
}
