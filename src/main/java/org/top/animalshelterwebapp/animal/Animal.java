package org.top.animalshelterwebapp.animal;

import jakarta.persistence.*;
import org.top.animalshelterwebapp.city.City;
import org.top.animalshelterwebapp.type.Type;
import org.top.animalshelterwebapp.user.User;

import java.sql.Blob;
import java.time.Year;

@Entity
@Table(name = "animals")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nickname_f", nullable = false, length = 45)
    private String nickname;

    @Column(name = "age_f", nullable = false, length = 3)
    private Integer age;

    @Column(name = "yearOfBirth_f", nullable = false, length = 4)
    private Integer yearOfBirth;

    @Lob
    @Column(name = "photo_f", columnDefinition = "MEDIUMBLOB")
    private String photo;

    @Column(name = "description_f", nullable = false, length = 200)
    private String description;

    // связь с сущностью (таблицей) пользователей
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // связь с сущностью (таблицей) городов
    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    // связь с сущностью (таблицей) видов
    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private Type type;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public Integer getAge() {
        return age;
    }

    public String getDescription() {
        return description;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getYearOfBirth() {
        return yearOfBirth;
    }

    public void setAge(Integer age) {
        this.age = age;
        this.yearOfBirth = Year.now().getValue() - this.age;
    }

    public City getCity() {
        return city;
    }

    public String getTypeTitle() {
        return type.getTitle();
    }

    public String getTypeBreed() {
        return type.getBreed();
    }

    public String getCityTitle() {
        return city.getTitle();
    }

    public String getUserName() {
        return user.toString();
    }

    public void setCity(City city) {
        this.city = city;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}