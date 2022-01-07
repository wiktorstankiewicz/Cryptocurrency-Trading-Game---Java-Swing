package Utilities;

import java.awt.*;
import java.io.Serial;
import java.io.Serializable;

public class CandleStick implements Serializable {
    @Serial
    private static final long serialVersionUID = 3541615373408865763L;
    //in 0-1, relative to context
    private double openPricePercentHeight;
    private double closePricePercentHeight;
    private double maxPricePercentHeight;
    private double minPricePercentHeight;


    //in USDT
    private double openPrice;
    private double closePrice;
    private double maxPrice;
    private double minPrice;

    private Color color;
    private CryptoCurrency cryptoCurrency;
    private GameTime openTime;
    private GameTime closeTime;
    private boolean closed = false;

    //Mapping parameters
    private final double LOWER_BOUND = 0d;
    private final double UPPER_BOUND = 1d;

    public void mapPriceValues(double minPriceInArray, double maxPriceInArray) {
        if (minPriceInArray == maxPriceInArray) {
            maxPriceInArray = minPriceInArray * 1.2d;
        }
        assert (minPriceInArray < maxPriceInArray);
        openPricePercentHeight = linearMapping(openPrice, minPriceInArray, maxPriceInArray, LOWER_BOUND, UPPER_BOUND);
        closePricePercentHeight = linearMapping(closePrice, minPriceInArray, maxPriceInArray, LOWER_BOUND, UPPER_BOUND);
        maxPricePercentHeight = linearMapping(maxPrice, minPriceInArray, maxPriceInArray, LOWER_BOUND, UPPER_BOUND);
        minPricePercentHeight = linearMapping(minPrice, minPriceInArray, maxPriceInArray, LOWER_BOUND, UPPER_BOUND);
        assert (openPricePercentHeight >= 0);
        assert (closePricePercentHeight >= 0);
        assert (maxPricePercentHeight >= 0);
        assert (minPricePercentHeight >= 0);

        assert (openPricePercentHeight <= 1);
        assert (closePricePercentHeight <= 1);
        assert (maxPricePercentHeight <= 1);
        assert (minPricePercentHeight <= 1);
    }

    public static double linearMapping(double valueToMap, double min, double max, double lowerBound, double upperBound) throws IllegalArgumentException {
        assert (valueToMap >= min);
        assert (valueToMap <= max);
        assert (min < max);
        return ((valueToMap - min) / (max - min)) * (upperBound - lowerBound) + lowerBound;
    }

    public CandleStick(double openPriceUSDT,
                       double closePriceUSDT,
                       double maxPriceUSDT,
                       double minPriceUSDT,
                       CryptoCurrency cryptoCurrency,
                       GameTime openTime) {

        if (maxPriceUSDT < minPriceUSDT) {
            throw new IllegalArgumentException("max price cant be smaller than min price");
        }
        this.openPrice = openPriceUSDT;
        this.closePrice = closePriceUSDT;
        this.maxPrice = maxPriceUSDT;
        this.minPrice = minPriceUSDT;
        this.cryptoCurrency = cryptoCurrency;
        this.openTime = new GameTime(openTime.getDays(), openTime.getHours(), openTime.getMinutes()
                , openTime.getSeconds());
    }

    public CandleStick(CryptoCurrency cryptoCurrency, GameTime openTime) {
        this(cryptoCurrency.getCurrentPrice(),
                cryptoCurrency.getCurrentPrice(),
                cryptoCurrency.getCurrentPrice(),
                cryptoCurrency.getCurrentPrice(),
                cryptoCurrency,
                openTime);
    }

    public void updatePrices() {
        double newPrice = cryptoCurrency.getCurrentPrice();
        //todo zmapowac cene krypto na wspolrzedne wykresu
        this.closePrice = newPrice;
        this.maxPrice = Math.max(newPrice, this.maxPrice);
        this.minPrice = Math.min(newPrice, this.minPrice);
    }

    public void close(GameTime gameTime) {
        this.closeTime = gameTime;
        this.closed = true;
    }

    public double getOpenPricePercentHeight() {
        return openPricePercentHeight;
    }

    public void setOpenPricePercentHeight(int openPricePercentHeight) {
        this.openPricePercentHeight = openPricePercentHeight;
    }

    public double getClosePricePercentHeight() {
        return closePricePercentHeight;
    }

    public void setClosePricePercentHeight(int closePricePercentHeight) {
        this.closePricePercentHeight = closePricePercentHeight;
    }

    public double getMaxPricePercentHeight() {
        return maxPricePercentHeight;
    }

    public void setMaxPricePercentHeight(int maxPricePercentHeight) {
        this.maxPricePercentHeight = maxPricePercentHeight;
    }

    public double getMinPricePercentHeight() {
        return minPricePercentHeight;
    }

    public void setMinPricePercentHeight(int minPricePercentHeight) {
        this.minPricePercentHeight = minPricePercentHeight;
    }

    public Color getColor() {
        if (closePricePercentHeight - openPricePercentHeight > 0) {
            return Color.GREEN;
        }
        return Color.RED;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public CryptoCurrency getCryptoCurrency() {
        return cryptoCurrency;
    }

    public void setCryptoCurrency(CryptoCurrency cryptoCurrency) {
        this.cryptoCurrency = cryptoCurrency;
    }

    public GameTime getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(GameTime closeTime) {
        this.closeTime = closeTime.clone();
    }

    public GameTime getOpenTime() {
        return openTime;
    }

    public void setOpenTime(GameTime openTime) {
        this.openTime = openTime;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public void setOpenPricePercentHeight(double openPricePercentHeight) {
        if (openPricePercentHeight < 0) {
            throw new IllegalArgumentException("openPricePercentHeight should be positive");
        }
        this.openPricePercentHeight = openPricePercentHeight;
    }

    public void setClosePricePercentHeight(double closePricePercentHeight) {
        this.closePricePercentHeight = closePricePercentHeight;
    }

    public void setMaxPricePercentHeight(double maxPricePercentHeight) {
        this.maxPricePercentHeight = maxPricePercentHeight;
    }

    public void setMinPricePercentHeight(double minPricePercentHeight) {
        this.minPricePercentHeight = minPricePercentHeight;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }

    public double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(double closePrice) {
        this.closePrice = closePrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    @Override
    public String toString() {
        return "CandleStick{" +
                "openPricePercentHeight=" + openPricePercentHeight +
                ", closePricePercentHeight=" + closePricePercentHeight +
                ", maxPricePercentHeight=" + maxPricePercentHeight +
                ", minPricePercentHeight=" + minPricePercentHeight +
                ", openPrice=" + openPrice +
                ", closePrice=" + closePrice +
                ", maxPrice=" + maxPrice +
                ", minPrice=" + minPrice +
                '}';
    }
}
