package Interfaces.CalculationOfPriceStrategy;

import Utilities.CryptoCurrency;

public interface ICurrencyPriceCalculation {
    /***
     *
     * @param dt CHANGE OF TIME IN SECONDS
     * @param cryptoCurrency crypto currency whose price is calculated
     */
    void calculatePrice(int dt, CryptoCurrency cryptoCurrency);
}
