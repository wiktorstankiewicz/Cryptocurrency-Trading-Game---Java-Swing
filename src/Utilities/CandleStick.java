package Utilities;

import java.awt.*;
import java.util.ArrayList;

public class CandleStick {
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
    private static final double LOWER_BOUND = 0f;
    private static final double UPPER_BOUND = 1f;

    public void mapPriceValues(double minPriceInArray, double maxPriceInArray) {
        if(minPriceInArray == maxPriceInArray){
            maxPriceInArray = minPriceInArray*1.2f;
        }
        openPricePercentHeight = linearMapping(openPrice, minPriceInArray, maxPriceInArray);
        closePricePercentHeight = linearMapping(closePrice, minPriceInArray, maxPriceInArray);
        maxPricePercentHeight = linearMapping(maxPrice, minPriceInArray, maxPriceInArray);
        minPricePercentHeight = linearMapping(minPrice, minPriceInArray, maxPriceInArray);
    }

    private double linearMapping(double valueToMap, double min, double max) throws IllegalArgumentException {
        if(min >= max){
            throw new IllegalArgumentException("min and max can't be equal");
        }
        return ((valueToMap - min) / (max - min)) * (UPPER_BOUND - LOWER_BOUND) + LOWER_BOUND;

    }

    public CandleStick(double openPriceUSDT,
                       double closePriceUSDT,
                       double maxPriceUSDT,
                       double minPriceUSDT,
                       double minPriceInArrayList,
                       double maxPriceInArrayList,
                       CryptoCurrency cryptoCurrency,
                       GameTime openTime) {

        if (maxPriceUSDT < minPriceUSDT){
            throw new IllegalArgumentException("max price cant be smaller than min price");
        };

        this.openPrice = openPriceUSDT;
        this.closePrice = closePriceUSDT;
        this.maxPrice = maxPriceUSDT;
        this.minPrice = minPriceUSDT;

        mapPriceValues(minPriceInArrayList, maxPriceInArrayList);
        this.cryptoCurrency = cryptoCurrency;
        this.openTime = new GameTime(openTime.getDays(), openTime.getHours(), openTime.getMinutes()
                , openTime.getSeconds());
    }

    public CandleStick(CryptoCurrency cryptoCurrency, double minPriceInArrayList, double maxPriceInArrayList, GameTime openTime) {
        this(cryptoCurrency.getCurrentPrice(),
                cryptoCurrency.getCurrentPrice(),
                cryptoCurrency.getCurrentPrice(),
                cryptoCurrency.getCurrentPrice(),
                minPriceInArrayList,
                maxPriceInArrayList,
                cryptoCurrency,
                openTime);
    }

    public void update(ArrayList<CandleStick> candleStickArrayList) {
        double minPriceInArray = candleStickArrayList.stream().min((CandleStick cs1,CandleStick cs2) -> (int) (cs2.getMinPrice()-cs1.getMinPrice())).get().getMinPrice();
        double maxPriceInArray = candleStickArrayList.stream().max((CandleStick cs1,CandleStick cs2) -> (int) (cs1.getMaxPrice()-cs2.getMaxPrice())).get().getMaxPrice();
        double newPrice = cryptoCurrency.getCurrentPrice();
        //todo zmapowac cene krypto na wspolrzedne wykresu
        if(newPrice > maxPriceInArray){
            maxPriceInArray = newPrice;
        }

        if(newPrice < minPriceInArray){
            minPriceInArray = newPrice;
        }
        this.closePrice = newPrice;
        this.maxPrice = Math.max(newPrice, maxPrice);
        this.minPrice = Math.min(newPrice, minPrice);
        mapPriceValues(minPriceInArray, maxPriceInArray);
        System.out.println(this);
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
        this.closeTime = new GameTime(closeTime.getDays(), closeTime.getHours(), closeTime.getMinutes()
                , closeTime.getSeconds());
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
        if(openPricePercentHeight <0){
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
