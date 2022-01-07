package model;

import Utilities.CandleStick;
import Utilities.CryptoCurrency;
import Utilities.GameTime;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class CurrencyModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 6096373980849786301L;
    private double ownedAmount;
    private double valueOfOwnedAmount;
    private ArrayList<CandleStick> candleStickArrayList = new ArrayList<>(); //history of prices represented by candlesticks
    private CryptoCurrency cryptoCurrency;
    private double dailyPercentChange;
    private double dailyAbsoluteChange;

    private PacketToDraw packetToDraw;

    public CurrencyModel( CryptoCurrency cryptoCurrency) {
        this.cryptoCurrency = cryptoCurrency;
        ownedAmount = 0;
        candleStickArrayList.add(new CandleStick(cryptoCurrency, new GameTime()));
    }

    public double getValueOfOwnedAmount() {
        valueOfOwnedAmount = ownedAmount*cryptoCurrency.getCurrentPrice();
        return valueOfOwnedAmount;
    }

    public CryptoCurrency getCryptoCurrency() {
        return cryptoCurrency;
    }

    public ArrayList<CandleStick> getCandleStickArrayList() {
        return candleStickArrayList;
    }

    public void setCandleStickArrayList(ArrayList<CandleStick> candleStickArrayList) {
        this.candleStickArrayList = candleStickArrayList;
    }

    public void update(int timePassed, int amountToDraw) {
        cryptoCurrency.getPriceCalculation().calculatePrice(timePassed, cryptoCurrency);
        candleStickArrayList.get(candleStickArrayList.size() - 1).updatePrices();
        PacketToDraw packetToDraw = new PacketToDraw(20);
        //candleStickArrayList.get(candleStickArrayList.size() - 1).mapPriceValues(minPriceInArrayList,maxPriceInArrayList);
        assert (candleStickArrayList.size() > 0);

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

    public CandleStick lastCandleStick() {
        return candleStickArrayList.get(candleStickArrayList.size() - 1);
    }

    public PacketToDraw getPacketToDraw(int amountToDraw){
        return new PacketToDraw(amountToDraw);
    }

    public double getOwnedAmount() {
        return ownedAmount;
    }

    public void setOwnedAmount(double ownedAmount) {
        this.ownedAmount = ownedAmount;
    }

    public class PacketToDraw{
        ArrayList<CandleStick> candleSticks = new ArrayList<>();
        double minPriceInArrayList;
        double maxPriceInArrayList;
        ArrayList<Double> axisValues = new ArrayList<Double>();

        public PacketToDraw(int amountToDraw){
            for(int i = Math.max(0, candleStickArrayList.size()-amountToDraw); i<candleStickArrayList.size(); i++){
                candleSticks.add(candleStickArrayList.get(i));
            }
            fitToSize();
        }

        private void calculateMinAndMaxPriceInArrayList() {
            minPriceInArrayList = Double.MAX_VALUE;
            maxPriceInArrayList = Double.MIN_VALUE;
            for (CandleStick cs : candleSticks) {
                if (cs.getMinPrice() < minPriceInArrayList) {
                    minPriceInArrayList = cs.getMinPrice();
                }
                if (cs.getMaxPrice() > maxPriceInArrayList) {
                    maxPriceInArrayList = cs.getMaxPrice();
                }
            }
        }

        private void fitToSize() {
            calculateMinAndMaxPriceInArrayList();
            for (CandleStick cs : candleSticks) {
                cs.mapPriceValues(minPriceInArrayList, maxPriceInArrayList);
            }
        }

        public ArrayList<CandleStick> getCandleSticks() {
            return candleSticks;
        }


        public double getMinPriceInArrayList() {
            return minPriceInArrayList;
        }


        public double getMaxPriceInArrayList() {
            return maxPriceInArrayList;
        }


    }
}
