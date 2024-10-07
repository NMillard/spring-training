package com.mjukvare.controllers.requests.validation.validator;

import com.mjukvare.controllers.requests.validation.AllowedName;
import com.mjukvare.domain.BlacklistedNamesStore;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


/**
 * This class illustrates a simple validation on a String.
 * Notice that the validator takes a dependency on a service. This allows us to perform complex
 * validations where external values are required.
 */
public class AllowedNameValidator implements ConstraintValidator<AllowedName, String> {

    private final BlacklistedNamesStore blackListedNamesStore;

    public AllowedNameValidator(BlacklistedNamesStore blackListedNamesStore) {
        this.blackListedNamesStore = blackListedNamesStore;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !blackListedNamesStore.getBlacklistedNames().contains(value);
    }
}

