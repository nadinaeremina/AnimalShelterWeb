package org.top.animalshelterwebapp.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.top.animalshelterwebapp.user.User;

import java.util.Collection;
import java.util.List;

// 'UserDetails' - интерфейс, который описывает обьект пользователя, который исп-ся в SpringSecurity
// 'DbUserDetails' - имплементация 'UserDetails', работающая с БД
// базируется на агрегации БД-сущности поьзователя
// адаптер поведения пользователя, который знает SpringSecurity к модели пользователя
// который есть у нас в приложении
public class DbUserDetails implements UserDetails {

    // будет агрегировать в себе обьект юзера (пользователь из БД)
    private User user;

    public DbUserDetails(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // вызываем его логин
    @Override
    public String getUsername() {
        return user.getLogin();
    }

    // этот метод должен возвращать захэшированный пароль
    @Override
    public String getPassword() {
        return user.getPasswordHash();
    }

    // здесь нужно вернуть коллекцию ролей
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
    }
}
