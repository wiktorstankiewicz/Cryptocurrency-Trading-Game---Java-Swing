package utilities;

import interfaces.calculationOfPriceStrategy.ICurrencyPriceCalculation;
import interfaces.calculationOfPriceStrategy.RandomlyGeneratedValue;

import javax.swing.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Random;

public class CryptoCurrency implements Serializable {
    @Serial
    private double basePrice;
    private static final long serialVersionUID = 1932135322629796758L;
    private final double marketVolatility;
    private final double cycleLengthParameter;
    private final String name;
    private double currentPrice;
    private final ICurrencyPriceCalculation priceCalculation;
    private final ImageIcon imageIcon;
    private State state = State.BULL_MODE;
    private final double maxPercentChange;


    public enum State {
        BEAR_MODE, BULL_MODE, STAGNATION_MODE
    }

    //====================================================Public Methods==============================================//

    public CryptoCurrency(String name,
                          double basePrice,
                          String imgFilePath,
                          double marketVolatility,
                          double cycleLengthParameter,
                          double maxPercentChange) {
        this.name = name;
        this.currentPrice = basePrice;
        this.basePrice = basePrice;
        this.priceCalculation = new RandomlyGeneratedValue();
        this.imageIcon = new ImageIcon(imgFilePath);
        this.marketVolatility = marketVolatility;
        this.cycleLengthParameter = cycleLengthParameter;
        this.maxPercentChange = maxPercentChange;
    }

    public void updatePrice(int dt) {
        updateState();
        priceCalculation.calculatePrice(dt, this);
    }

    @Override
    public String toString() {
        return "Name: " + name;
    }

    //====================================================Private Methods=============================================//

    private void updateState() {
        Random generator = new Random();
        int n = generator.nextInt(0, 101);
        if (n < cycleLengthParameter) {
            changeState();
        }
    }

    private void changeState() {
        Random generator = new Random();
        int n = generator.nextInt(0, 101);
        if (state == State.STAGNATION_MODE) {
            switch (n % 2) {
                case (0) -> state = State.BULL_MODE;
                case (1) -> state = State.BEAR_MODE;
            }
            return;
        }

        state = State.STAGNATION_MODE;
    }

    //===================================================Getter and setters===========================================//

    public String getName() {
        return name;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    public State getState() {
        return state;
    }

    public double getMarketVolatility() {
        return marketVolatility;
    }

    public double getMaxPercentChange() {
        return maxPercentChange;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }
}
