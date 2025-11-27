package org.top.animalshelterwebapp.animal;

import org.springframework.web.multipart.MultipartFile;

import java.time.Year;

public class AnimalCreateData {
    private Integer id;
    private String nickname;
    private String typeId;
    private String breedId;
    private Integer age;
    private Integer yearOfBirth;
    private String description;
    private String cityId;
    private Integer userId;
    private MultipartFile photo;

    public AnimalCreateData() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getDescription() {
        return description;
    }

    public Integer getAge() {
        return age;
    }

    public String getType() {
        return typeId;
    }

    public String getNickname() {
        return nickname;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getYearOfBirth() {
        return yearOfBirth;
    }

    public MultipartFile getPhoto() {
        return photo;
    }

    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
    }

    public void setAge(Integer age) {
        this.age = age;
        this.yearOfBirth = Year.now().getValue() - this.age;
    }

    public String getTypeId() {
        return typeId;
    }

    public String getBreedId() {
        return breedId;
    }

    public void setBreedId(String breedId) {
        this.breedId = breedId;
    }
}