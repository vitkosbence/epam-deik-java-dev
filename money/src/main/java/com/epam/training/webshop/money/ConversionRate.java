package com.epam.training.webshop.money;

import java.util.Currency;

public interface ConversionRate {

  boolean canConvert(Currency originalCurrency, Currency targetCurrency);

  double convert(double value);
}
