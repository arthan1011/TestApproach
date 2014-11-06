package org.arthan

import spock.lang.Specification

/**
 * Created by artur.shamsiev on 31.10.2014.
 */
class HelloSpock extends Specification {
    def "length of spock and friends"() {
        expect:
            print("hello!")
            3 > 2
    }
}
