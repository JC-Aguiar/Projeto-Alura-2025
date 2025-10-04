package br.com.alura.projeto.util;

import org.springframework.data.domain.ExampleMatcher;

public class ExampleMatcherUtil {

    /// Create a restrictive example-matcher. This means that all fields must match
    /// in order to be considered acceptable.
    public final static ExampleMatcher MATCHER_ALL = ExampleMatcher
        .matchingAll()
        .withIgnoreNullValues()
        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
        .withIgnoreCase();

    /// Create a flexible example-matcher. This means that at last one field must match
    /// in order to be considered acceptable.
    public final static ExampleMatcher MATCHER_ANY = ExampleMatcher
        .matchingAny()
        .withIgnoreNullValues()
        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
        .withIgnoreCase();

    /// Fabric method in order to create custom example-matchers base on the existing ones
    public static ExampleMatcher createCustomMatcher(boolean matchAll) {
        return matchAll ? MATCHER_ALL : MATCHER_ANY;
    }

}
