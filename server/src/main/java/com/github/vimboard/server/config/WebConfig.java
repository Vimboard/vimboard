package com.github.vimboard.server.config;

import com.github.vimboard.starter.VimboardBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class WebConfig {

    @EnableWebMvc
    public static class WebMvcConfig implements WebMvcConfigurer {

        private final VimboardBean vimboardBean;

        @Autowired
        public WebMvcConfig(VimboardBean vimboardBean) {
            this.vimboardBean = vimboardBean;
        }

        @Bean
        public LocaleChangeInterceptor localeChangeInterceptor() {
            LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
            lci.setParamName("lang");
            return lci;
        }

        @Bean
        public LocaleResolver localeResolver() {
            SessionLocaleResolver slr = new SessionLocaleResolver();
            Locale locale = Locale.ENGLISH;
            //Locale locale = new Locale("ru", "RU");
            slr.setDefaultLocale(locale);
            return slr;
        }

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(localeChangeInterceptor());
        }

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry
                .addResourceHandler("/**")
                .addResourceLocations(
                        "file:" + vimboardBean.getWww(),
                        "classpath:/static/");
        }
    }

    @EnableWebSecurity
    public static class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .authorizeRequests()
                    .antMatchers("/admin.php/**").hasRole("ADMIN")
                    .antMatchers("/mod.php/**").hasRole("MOD")
                    .and()
                .formLogin();
        }
    }

    @Bean
    public UserDetailsService userDetailsService() throws Exception {
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username("admin").password("admin123").roles("USER", "MOD", "ADMIN").build());
        manager.createUser(users.username("mod").password("mod123").roles("USER", "MOD").build());
        manager.createUser(users.username("user").password("user123").roles("USER").build());
        return manager;
    }
}
