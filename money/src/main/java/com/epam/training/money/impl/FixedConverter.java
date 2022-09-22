package com.epam.training.money.impl;

import com.epam.training.money.ConversionRate;
import java.util.Currency;

public class FixedConverter implements ConversionRate {

  private final Currency originalCurrency;
  private final Currency targetCurrency;
  private final double rate;

  public FixedConverter(final Currency originalCurrency, final Currency targetCurrency, final double rate) {
    this.originalCurrency = originalCurrency;
    this.targetCurrency = targetCurrency;
    this.rate = rate;
  }

  @Override
  public boolean canConvert(final Currency originalCurrency, final Currency targetCurrency) {
    return this.originalCurrency.equals(originalCurrency) && this.targetCurrency.equals(targetCurrency);
  }

  @Override
  public double convert(final double value) {
    return value * rate;
  }
}
