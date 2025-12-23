package org.top.animalshelterwebapp.animal;

import jakarta.persistence.*;
import org.springframework.lang.Nullable;
import org.top.animalshelterwebapp.city.City;
import org.top.animalshelterwebapp.guardian.Guardian;
import org.top.animalshelterwebapp.type.Type;
import org.top.animalshelterwebapp.user.User;

import java.time.Year;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "animals")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nickname_f", nullable = false, length = 45)
    private String nickname;

    @Column(name = "gender_f", nullable = false, length = 1)
    private String gender;

    @Column(name = "age_f", nullable = false, length = 3)
    private Integer age;

    @Column(name = "yearOfBirth_f", nullable = false, length = 4)
    private Integer yearOfBirth;

    @Lob
    @Column(name = "photo_f", columnDefinition = "MEDIUMBLOB")
    private String photo;

    @Column(name = "description_f", nullable = false, length = 200)
    private String description;

    // связь с сущностью (таблицей) опекунов
    @ManyToOne
    @JoinColumn(name = "guardian_id", nullable = false)
    private Guardian guardian;

    // связь с сущностью (таблицей) городов
    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    // связь с сущностью (таблицей) видов
    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private Type type;

    // связь с сущность (таблицей) юзеров
     @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "animal_user",
            joinColumns = @JoinColumn(name = "animal_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName="id")
    )

    // @ManyToMany(mappedBy = "animals") // Указываем, что связь управляется со стороны Author
    private @Nullable Set<User> users = new HashSet<>();


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

    public Guardian getGuardian() {
        return guardian;
    }

    public void setGuardian(Guardian guardian) {
        this.guardian = guardian;
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

    public String getGuardianName() {
        return guardian.toString();
    }

    public void setCity(City city) {
        this.city = city;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getType() {
        return type.getTitle();
    }

    public String getBreed() {
        return type.getBreed();
    }

    @Nullable
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(@Nullable User user) {
        this.users.add(user);
    }

    public void unSetUsers(@Nullable User user) {
        this.users.remove(user);
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isUser (User user) {
        if (user == null) {
            return  false;
        }
        return users.contains(user);
    }
}