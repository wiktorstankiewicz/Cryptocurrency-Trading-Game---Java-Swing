package Interfaces.CalculationOfPriceStrategy;

import Utilities.CryptoCurrency;

import java.util.Random;

public class GeometricBrownianMotion implements ICurrencyPriceCalculation {
    @Override
    public void calculatePrice(int dt, CryptoCurrency cryptoCurrency) {
        Random generator = new Random();
        double currentPrice = cryptoCurrency.getCurrentPrice();
        double expectedAnnualReturn = cryptoCurrency.getExpectedAnnualReturn();
        double volatility = cryptoCurrency.getVolatility();
        cryptoCurrency.setCurrentPrice(currentPrice * Math.exp((expectedAnnualReturn - (volatility * volatility) / 2) * dt
                + volatility * generator.nextDouble(-1, 1)));
    }
}
