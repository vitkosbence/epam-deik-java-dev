package com.epam.training.money.impl;

import com.epam.training.money.ConversionRate;
import java.util.Currency;

public class IdenticalConverter implements ConversionRate {

  @Override
  public boolean canConvert(final Currency originalCurrency, final Currency targetCurrency) {
    return originalCurrency.equals(targetCurrency);
  }

  @Override
  public double convert(final double value) {
    return value;
  }
}
