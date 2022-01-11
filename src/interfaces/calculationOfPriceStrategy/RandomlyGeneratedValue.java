package interfaces.calculationOfPriceStrategy;

import utilities.CryptoCurrency;

import java.io.Serial;
import java.io.Serializable;
import java.util.Random;

public class RandomlyGeneratedValue implements ICurrencyPriceCalculation, Serializable {
    @Serial
    private static final long serialVersionUID = 3985197065325601270L;

    @Override
    public void calculatePrice(int dt, CryptoCurrency cryptoCurrency) {
        Random generator = new Random();
        double newPrice;
        double maxPercentChange = cryptoCurrency.getMaxPercentChange();
        double marketVolatility = cryptoCurrency.getMarketVolatility();
        double previousPrice = cryptoCurrency.getCurrentPrice();
        double lowerBound;
        double upperBound;

        switch (cryptoCurrency.getState()) {
            case STAGNATION_MODE -> {
                lowerBound = -maxPercentChange;
                upperBound = maxPercentChange;
            }
            case BULL_MODE -> {
                lowerBound = -maxPercentChange;
                upperBound = marketVolatility*maxPercentChange;
            }
            case BEAR_MODE -> {
                lowerBound = -marketVolatility*maxPercentChange;
                upperBound = maxPercentChange;
            }
            default -> throw new IllegalStateException("Unexpected value: " + cryptoCurrency.getState());
        }
        newPrice = previousPrice +
                previousPrice * generator.nextDouble(lowerBound,upperBound);
        cryptoCurrency.setCurrentPrice(newPrice);
    }
}
