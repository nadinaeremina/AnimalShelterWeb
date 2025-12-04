package org.top.animalshelterwebapp.animal;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.EnumSet;

// этот класс позволяет получить критерий для выборки
public class WorkerSpecification implements Specification<Animal> {
    private static final EnumSet<Operation> NULL_OPERATIONS = EnumSet.of(Operation.NULL, Operation.NOT_NULL);
    private final CriteriaData criteriaData;

    public WorkerSpecification(CriteriaData criteriaData) {
        this.criteriaData = criteriaData;
    }

    @Override
    public Predicate toPredicate(Root<Animal> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        checkCriteria(criteriaData);

        // получаем наш критерий
        Operation operation = criteriaData.getOperation();

//      // Path<Object> является "одиночным" указателем на поле,
//      // а для работы с несколькими полями требуется их явное указание или сбор в структуру данных.
        // получаем выражение
        Path<Object> expression = root.get(criteriaData.getField());
        String value = criteriaData.getValue();
        String field = criteriaData.getField();
        switch (operation) {
            case NULL -> {
                return criteriaBuilder.isNull(expression);
            }
            case NOT_NULL -> {
                return criteriaBuilder.isNotNull(expression);
            }
            case EQ -> {
                return criteriaBuilder.equal(expression, value);
            }
            case LIKE -> {
                String likeString = "%" + value + "%";
                return criteriaBuilder.like(expression.as(String.class), likeString);
            }
            case GT -> {
                // если такое поле фигурирует в нашей записи
                if (field.equals(criteriaData.getField())) {
                    // парсим, чтобы получить целое число
                    return  criteriaBuilder.gt(expression.as(Integer.class), Integer.parseInt(value));
                    // а если такое
                } else if ("birthDate".equals(criteriaData.getField())) {
                    return  criteriaBuilder.greaterThan(expression.as(LocalDateTime.class), LocalDateTime.parse(value));
                }
            }
            case LT -> {
                // если такое поле фигурирует в нашей записи
                if (field.equals(criteriaData.getField())) {
                    Predicate lt = criteriaBuilder.lt(expression.as(Integer.class), Integer.parseInt(value));
                    // парсим, чтобы получить целое число
                    return  criteriaBuilder.lt(expression.as(Integer.class), Integer.parseInt(value));
                    // а если такое
                } else if ("birthDate".equals(criteriaData.getField())) {
                    return criteriaBuilder.lessThan(expression.as(LocalDateTime.class), LocalDateTime.parse(value));
                }
            }
        }
        return null;
    }

    private void checkCriteria(CriteriaData criteriaData) {
        if (criteriaData == null) {
            throw new IllegalArgumentException("CriteriaData is null");
        }

        if (StringUtils.isBlank(criteriaData.getField())) {
            throw new IllegalArgumentException("Field must be not null");
        }

        Operation operation = criteriaData.getOperation();
        if (operation == null) {
            throw new IllegalArgumentException("Operation must be not null");
        }

        if (!NULL_OPERATIONS.contains(operation) && criteriaData.getValue() == null) {
            throw new IllegalArgumentException("Value must be not null");
        }
    }
}
