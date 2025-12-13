package org.top.animalshelterwebapp.guardian;

public class GuardianNotFoundException extends Throwable {
    public GuardianNotFoundException(String message) {
        super(message);
    }
}