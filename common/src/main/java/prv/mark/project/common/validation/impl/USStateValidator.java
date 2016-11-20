package prv.mark.project.common.validation.impl;

import prv.mark.project.common.validation.USState;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Implementation of {@link USState} validation annotation.
 *
 * @author mlglenn
 */
public class USStateValidator implements ConstraintValidator<USState, String> {

    private static final String[] US_STATES = {
            "AL", "AK", "AZ", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID",
            "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN",
            "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND",
            "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT",
            "VA", "WA", "WV", "WI", "WY", "DC"
    };

    public void initialize(USState state) {
    }

    /**
     * Tests whether the US State code is valid.
     * @param state The input US State Code
     * @param context {@link ConstraintValidatorContext}
     * @return boolean
     */
    public boolean isValid(String state, ConstraintValidatorContext context) {
        boolean valid = false;
        for (String s : US_STATES) {
            if (state.equals(s)) {
                valid = true;
            }
        }
        return state != null && valid;
    }
}
