package model;

import Constants.ExCryptocurrencies;
import Interfaces.Observable;
import Interfaces.Observer;
import Utilities.GameTime;
import mainFrame.GameView;

import java.util.ArrayList;

public class GameModel implements Observable, Runnable {
    private ArrayList<Observer> observerArrayList = new ArrayList<>();

    /*private int day;
    private int hour;
    private int minute;
    private int second;*/

    private GameTime gameTime;

    private int timeStep = 1000; //co ile ms aktualizowac stan gry
    private boolean isPaused = false;

    private double ownedFiat = 1000000; //USDT
    private double previousDayOwnedFiat = 0;

    //private final int N_OF_CYRPTOCURRENCIES = 5;//

    /**
     * CurrencyModel class contains status of given CryptoCurrency
     * in particular GameModel. Each currency used in the simulation has its own
     * currency model.
     */

    private ArrayList<CurrencyModel> currencyModels = new ArrayList<>();
    private CurrencyModel choosenCurrencyModel; // represents currency choosen by user

    public GameModel() {
        for (int i = 0; i < ExCryptocurrencies.availableCryptoCurrencies.size(); i++) {
            currencyModels.add(new CurrencyModel(i, ExCryptocurrencies.availableCryptoCurrencies.get(i)));
        }
        choosenCurrencyModel = currencyModels.get(0);//default choosen currency is the 0th one
        gameTime = new GameTime(0,0,0,0);
        this.addObserver(new GameView(this));
    }

    @Override
    public void run() {

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

    public ArrayList<Observer> getObserverArrayList() {
        return observerArrayList;
    }

    public void setObserverArrayList(ArrayList<Observer> observerArrayList) {
        this.observerArrayList = observerArrayList;
    }

    public GameTime getGameTime() {
        return gameTime;
    }

    public void setGameTime(GameTime gameTime) {
        this.gameTime = gameTime;
    }

    public int getTimeStep() {
        return timeStep;
    }

    public void setTimeStep(int timeStep) {
        this.timeStep = timeStep;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public double getOwnedFiat() {
        return ownedFiat;
    }

    public void setOwnedFiat(double ownedFiat) {
        this.ownedFiat = ownedFiat;
    }

    public double getPreviousDayOwnedFiat() {
        return previousDayOwnedFiat;
    }

    public void setPreviousDayOwnedFiat(double previousDayOwnedFiat) {
        this.previousDayOwnedFiat = previousDayOwnedFiat;
    }

    public ArrayList<CurrencyModel> getCurrencyModels() {
        return currencyModels;
    }

    public void setCurrencyModels(ArrayList<CurrencyModel> currencyModels) {
        this.currencyModels = currencyModels;
    }

    public CurrencyModel getChoosenCurrencyModel() {
        return choosenCurrencyModel;
    }

    public void setChoosenCurrencyModel(CurrencyModel choosenCurrencyModel) {
        this.choosenCurrencyModel = choosenCurrencyModel;
    }
}
