package org.top.animalshelterwebapp.animal;

public enum Operation {
    NULL,
    NOT_NULL,
    // равно
    EQ,
    // ищем по шаблону
    LIKE,
    // больше
    GT,
    // больше, либо равно
    GE,
    // меньше
    LT,
    // меньше, либо равно
    LE
}
