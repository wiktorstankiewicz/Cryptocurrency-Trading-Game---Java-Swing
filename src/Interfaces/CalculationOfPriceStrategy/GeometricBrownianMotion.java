package Interfaces.CalculationOfPriceStrategy;

import Utilities.CryptoCurrency;

import java.io.Serial;
import java.io.Serializable;
import java.util.Random;

public class GeometricBrownianMotion implements ICurrencyPriceCalculation, Serializable {
    @Serial
    private static final long serialVersionUID = -8697733920637380802L;

    @Override
    public void calculatePrice(int dt, CryptoCurrency cryptoCurrency) {
        Random generator = new Random();
        double currentPrice = cryptoCurrency.getCurrentPrice();
        double expectedAnnualReturn = cryptoCurrency.getExpectedAnnualReturn();
        double volatility = cryptoCurrency.getVolatility();
        cryptoCurrency.setCurrentPrice(currentPrice * Math.exp((expectedAnnualReturn - (volatility * volatility) / 2) * dt
                + currentPrice * volatility * generator.nextDouble(-1, 1)));
    }
}
