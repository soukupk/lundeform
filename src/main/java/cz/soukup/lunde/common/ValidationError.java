package cz.soukup.lunde.common;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.ConstraintViolation;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@ToString
@EqualsAndHashCode
@Getter
public class ValidationError {

    private final String fieldName;

    private final String message;

    private ValidationError(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }

    public static <T> ValidationError create(ConstraintViolation<T> constraintViolation) {
        return new ValidationError(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
    }

    public static <T> Set<ValidationError> createSet(Set<ConstraintViolation<T>> constraintViolations) {
        return constraintViolations.stream()
                .map(ValidationError::create)
                .collect(Collectors.toSet());
    }

    public static ValidationError create(String fieldName, String message) {
        return new ValidationError(fieldName, message);
    }

    public static Set<ValidationError> createSet(Map<String, String> messageByFieldName) {
        return messageByFieldName.entrySet().stream()
                .map(entry -> create(entry.getKey(), entry.getValue())).collect(Collectors.toSet());
    }

}
