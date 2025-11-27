package org.top.animalshelterwebapp.animal;

public class AnimalNotFoundException extends Throwable {
    public AnimalNotFoundException(String message) {
        super(message);
    }
}