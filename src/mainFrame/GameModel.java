package mainFrame;

import Constants.ExCryptocurrencies;
import Interfaces.Observable;
import Interfaces.Observer;
import model.CryptoCurrency;
import plot.CandleStick;

import java.util.ArrayList;

public class GameModel implements Observable, Runnable {
    private ArrayList<Observer> observerArrayList = new ArrayList<>();

    /*private int day;
    private int hour;
    private int minute;
    private int second;*/

    private GameTime gameTime;



    private int timeStep; //co ile s aktualizowac stan gry

    private double ownedFiat = 1000000; //USDT
    private double previousDayOwnedFiat = 0;

    //private final int N_OF_CYRPTOCURRENCIES = 5;//

    /**
     * CurrencyModel class contains status of given CryptoCurrency
     * in particular GameModel. Each currency used in the simluation has its own
     * currency model.
     */
    private class CurrencyModel {
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

    private ArrayList<CurrencyModel> currencyModels = new ArrayList<>();
    private CurrencyModel choosenCurrencyModel; // represents currency choosen by user

    public GameModel() {
        for (int i = 0; i < ExCryptocurrencies.availableCryptoCurrencies.size(); i++) {
            currencyModels.add(new CurrencyModel(i, ExCryptocurrencies.availableCryptoCurrencies.get(i)));
        }
        choosenCurrencyModel = currencyModels.get(0);//default choosen currency is the 0th one

    }

    @Override
    public void run() {
        CryptoCurrency btc = new CryptoCurrency("Btc");
        CryptoCurrency eth = new CryptoCurrency("Eth");
        CurrencyModel currencyModel = new CurrencyModel(0, btc);
    }

    @Override
    public void addObserver(Observer observer) {
        observerArrayList.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observerArrayList.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observerArrayList) {
            o.update();
        }
    }
}
