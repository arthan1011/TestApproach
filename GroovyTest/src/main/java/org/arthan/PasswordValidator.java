package org.arthan;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;

/**
 * Created by artur.shamsiev on 02.10.2014.
 */
public class PasswordValidator {

    private final static int MINIMUM_PASSWORD_LENGTH = 7;

    public static boolean validatePassword(String password) {
        Preconditions.checkArgument(password != null);
        if (!validLength(password)) {
            return false;
        }
        // Не содержит ни одной цифры
        if (!hasDigits(password)) {
            return false;
        }
        return true;
    }

    @VisibleForTesting
    static boolean hasDigits(String password) {
        return password.matches(".*\\d.*");
    }

    @VisibleForTesting
    static boolean validLength(String password) {
        return !(password.length() < MINIMUM_PASSWORD_LENGTH);
    }
}
