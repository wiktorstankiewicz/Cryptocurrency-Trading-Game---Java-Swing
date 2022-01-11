package interfaces.calculationOfPriceStrategy;

import utilities.CryptoCurrency;

public interface ICurrencyPriceCalculation {
    void calculatePrice(int dt, CryptoCurrency cryptoCurrency);
}
