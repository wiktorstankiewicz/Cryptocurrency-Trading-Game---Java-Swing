package utilities;

import java.awt.*;
import java.io.Serial;
import java.io.Serializable;

@SuppressWarnings("FieldCanBeLocal")
public class CandleStick implements Serializable {
    @Serial
    private static final long serialVersionUID = 3541615373408865763L;

    //in 0-1, relative to context
    private double openPricePercentHeight;
    private double closePricePercentHeight;
    private double maxPricePercentHeight;
    private double minPricePercentHeight;

    //in USDT
    private final double openPrice;
    private double closePrice;
    private double maxPrice;
    private double minPrice;

    private final CryptoCurrency cryptoCurrency;
    private final GameTime openTime;

    //Mapping parameters
    private final double LOWER_BOUND = 0d;
    private final double UPPER_BOUND = 1d;

    //====================================================Public Methods==============================================//

    public CandleStick(double openPriceUSDT,
                       double closePriceUSDT,
                       double maxPriceUSDT,
                       double minPriceUSDT,
                       CryptoCurrency cryptoCurrency,
                       GameTime openTime) {
        this.openPrice = openPriceUSDT;
        this.closePrice = closePriceUSDT;
        this.maxPrice = maxPriceUSDT;
        this.minPrice = minPriceUSDT;
        this.cryptoCurrency = cryptoCurrency;
        this.openTime = openTime.clone();
    }

    public CandleStick(CryptoCurrency cryptoCurrency, GameTime openTime) {
        this(cryptoCurrency.getCurrentPrice(),
                cryptoCurrency.getCurrentPrice(),
                cryptoCurrency.getCurrentPrice(),
                cryptoCurrency.getCurrentPrice(),
                cryptoCurrency,
                openTime);
    }

    public void mapPriceValues(double minPriceInArray, double maxPriceInArray) {
        if (minPriceInArray == maxPriceInArray) {
            maxPriceInArray = minPriceInArray * 1.2d;
        }
        assert (minPriceInArray < maxPriceInArray);
        openPricePercentHeight = linearMapping(openPrice, minPriceInArray,
                maxPriceInArray, LOWER_BOUND, UPPER_BOUND);
        closePricePercentHeight = linearMapping(closePrice, minPriceInArray,
                maxPriceInArray, LOWER_BOUND, UPPER_BOUND);
        maxPricePercentHeight = linearMapping(maxPrice, minPriceInArray,
                maxPriceInArray, LOWER_BOUND, UPPER_BOUND);
        minPricePercentHeight = linearMapping(minPrice, minPriceInArray,
                maxPriceInArray, LOWER_BOUND, UPPER_BOUND);
        assert (openPricePercentHeight >= 0);
        assert (closePricePercentHeight >= 0);
        assert (maxPricePercentHeight >= 0);
        assert (minPricePercentHeight >= 0);
        assert (openPricePercentHeight <= 1);
        assert (closePricePercentHeight <= 1);
        assert (maxPricePercentHeight <= 1);
        assert (minPricePercentHeight <= 1);
    }

    public static double linearMapping(double valueToMap, double min, double max,
                                       double lowerBound, double upperBound) {
        return ((valueToMap - min) / (max - min)) * (upperBound - lowerBound) + lowerBound;
    }

    public void updatePrices() {
        double newPrice = cryptoCurrency.getCurrentPrice();

        this.closePrice = newPrice;
        this.maxPrice = Math.max(newPrice, this.maxPrice);
        this.minPrice = Math.min(newPrice, this.minPrice);
    }
    //====================================================Private Methods=============================================//

    //====================================================Getters and setters=========================================//

    public double getOpenPricePercentHeight() {
        return openPricePercentHeight;
    }

    public double getClosePricePercentHeight() {
        return closePricePercentHeight;
    }

    public double getMaxPricePercentHeight() {
        return maxPricePercentHeight;
    }

    public double getMinPricePercentHeight() {
        return minPricePercentHeight;
    }

    public Color getColor() {
        if (closePricePercentHeight - openPricePercentHeight > 0) {
            return Color.GREEN;
        }
        return Color.RED;
    }

    public GameTime getOpenTime() {
        return openTime;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public double getMinPrice() {
        return minPrice;
    }

}
