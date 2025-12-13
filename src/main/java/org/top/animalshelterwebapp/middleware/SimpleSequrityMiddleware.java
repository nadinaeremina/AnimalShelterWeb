package org.top.animalshelterwebapp.middleware;

import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.logging.ErrorManager;

@Component
public class SimpleSequrityMiddleware implements Filter {

    // данный middleware проверяет наличие секрктного ключа в заголовке x-secret-key
    // если там валидный ключ - то доступ мы дадим, иначе вернуть 401 и сообщение

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain next)
            throws ServletException, IOException {

        // действия до
        HttpServletRequest req = (HttpServletRequest) request;

        if (!req.getRequestURI().contains("api")) {
            // если запрос не к API - то пропустить
            next.doFilter(request, response);
            return;
        }

        // String method = req.getMethod();
        // String url = req.getRequestURI();
        // System.out.println(method + " " + url);
        String secretKey = req.getHeader("X-Secret_Key");
        if ("QWERTY".equals(secretKey)) {
            // если ключ совпадает - дать доступ
            next.doFilter(request, response);
            return;
        }

        // иначе 401
        ErrorMessage error = new ErrorMessage("ACCESS_DENIED", "Access is denied without valid secret code");
        Gson gson = new Gson();
        String jsonError = gson.toJson(error);
        HttpServletResponse resp = (HttpServletResponse)response;
        resp.setStatus(HttpStatus.UNAUTHORIZED.value());
        resp.setHeader("Content-Type", "application/json");
        resp.getWriter().write(jsonError);

        // вызов следующего обработчика
        // next.doFilter(request, response);

        // действия после
        // HttpServletResponse resp = (HttpServletResponse)response;
        // int statusCode = resp.getStatus();
        // System.out.println();
    }
}
