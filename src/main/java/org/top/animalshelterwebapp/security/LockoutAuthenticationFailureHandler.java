package org.top.animalshelterwebapp.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.top.animalshelterwebapp.user.UserRepository;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Обработчик неудачных попыток аутентификации с поддержкой lockout-блокировок
 */
@Component
public class LockoutAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final UserRepository userRepository;

    // Константы для настройки lockout-блокировок (должны совпадать с SecurityConfiguration)
    private static final int MAX_FAILED_ATTEMPTS = 3;
    private static final int LOCKOUT_DURATION_MINUTES = 15;

    public LockoutAuthenticationFailureHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        String username = request.getParameter("username");

        if (username != null && !username.trim().isEmpty()) {
            // Увеличиваем счетчик неудачных попыток
            //userRepository.incrementFailedAttempts(username);

            // Получаем обновленную информацию о пользователе
            var userOpt = userRepository.findByLogin(username);
            if (userOpt.isPresent()) {
                var user = userOpt.get();

                // Если достигли максимального количества попыток, блокируем аккаунт
                if (user.getFailedAttempts() >= MAX_FAILED_ATTEMPTS) {
                    LocalDateTime lockoutUntil = LocalDateTime.now().plusMinutes(LOCKOUT_DURATION_MINUTES);
                    //userRepository.lockUser(username, lockoutUntil);

                    // Перенаправляем на страницу входа с сообщением о блокировке
                    response.sendRedirect("/login?error=locked&lockoutUntil=" + lockoutUntil);
                    return;
                }
            }
        }

        // Обычная обработка неудачной аутентификации
        response.sendRedirect("/login?error=true");
    }
}
