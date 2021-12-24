package plot;

import java.awt.*;

public class CandleStick {
    private int openPriceHeight;
    private int closePriceHeight;
    private int maxPriceHeight;
    private int minPriceHeight;
    private int currentPriceHeigth;
    private Color color;

    public CandleStick(int openPriceHeight, int closePriceHeight, int maxPriceHeight, int minPriceHeight, int currentPriceHeigth, Color color) {
        this.openPriceHeight = openPriceHeight;
        this.closePriceHeight = closePriceHeight;
        this.maxPriceHeight = maxPriceHeight;
        this.minPriceHeight = minPriceHeight;
        this.currentPriceHeigth = currentPriceHeigth;
        this.color = color;
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
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getCurrentPriceHeigth() {
        return currentPriceHeigth;
    }

    public void setCurrentPriceHeigth(int currentPriceHeigth) {
        this.currentPriceHeigth = currentPriceHeigth;
    }
}
