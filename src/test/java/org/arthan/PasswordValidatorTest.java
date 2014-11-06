package org.arthan;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junitparams.JUnitParamsRunner.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.fest.assertions.Assertions;

/**
 * Created by artur.shamsiev on 02.10.2014.
 */
@RunWith(JUnitParamsRunner.class)
public class PasswordValidatorTest {
    private Object[] parametersForLengthValidation() {
        return $(
                $("validLengthPassword", true),
                $("tooSrt", false),
                $("", false)
        );
    }
    private Object[] parametersForDigitsValidation() {
        return $(
                $("iwer8kkj9", true),
                $("kdjrkkjiiyow", false),
                $("9998887424344", true),
                $("kkkkkkkkkkkkkkkk0kkkkkkkkkkk", true)
        );
    }

    /**
     * Пароль должен содержать 7 и больше символов
     * @param password
     * @param validationResult
     * @throws Exception
     */
    @Test
    @Parameters(method = "parametersForLengthValidation")
    public void passwordShouldBeAtLeast7CharactersLong(String password, boolean validationResult) throws Exception {
        Assertions.assertThat(PasswordValidator.validLength(password))
                .isEqualTo(validationResult);
        assertEquals("The result of password validation should be false if the password has less than 7 characters\n" +
                        "and result of password validation should be true if the password has at least 7 characters\n" +
                        "password = " + password,
                PasswordValidator.validLength(password), validationResult
        );
    }

    /**
     * Пароль не должен быть null
     * @throws Exception
     */
    @Test
    public void passwordShouldNotBeNull() throws Exception {
        try {
            PasswordValidator.validatePassword(null);
            fail("Method validatePassword should throw IllegalArgumentException\n if its call parameter is null " +
                    "but no Exception was thrown");
        } catch (IllegalArgumentException e) {

        } catch (Exception e) {
            fail("Method validatePassword should throw IllegalArgumentException\nif its call parameter is null " +
                    "but it throws " + e.toString());
        }
    }

    /**
     * Пароль должен содеражать цифры
     * @param password
     * @param validationResult
     * @throws Exception
     */
    @Test
    @Parameters(method = "parametersForDigitsValidation")
    public void passwordShouldContainDigits(String password, boolean validationResult) throws Exception {
        assertEquals("The result of password validation should be false if the password has no digits\n" +
                "and result of password validation should be true if the password has at least 1 digit\n" +
                "password = " + password,
                PasswordValidator.hasDigits(password), validationResult);
    }

}
