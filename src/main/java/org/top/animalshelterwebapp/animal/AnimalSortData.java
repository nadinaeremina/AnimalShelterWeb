package org.top.animalshelterwebapp.animal;

import org.springframework.web.multipart.MultipartFile;
import org.top.animalshelterwebapp.city.City;
import org.top.animalshelterwebapp.type.Type;

public class AnimalSortData {
    private Type type;
    private Integer age;
    private City city;

    public AnimalSortData() {
    }

    public City getCity() {
        return city;
    }

    public Integer getAge() {
        return age;
    }

    public Type getType() {
        return type;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
