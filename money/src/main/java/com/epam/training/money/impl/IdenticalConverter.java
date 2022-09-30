package com.epam.training.money.impl;

import com.epam.training.money.ConversionRate;
import java.util.Currency;
import java.util.Optional;

public class IdenticalConverter implements ConversionRate {

  @Override
  public boolean canConvert(final Currency originalCurrency, final Currency targetCurrency) {
    return Optional.ofNullable(originalCurrency)
            .map(og -> og.equals(targetCurrency))
            .orElse(false);
  }

  @Override
  public double convert(final double value) {
    return value;
  }
}
