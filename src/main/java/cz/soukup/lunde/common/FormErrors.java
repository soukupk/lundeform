package cz.soukup.lunde.common;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.*;

@ToString
@EqualsAndHashCode
public class FormErrors {

    private final Map<String, List<String>> errors;

    private FormErrors(Map<String, List<String>> errors) {
        this.errors = errors;
    }

    public static FormErrors create(Map<String, List<String>> errors) {
        return new FormErrors(errors);
    }

    public static FormErrors create(Set<ValidationError> validationErrors) {
        Map<String, List<String>> errors = new HashMap<>();
        for (ValidationError validationError : validationErrors) {
            List<String> messages = errors.get(validationError.getFieldName());

            if (messages == null) {
                messages = new ArrayList<>();
                errors.put(validationError.getFieldName(), messages);
            }
            messages.add(validationError.getMessage());
        }

        return new FormErrors(errors);
    }

    public static Object createEmpty() {
        return new FormErrors(Collections.emptyMap());
    }

    public List<String> errors(String fieldName) {
        List<String> messages = errors.get(fieldName);

        if (messages == null) {
            return Collections.emptyList();
        }

        return messages;
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

}
