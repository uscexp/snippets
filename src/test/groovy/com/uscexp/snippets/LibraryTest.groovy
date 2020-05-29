/*
 * This Spock specification was generated by the Gradle 'init' task.
 */
package com.uscexp.snippets

import spock.lang.Specification

class LibraryTest extends Specification {
    def "someLibraryMethod returns true"() {
        setup:
        def lib = new Library()

        when:
        def result = lib.someLibraryMethod()

        then:
        result == true
    }
}
