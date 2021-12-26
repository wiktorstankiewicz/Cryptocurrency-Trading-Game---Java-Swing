package Utilities;

import java.awt.*;

public class CandleStick {
    private int openPriceHeight;
    private int closePriceHeight;
    private int maxPriceHeight;
    private int minPriceHeight;
    private Color color;
    private CryptoCurrency cryptoCurrency;

    public CandleStick(int openPriceHeight, int closePriceHeight, int maxPriceHeight,
                       int minPriceHeight, CryptoCurrency cryptoCurrency) {
        if (maxPriceHeight < minPriceHeight) {
            throw new IllegalArgumentException("Max price cannot be smaller than min price");
        }
        this.openPriceHeight = openPriceHeight;
        this.closePriceHeight = closePriceHeight;
        this.maxPriceHeight = maxPriceHeight;
        this.minPriceHeight = minPriceHeight;
        this.cryptoCurrency = cryptoCurrency;
    }

    public CandleStick(CryptoCurrency cryptoCurrency) {
        this.cryptoCurrency = cryptoCurrency;
        this.minPriceHeight = (int) cryptoCurrency.getCurrentPrice();
        this.maxPriceHeight = (int) cryptoCurrency.getCurrentPrice();
        this.openPriceHeight = (int) cryptoCurrency.getCurrentPrice();
        this.closePriceHeight = (int) cryptoCurrency.getCurrentPrice();
    }

    public void update() {
        int newPrice = (int) cryptoCurrency.getCurrentPrice();
        //todo zmapowac cene krypto na wspolrzedne wykresu
        this.closePriceHeight = newPrice;
        this.maxPriceHeight = Math.max(newPrice, maxPriceHeight);
        this.minPriceHeight = Math.min(newPrice, minPriceHeight);
    }

    public int getOpenPriceHeight() {
        return openPriceHeight;
    }

    public void setOpenPriceHeight(int openPriceHeight) {
        this.openPriceHeight = openPriceHeight;
    }

    public int getClosePriceHeight() {
        return closePriceHeight;
    }

    public void setClosePriceHeight(int closePriceHeight) {
        this.closePriceHeight = closePriceHeight;
    }

    public int getMaxPriceHeight() {
        return maxPriceHeight;
    }

    public void setMaxPriceHeight(int maxPriceHeight) {
        this.maxPriceHeight = maxPriceHeight;
    }

    public int getMinPriceHeight() {
        return minPriceHeight;
    }

    public void setMinPriceHeight(int minPriceHeight) {
        this.minPriceHeight = minPriceHeight;
    }

    public Color getColor() {
        if (closePriceHeight - openPriceHeight > 0) {
            return Color.GREEN;
        }
        return Color.RED;
    }
}
