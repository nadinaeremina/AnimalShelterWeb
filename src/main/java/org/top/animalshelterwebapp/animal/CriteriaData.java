package org.top.animalshelterwebapp.animal;

public class CriteriaData {
    private String field;
    private Operation operation;
    private String value;

    public CriteriaData(String value, Operation operation, String field) {
        this.value = value;
        this.operation = operation;
        this.field = field;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Operation getOperation() {
        return operation;
    }

    public String getField() {
        return field;
    }

    public String getValue() {
        return value;
    }
}
