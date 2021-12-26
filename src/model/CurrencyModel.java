package model;

import Utilities.CandleStick;
import Utilities.CryptoCurrency;

import java.util.ArrayList;

public class CurrencyModel {
    private int index;
    private CryptoCurrency cryptoCurrency;
    private double ownedAmount;
    private ArrayList<CandleStick> candleStickArrayList; //history of prices represented by candlesticks
    private double averagePrice;
    private double dailyPercentChange;
    private double dailyAbsoluteChange;

    public CurrencyModel(int index, CryptoCurrency cryptoCurrency) {
        this.index = index;
        this.cryptoCurrency = cryptoCurrency;
        ownedAmount = 0;
        candleStickArrayList = new ArrayList<>();
    }

    //setters and getters
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public CryptoCurrency getCryptoCurrency() {
        return cryptoCurrency;
    }

    public void setCryptoCurrency(CryptoCurrency cryptoCurrency) {
        this.cryptoCurrency = cryptoCurrency;
    }

    public double getOwnedAmount() {
        return ownedAmount;
    }

    public void setOwnedAmount(double ownedAmount) {
        this.ownedAmount = ownedAmount;
    }

    public ArrayList<CandleStick> getCandleStickArrayList() {
        return candleStickArrayList;
    }

    public void setCandleStickArrayList(ArrayList<CandleStick> candleStickArrayList) {
        this.candleStickArrayList = candleStickArrayList;
    }
}
