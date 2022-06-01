package model;

import interfaces.pricePredictionStrategy.SugestedAction;
import interfaces.pricePredictionStrategy.PricePredictor;
import utilities.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class CurrencyModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 6096373980849786301L;
    private static final int durationOfOneCandleStick = 7200;

    //history of prices represented by candlesticks
    private final ArrayList<CandleStick> candleStickArrayList = new ArrayList<>();
    private final CryptoCurrency cryptoCurrency;
    private double ownedAmount;
    private PricePredictor pricePredictor;

    //====================================================Public Methods==============================================//
    public CurrencyModel(CryptoCurrency cryptoCurrency, PricePredictor pricePredictor) {
        this.cryptoCurrency = cryptoCurrency;
        ownedAmount = 0;
        candleStickArrayList.add(new CandleStick(cryptoCurrency, new GameTime()));
        this.pricePredictor = pricePredictor;
    }

    public SugestedAction getSugestedAction(){
        return pricePredictor.getSuggestedAction(this);
    }

    public void updateCurrencyModel(int timePassed, GameTime gameTime) {
        checkIfCreateCandleStick(gameTime);
        cryptoCurrency.updatePrice(timePassed);
        candleStickArrayList.get(candleStickArrayList.size() - 1).updatePrices();
        assert (candleStickArrayList.size() > 0);
    }

    //====================================================Private Methods=============================================//
    private void checkIfCreateCandleStick(GameTime gameTime) {
        if (candleStickArrayList.size() == 0) {
            candleStickArrayList.add(new CandleStick(cryptoCurrency,
                    gameTime));
            return;
        }
        int last = candleStickArrayList.size() - 1;
        if (gameTime.valueOf() - candleStickArrayList.get(last).getOpenTime().valueOf()
                >= durationOfOneCandleStick) {
            candleStickArrayList.add(new CandleStick(cryptoCurrency,
                    gameTime));
        }
    }

    //====================================================Getters and Setters=========================================//
    public double getValueOfOwnedAmount() {
        return ownedAmount * cryptoCurrency.getCurrentPrice();
    }

    public PacketToDraw getPacketToDraw(int amountToDraw) { return new PacketToDraw(amountToDraw); }

    public CryptoCurrency getCryptoCurrency() {
        return cryptoCurrency;
    }

    public double getOwnedAmount() {
        return ownedAmount;
    }

    public void setOwnedAmount(double ownedAmount) {
        this.ownedAmount = ownedAmount;
    }

    public double getAveragePrice() {
        double sum = 0;
        for(CandleStick cs: candleStickArrayList){
            sum += cs.getAveragePrice();
        }
        try{
            return sum/(double)candleStickArrayList.size();
        }catch(ArithmeticException e){
            return 0;
        }
    }

    //====================================================Inner Classes===============================================//
    public class PacketToDraw {
        private final ArrayList<CandleStick> candleSticks = new ArrayList<>();
        private double minPriceInArrayList;
        private double maxPriceInArrayList;

        public PacketToDraw(int amountToDraw) {
            for (int i = Math.max(0, candleStickArrayList.size() - amountToDraw); i < candleStickArrayList.size(); i++) {
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
    }
}
