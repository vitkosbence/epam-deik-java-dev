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

class FixedConverterTest {

    private static final Currency USD_CURRENCY = Currency.getInstance("USD");
    private static final Currency HUF_CURRENCY = Currency.getInstance("HUF");
    private static final Currency EUR_CURRENCY = Currency.getInstance("EUR");
    private static final double VALUE_TO_CONVERT = 300.0;

    private ConversionRate underTest;

    @BeforeEach
    void setUp() {
        underTest = new FixedConverter(USD_CURRENCY, HUF_CURRENCY, VALUE_TO_CONVERT);
    }

    @Test
    public void testCanConvertShouldReturnTrueWhenGivenConvertAbleCurrencies() {
        // Given
        // When
        final boolean actual = underTest.canConvert(USD_CURRENCY, HUF_CURRENCY);
        // Then
        Assertions.assertTrue(actual);
    }

    @Test
    public void testCanConvertShouldReturnFalseWhenGivenUnConvertAbleCurrencyPair() {
        // Given
        // When
        final boolean actual = underTest.canConvert(HUF_CURRENCY, USD_CURRENCY);
        // Then
        Assertions.assertFalse(actual);
    }

    @Test
    public void testCanConvertShouldReturnFalseWhenGivenOriginalCurrencyIsUnknown() {
        // Given
        // When
        final boolean actual = underTest.canConvert(EUR_CURRENCY, USD_CURRENCY);
        // Then
        Assertions.assertFalse(actual);
    }

    @Test
    public void testCanConvertShouldReturnFalseWhenGivenTargetCurrencyIsUnknown() {
        // Given
        // When
        final boolean actual = underTest.canConvert(USD_CURRENCY, EUR_CURRENCY);
        // Then
        Assertions.assertFalse(actual);
    }

    @MethodSource("convertTestData")
    @ParameterizedTest
    void testConvert(final double valueToConvert, final double expectedValue) {
        // Given
        // When
        final double actual = underTest.convert(valueToConvert);
        // Then
        Assertions.assertEquals(expectedValue, actual);
    }

    private static Stream<Arguments> convertTestData() {
        return Stream.of(
                Arguments.of(1, 300),
                Arguments.of(0, 0),
                Arguments.of(-1, -300)
        );
    }
}
