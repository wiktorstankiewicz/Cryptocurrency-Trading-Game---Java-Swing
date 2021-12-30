package model;

import Constants.ExCryptocurrencies;
import Utilities.CandleStick;
import Utilities.CryptoCurrency;
import Utilities.GameTime;

import java.util.ArrayList;

public class CurrencyModel {
    private int index;
    private CryptoCurrency cryptoCurrency;
    private double ownedAmount;
    private ArrayList<CandleStick> candleStickArrayList = new ArrayList<>();; //history of prices represented by candlesticks
    private double averagePrice;
    private double dailyPercentChange;
    private double dailyAbsoluteChange;
    private double minPriceInArrayList;
    private double maxPriceInArrayList;

    public CurrencyModel(int index, CryptoCurrency cryptoCurrency) {
        this.index = index;
        this.cryptoCurrency = cryptoCurrency;
        ownedAmount = 0;
        candleStickArrayList.add(new CandleStick(cryptoCurrency,cryptoCurrency.getCurrentPrice(),cryptoCurrency.getCurrentPrice(),new GameTime(0,0,0,0)));
        System.out.println("size " + candleStickArrayList.size());
    }

    public void calculateMinAndMaxPriceInArrayList(){
        minPriceInArrayList = Double.MAX_VALUE;
        maxPriceInArrayList = Double.MIN_VALUE;

        for(CandleStick cs: candleStickArrayList){
            if(cs.getMinPrice() < minPriceInArrayList){
                minPriceInArrayList = cs.getMinPrice();
            }
            if(cs.getMaxPrice() > maxPriceInArrayList){
                maxPriceInArrayList = cs.getMaxPrice();
            }
        }
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

    public void update(int timePassed) {
        cryptoCurrency.getPriceCalculation().calculatePrice(timePassed,cryptoCurrency);
        calculateMinAndMaxPriceInArrayList();
        assert (candleStickArrayList.size() > 0);
        if(minPriceInArrayList == maxPriceInArrayList){
            candleStickArrayList.get(candleStickArrayList.size()-1).update(candleStickArrayList);
        }
        candleStickArrayList.get(candleStickArrayList.size()-1).update(candleStickArrayList);
        fitToSize();

    }

    private void fitToSize() {
        for(CandleStick cs: candleStickArrayList){
            cs.mapPriceValues(minPriceInArrayList,maxPriceInArrayList);
        }
    }

    public double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public double getDailyPercentChange() {
        return dailyPercentChange;
    }

    public void setDailyPercentChange(double dailyPercentChange) {
        this.dailyPercentChange = dailyPercentChange;
    }

    public double getDailyAbsoluteChange() {
        return dailyAbsoluteChange;
    }

    public void setDailyAbsoluteChange(double dailyAbsoluteChange) {
        this.dailyAbsoluteChange = dailyAbsoluteChange;
    }

    public double getMinPriceInArrayList() {
        return minPriceInArrayList;
    }

    public void setMinPriceInArrayList(double minPriceInArrayList) {
        this.minPriceInArrayList = minPriceInArrayList;
    }

    public double getMaxPriceInArrayList() {
        return maxPriceInArrayList;
    }

    public void setMaxPriceInArrayList(double maxPriceInArrayList) {
        this.maxPriceInArrayList = maxPriceInArrayList;
    }
}
