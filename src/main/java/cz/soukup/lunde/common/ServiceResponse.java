package cz.soukup.lunde.common;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Collections;
import java.util.Set;

@Getter
@ToString
@EqualsAndHashCode
public class ServiceResponse<T> {

    private final boolean success;

    private final Set<ValidationError> validationErrors;

    private final T model;

    private ServiceResponse(boolean success, Set<ValidationError> validationErrors, T model) {
        this.success = success;
        this.validationErrors = validationErrors;
        this.model = model;
    }

    public static <T> ServiceResponse createSuccessResponse(T model) {
        return new ServiceResponse(true, Collections.emptySet(), model);
    }

    public static ServiceResponse createFailureResponse(Set<ValidationError> validationErrors) {
        return new ServiceResponse(false, validationErrors, null);
    }

}