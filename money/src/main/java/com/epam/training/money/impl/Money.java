package com.epam.training.money.impl;

import com.epam.training.money.MonetaryValueConversionService;
import java.util.Currency;

public class Money {

  private final double amount;
  private final Currency currency;

  public Money(double amount, Currency currency) {
    this.amount = amount;
    this.currency = currency;
  }

  public double getAmount() {
    return amount;
  }

  public Currency getCurrency() {
    return currency;
  }

  public Money add(final Money moneyToGive, MonetaryValueConversionService monetaryValueConversionService) {
    final double convertedMoney = convertMoney(moneyToGive, monetaryValueConversionService);
    return new Money(this.getAmount() + convertedMoney, this.getCurrency());
  }

  public Integer compareTo(final Money money, MonetaryValueConversionService monetaryValueConversionService) {
    double convertedMoney = convertMoney(money, monetaryValueConversionService);
    return Double.compare(this.getAmount(), convertedMoney);
  }

  private double convertMoney(final Money money, MonetaryValueConversionService monetaryValueConversionService) {
    return monetaryValueConversionService.convert(money.getAmount(), money.getCurrency(), this.getCurrency());
  }
}