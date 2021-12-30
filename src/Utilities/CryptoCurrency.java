package Utilities;

import Interfaces.CalculationOfPriceStrategy.GeometricBrownianMotion;
import Interfaces.CalculationOfPriceStrategy.ICurrencyPriceCalculation;

import javax.swing.*;
import java.util.Random;

public class CryptoCurrency {
    private String name;
    private double basePrice;
    private double currentPrice;
    private String imgFilePath;
    private double volatility;
    private double expectedAnnualReturn;
    private ICurrencyPriceCalculation priceCalculation;
    private ImageIcon imageIcon;
    //S(t+dt) = St * exp( (eAR - vol^2/2)*dt + vol*RDN*sqrt(dt))

    public CryptoCurrency(String name, double basePrice,
                          String imgFilePath, double volatility, double expectedAnnualReturn) {
        this.name = name;
        this.basePrice = basePrice;
        this.imgFilePath = imgFilePath;
        this.volatility = volatility;
        this.currentPrice = basePrice;
        this.expectedAnnualReturn = expectedAnnualReturn;
        //todo example price calculation strategy
        this.priceCalculation = new GeometricBrownianMotion();
        this.imageIcon = new ImageIcon(imgFilePath);
    }

    //todo strategia obliczania ceny

    @Override
    public String toString(){
        return "Name: " + name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }

    public String getImgFilePath() {
        return imgFilePath;
    }

    public void setImgFilePath(String imgFilePath) {
        this.imgFilePath = imgFilePath;
    }

    public double getVolatility() {
        return volatility;
    }

    public void setVolatility(double volatility) {
        this.volatility = volatility;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
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

    public void setExpectedAnnualReturn(double expectedAnnualReturn) {
        this.expectedAnnualReturn = expectedAnnualReturn;
    }

    public ICurrencyPriceCalculation getPriceCalculation() {
        return priceCalculation;
    }

    public void setPriceCalculation(ICurrencyPriceCalculation priceCalculation) {
        this.priceCalculation = priceCalculation;
    }

    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    public void setImageIcon(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
    }
}
