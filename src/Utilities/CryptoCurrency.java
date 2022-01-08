package Utilities;

import Interfaces.CalculationOfPriceStrategy.GeometricBrownianMotion;
import Interfaces.CalculationOfPriceStrategy.ICurrencyPriceCalculation;
import Interfaces.CalculationOfPriceStrategy.RandomlyGeneratedValue;

import javax.swing.*;
import java.io.Serial;
import java.io.Serializable;

public class CryptoCurrency implements Serializable {
    @Serial
    private static final long serialVersionUID = 1932135322629796758L;
    private final String name;
    private double currentPrice;
    private final double volatility;
    private final double expectedAnnualReturn;
    private final ICurrencyPriceCalculation priceCalculation;
    private ImageIcon imageIcon;
    //S(t+dt) = St * exp( (eAR - vol^2/2)*dt + vol*RDN*sqrt(dt))

    public CryptoCurrency(String name, double basePrice,
                          String imgFilePath, double volatility, double expectedAnnualReturn) {
        this.name = name;
        this.volatility = volatility;
        this.currentPrice = basePrice;
        this.expectedAnnualReturn = expectedAnnualReturn;
        //todo example price calculation strategy
        this.priceCalculation = new RandomlyGeneratedValue();
        this.imageIcon = new ImageIcon(imgFilePath);
    }

    @Override
    public String toString(){
        return "Name: " + name;
    }

    public String getName() {
        return name;
    }

    public double getVolatility() {
        return volatility;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getExpectedAnnualReturn() {
        return expectedAnnualReturn;
    }

    public ICurrencyPriceCalculation getPriceCalculation() {
        return priceCalculation;
    }

    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    public void setImageIcon(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
    }
}
