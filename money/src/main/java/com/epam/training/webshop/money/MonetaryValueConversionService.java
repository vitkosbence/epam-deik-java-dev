package com.epam.training.webshop.money;

import java.util.Currency;

public interface MonetaryValueConversionService {

  double convert(double value, Currency originalCurrency, Currency targetCurrency);

}
