package com.epam.training.money;

import com.epam.training.webshop.money.MonetaryValueConversionService;
import com.epam.training.webshop.money.exception.UnknownConversionRateException;
import com.epam.training.webshop.money.impl.BankService;
import com.epam.training.webshop.money.impl.Money;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Currency;

import static java.lang.Integer.signum;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class MoneyIT {

  private static final Currency HUF_CURRENCY = Currency.getInstance("HUF");
  private static final Currency USD_CURRENCY = Currency.getInstance("USD");
  private static final Currency GBP_CURRENCY = Currency.getInstance("GBP");

  private final MonetaryValueConversionService monetaryValueConversionService = new BankService();

  @Test
  public void testAddReturnsExpectedResultWhenDifferentCurrencyIsUsed() {
    // Given
    Money underTest = new Money(120, HUF_CURRENCY);
    Money moneyToAdd = new Money(1, USD_CURRENCY);

    // When
    Money result = underTest.add(moneyToAdd, monetaryValueConversionService);

    // Then
    assertThat(result.getAmount(), equalTo(369.3));
    assertThat(result.getCurrency(), equalTo(HUF_CURRENCY));
  }

  @Test
  public void testAddReturnsExpectedResultWhenMatchingCurrencyIsUsed() {
    // Given
    Money underTest = new Money(120, HUF_CURRENCY);
    Money moneyToAdd = new Money(1, HUF_CURRENCY);

    // When
    Money result = underTest.add(moneyToAdd, monetaryValueConversionService);

    // Then
    assertThat(result.getAmount(), equalTo(121.0));
    assertThat(result.getCurrency(), equalTo(HUF_CURRENCY));
  }

  @Test
  public void testAddReturnsNullWhenCurrencyWithUnknownRateIsUsed() {
    // Given
    Money underTest = new Money(120, HUF_CURRENCY);
    Money moneyToAdd = new Money(1, GBP_CURRENCY);

    // When - Then
    Assertions.assertThrows(UnknownConversionRateException.class, () -> underTest.add(moneyToAdd, monetaryValueConversionService));
  }


  @ParameterizedTest
  @CsvSource({"249, 1, -1", "249.3, 1, 0", "250, 0, 1"})
  public void testCompareToReturnsExpectedResultWhenDifferentCurrencyIsUsed(double firstValue, double secondValue, int expectedSignum) {
    // Given
    Money underTest = new Money(firstValue, HUF_CURRENCY);
    Money moneyToCompareWith = new Money(secondValue, USD_CURRENCY);

    // When
    Integer result = underTest.compareTo(moneyToCompareWith, monetaryValueConversionService);

    // Then
    assertThat(signum(result), equalTo(expectedSignum));
  }

  @ParameterizedTest
  @CsvSource({"0, 100, -1", "100, 100, 0", "100, 0, 1"})
  public void testCompareToReturnsExpectedResultWhenMatchingCurrencyIsUsed(double firstValue, double secondValue, int expectedSignum) {
    // Given
    Money underTest = new Money(firstValue, HUF_CURRENCY);
    Money moneyToCompareWith = new Money(secondValue, HUF_CURRENCY);

    // When
    Integer result = underTest.compareTo(moneyToCompareWith, monetaryValueConversionService);

    // Then
    assertThat(signum(result), equalTo(expectedSignum));
  }

  @Test
  public void testCompareToReturnsNullWhenCurrencyWithUnknownRateIsUsed() {
    // Given
    Money underTest = new Money(120, HUF_CURRENCY);
    Money moneyToCompareWith = new Money(1, GBP_CURRENCY);

    // When - Then
    Assertions.assertThrows(UnknownConversionRateException.class, () -> underTest.compareTo(moneyToCompareWith, monetaryValueConversionService));
  }

}
