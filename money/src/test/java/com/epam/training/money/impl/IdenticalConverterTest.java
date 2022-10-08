package com.epam.training.money.impl;

import com.epam.training.money.ConversionRate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Currency;
import java.util.stream.Stream;

class IdenticalConverterTest {

    private static final Currency USD_CURRENCY = Currency.getInstance("USD");
    private static final Currency HUF_CURRENCY = Currency.getInstance("HUF");

    private ConversionRate underTest;

    @BeforeEach
    void setUp() {
        underTest = new IdenticalConverter();
    }

    @Test
    void testCanConvertShouldReturnTrueWhenGivenSameCurrencies() {
        // Given
        // When
        final boolean actual = underTest.canConvert(USD_CURRENCY, USD_CURRENCY);
        // Then
        Assertions.assertTrue(actual);
    }

    @Test
    void testCanConvertShouldReturnFalseWhenGivenDifferentCurrencies() {
        // Given
        // When
        final boolean actual = underTest.canConvert(USD_CURRENCY, HUF_CURRENCY);
        // Then
        Assertions.assertFalse(actual);
    }

    @Test
    void testCanConvertShouldReturnFalseWhenGivenTargetCurrencyAsNull() {
        // Given
        // When
        final boolean actual = underTest.canConvert(USD_CURRENCY, null);
        // Then
        Assertions.assertFalse(actual);
    }

    @Test
    void testCanConvertShouldReturnFalseWhenGivenOriginalCurrencyAsNull() {
        // Given
        // When
        final boolean actual = underTest.canConvert(null, USD_CURRENCY);
        // Then
        Assertions.assertFalse(actual);
    }

    @Test
    void testConvertShouldReturnSameValueThatGiven() {
        // Given
        // When
        final double actual = underTest.convert(1);
        // Then
        Assertions.assertEquals(1, actual);
    }

    /**
     * All other test methods which test the {@link IdenticalConverter#canConvert} method
     * can be replaced by this parameterized test because it covers same test cases.
     */
    @ParameterizedTest
    @MethodSource("canConvertTestData")
    public void testCanConvert(final Currency from, final Currency target, final boolean expectedResult) {
        // Given
        // When
        final boolean actual = underTest.canConvert(from, target);
        // Then
        Assertions.assertEquals(actual, expectedResult);
    }

    private static Stream<Arguments> canConvertTestData() {
        return Stream.of(
                Arguments.of(null, null, false),
                Arguments.of(USD_CURRENCY, null, false),
                Arguments.of(USD_CURRENCY, HUF_CURRENCY, false),
                Arguments.of(USD_CURRENCY, USD_CURRENCY, true)
        );
    }
}
