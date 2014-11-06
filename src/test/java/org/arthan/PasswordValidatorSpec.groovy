package org.arthan

import spock.lang.Specification

/**
 * Created by artur.shamsiev on 31.10.2014.
 */
class PasswordValidatorSpec extends Specification {
    def "password with less than 7 symbols are not allowed"() {
        given:
            PasswordValidator passValid = new PasswordValidator()
        when:
            boolean validated = passValid.validatePassword("ej3io")
        then:
            !validated
    }

    def "password with 7 and more symbols are allowed"(String password) {
        expect:
            PasswordValidator.validatePassword(password)
        where:
            password << ["alloedPass" ,"kjdkfj7er", "333333000"]
    }
}
