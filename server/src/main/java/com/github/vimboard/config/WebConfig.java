package com.github.vimboard.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;
import javax.sql.DataSource;

@Configuration
public class WebConfig {

    @EnableWebMvc
    public static class WebMvcConfig implements WebMvcConfigurer {

        private final VimboardProperties vimboardProperties;

        @Autowired
        public WebMvcConfig(VimboardProperties vimboardProperties) {
            this.vimboardProperties = vimboardProperties;
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
                        "file:" + vimboardProperties.getWww(),
                        "classpath:/static/");
        }
    }

    @EnableWebSecurity
    public static class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        private final DataSource dataSource;

        @Autowired
        public WebSecurityConfig(DataSource dataSource) {
            this.dataSource = dataSource;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth
                .jdbcAuthentication()
                    .dataSource(dataSource)
                    .usersByUsernameQuery("SELECT username, password, enabled FROM vmb.sec_users_by_username(?)")
                    .authoritiesByUsernameQuery("SELECT username, authority FROM vmb.sec_authorities_by_username(?)")
                    .passwordEncoder(passwordEncoder())
            ;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
//                .csrf()
//                    .and()
                .authorizeRequests()
                    .antMatchers("/admin.php/**").hasRole("ADMIN")
                    .antMatchers("/mod.php/**").hasRole("MOD")
                    .and()
                .formLogin()
//                    .loginPage("/my-login-page")
            ;
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }
}
