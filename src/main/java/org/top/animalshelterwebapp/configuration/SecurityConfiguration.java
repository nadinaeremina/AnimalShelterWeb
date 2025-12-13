package org.top.animalshelterwebapp.configuration;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.top.animalshelterwebapp.security.DbUserDetailsService;
import org.top.animalshelterwebapp.security.LockoutAuthenticationFailureHandler;
import org.top.animalshelterwebapp.user.UserRepository;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    // Константы для настройки lockout-блокировок
    private static final int MAX_FAILED_ATTEMPTS = 3;
    private static final int LOCKOUT_DURATION_MINUTES = 15;

    private  final UserRepository userRepository;
    private final DataSource dataSource;
    private final LockoutAuthenticationFailureHandler lockoutFailureHandler;

    public SecurityConfiguration(UserRepository userRepository, DataSource dataSource,
                                 LockoutAuthenticationFailureHandler lockoutFailureHandler) {
        this.userRepository = userRepository;
        this.dataSource = dataSource;
        this.lockoutFailureHandler = lockoutFailureHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // настройка конфига защиты
        http.authorizeHttpRequests(r ->
                        r.requestMatchers("/", "/api/**", "/webjars/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/airport").authenticated()
                                .requestMatchers("/airport/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                ).formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/")
                        .failureHandler(lockoutFailureHandler)
//                        .successHandler(authenticationSuccessHandler())
                )
                .csrf(AbstractHttpConfigurer::disable);

        // сборка конфига защиты
        return http.build();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//
//        // настройка конфига защиты
//        // запросы, которые нам необходимо будет защищать, либо разрешать
//        http.authorizeHttpRequests(r ->
//                // будем требовать аутентификацию
//                // разрешим доступ ко всем либам "webjars" (например, к 'bootstrap')
//                // здесь доступ разрешен всем авторизованным пользователям
//                r.requestMatchers("/myCard/**", "webjars/**").authenticated()
//
//                // здесь задаем еще и метод
//                // .requestMatchers(HttpMethod.GET, "/myCard/**", "webjars/**").authenticated()
//
//                        // здесь доступ только для админов
//                        // .requestMatchers("/sorted_animals").hasRole("ADMIN")
//                        // значит в 'GrantedAuthority' должно быть указано ROLE_ADMIN
//
//                        // всему остальному мы будем разрешать доступ
//                        .anyRequest().permitAll()
//                // разрешать зайти на форму логина
//        ).formLogin(form -> form.permitAll().successForwardUrl("/index"));
//
//        // сборка конфига защиты
//        return http.build();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public AuthenticationSuccessHandler authenticationSuccessHandler() {
//        return new AuthenticationSuccessHandler() {
//            @Override
//            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//                                                org.springframework.security.core.Authentication authentication)
//                    throws IOException, ServletException {
//                // Сбрасываем счетчик неудачных попыток при успешной аутентификации
//                String username = authentication.getName();
//                if (username != null) {
//                    userRepository.resetFailedAttempts(username);
//                }
//                response.sendRedirect("/");
//            }
//        };
//    }

    // Сервисы для внедрения компонентов БД-сервисов в использование 'Spring Security'
    @Bean
    public UserDetailsService userDetailsService() {
        return new DbUserDetailsService(userRepository);
    }

    // Зависимости, необходимые для работы Spring Security
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // это по сути UserDetailsService
    @Bean
    public UserDetailsManager users(HttpSecurity http) throws Exception {
        // Создаем AuthenticationManagerBuilder без использования deprecated .and()
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());

        // Добавляем authentication provider отдельно
        authBuilder.authenticationProvider(daoAuthenticationProvider());

        AuthenticationManager authenticationManager = authBuilder.build();
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setAuthenticationManager(authenticationManager);
        return jdbcUserDetailsManager;
    }

    // сервис для работы с пользователями, реализованный в виде in-memory заглушки
    // встроенные юзеры
//    @Bean
//    public UserDetailsService userDetailsService() {
//        // подготовим тестовых пользователй через встроенные типы
//        UserDetails user = User.builder()
//                .username("user")
//                .password("qwerty")
//                .passwordEncoder(passwordEncoder()::encode)
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("qwerty")
//                .passwordEncoder(passwordEncoder()::encode)
//                .roles("ADMIN")
//                .build();
//        // подготовка тестовой имплементации UserDetailsService
//        return new InMemoryUserDetailsManager(user, admin);
//    }
}
