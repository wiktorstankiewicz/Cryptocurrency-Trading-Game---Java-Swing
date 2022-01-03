package model;

import Constants.ExCryptocurrencies;
import Interfaces.Observable;
import Interfaces.Observer;
import Utilities.CandleStick;
import Utilities.CryptoCurrency;
import Utilities.GameTime;

import javax.swing.*;
import java.util.ArrayList;

public class GameModel implements Observable, Runnable {
    private ArrayList<Observer> observerArrayList = new ArrayList<>();

    /*private int day;
    private int hour;
    private int minute;
    private int second;*/

    private GameTime gameTime;

    private int gameSecondsPerFrame = 1; //how many game seconds passes in one frame
    private int delay = 1000; //how many ms it takes to refresh the frame
    private int durationOfOneCandleStick = 5; // how many game seconds does one CandleStick represent
    private boolean isPaused = false;

    private int amountToDraw = 20;

    private double ownedFiat = 1000000; //USDT
    private double previousDayOwnedFiat = 0;

    private Timer frameRefreshingTimer;

    //private final int N_OF_CYRPTOCURRENCIES = 5;//

    private ArrayList<CurrencyModel> currencyModels = new ArrayList<>();
    private CurrencyModel choosenCurrencyModel; // represents currency choosen by user

    public GameModel() {
        /*for (int i = 0; i < ExCryptocurrencies.availableCryptoCurrencies.size(); i++) {
            currencyModels.add(new CurrencyModel(i, ExCryptocurrencies.availableCryptoCurrencies.get(i)));
        }*/
        currencyModels.add(new CurrencyModel(ExCryptocurrencies.availableCryptoCurrencies.get(0)));
        choosenCurrencyModel = currencyModels.get(0);//default choosen currency is the 0th one
        gameTime = new GameTime();
    }

    @Override
    public void run() {
        frameRefreshingTimer = new Timer(delay, e -> {
            updateGame();
        });
        frameRefreshingTimer.setInitialDelay(10);
        frameRefreshingTimer.start();
    }

    private void updateGame() {
        gameTime.addElapsedTime(gameSecondsPerFrame);
        //System.out.println(choosenCurrencyModel.getCandleStickArrayList().size());
        checkIfCreateCandleStick();
        choosenCurrencyModel.update(gameSecondsPerFrame, amountToDraw);
        this.notifyObservers();
    }

    private void checkIfCreateCandleStick() {
        CryptoCurrency cryptoCurrency = choosenCurrencyModel.getCryptoCurrency();
        if (choosenCurrencyModel.getCandleStickArrayList().size() == 0) {
            choosenCurrencyModel.getCandleStickArrayList().add(new CandleStick(cryptoCurrency,
                    gameTime));
            return;
        }
        int last = choosenCurrencyModel.getCandleStickArrayList().size() - 1;
        //System.out.println("time of creation: " + choosenCurrencyModel.getCandleStickArrayList().get(last).getOpenTime().valueOf());
        if (gameTime.valueOf() - choosenCurrencyModel.getCandleStickArrayList().get(last).getOpenTime().valueOf()
                >= durationOfOneCandleStick) {
            choosenCurrencyModel.getCandleStickArrayList().get(last).setClosed(true);
            choosenCurrencyModel.getCandleStickArrayList().get(last).setCloseTime(gameTime);
            choosenCurrencyModel.getCandleStickArrayList().add(new CandleStick(cryptoCurrency,
                    gameTime));
        }
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

    public int getGameSecondsPerFrame() {
        return gameSecondsPerFrame;
    }

    public void setGameSecondsPerFrame(int gameSecondsPerFrame) {
        this.gameSecondsPerFrame = gameSecondsPerFrame;
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

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
        frameRefreshingTimer.setDelay(delay);
    }

    public int getDurationOfOneCandleStick() {
        return durationOfOneCandleStick;
    }

    public void setDurationOfOneCandleStick(int durationOfOneCandleStick) {
        this.durationOfOneCandleStick = durationOfOneCandleStick;
    }

    public Timer getFrameRefreshingTimer() {
        return frameRefreshingTimer;
    }

    public void setFrameRefreshingTimer(Timer frameRefreshingTimer) {
        this.frameRefreshingTimer = frameRefreshingTimer;
    }

}
