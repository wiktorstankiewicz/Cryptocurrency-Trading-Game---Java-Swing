package Interfaces.CalculationOfPriceStrategy;

import Utilities.CryptoCurrency;

import java.io.Serial;
import java.io.Serializable;
import java.util.Random;

public class RandomlyGeneratedValue implements ICurrencyPriceCalculation, Serializable {
    @Serial
    private static final long serialVersionUID = 3985197065325601270L;

    @Override
    public void calculatePrice(int dt, CryptoCurrency cryptoCurrency) {
        Random generator = new Random();
        double maxPercentChange = 0.001;
        double previousPrice = cryptoCurrency.getCurrentPrice();
        double newPrice = previousPrice + previousPrice*generator.nextDouble(-maxPercentChange,maxPercentChange);
        cryptoCurrency.setCurrentPrice(newPrice);
    }
}
