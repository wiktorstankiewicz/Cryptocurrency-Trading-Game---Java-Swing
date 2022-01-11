package model;

import utilities.Constants;
import interfaces.Observable;
import interfaces.Observer;
import utilities.CryptoCurrency;
import utilities.GameTime;

import javax.swing.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class GameModel implements Observable, Runnable, Serializable {
    @Serial
    private static final long serialVersionUID = -7756479980491019706L;
    private transient ArrayList<Observer> observerArrayList = new ArrayList<>();
    private final String name;
    private final int gameSecondsPerFrame = 300; //how many game seconds passes in one frame
    private int delay = 1000; //how many ms it takes to refresh the frame
    private double ownedFiat;
    private boolean isPaused = false;
    private Timer frameRefreshingTimer;
    private final ArrayList<CurrencyModel> currencyModels = new ArrayList<>();
    private CurrencyModel chosenCurrencyModel; // represents currency chosen by user
    private final GameTime gameTime = new GameTime();

    //====================================================Public Methods==============================================//

    public GameModel(String name, double startingFunds) {
        for (CryptoCurrency cc : Constants.AVAILABLE_CRYPTO_CURRENCIES) {
            currencyModels.add(new CurrencyModel(cc));
        }
        chosenCurrencyModel = currencyModels.get(0);//default chosen currency is the first one
        this.name = name;
        this.ownedFiat = startingFunds;
    }

    @Override
    public void run() {
        frameRefreshingTimer = new Timer(delay, e -> updateGame());
        frameRefreshingTimer.setInitialDelay(10);
        if (isPaused) {
            frameRefreshingTimer.stop();
        } else {
            frameRefreshingTimer.start();
        }
    }

    @Override
    public void addObserver(Observer observer) {
        if (observerArrayList == null) {
            observerArrayList = new ArrayList<>();
        }
        observerArrayList.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        if (observerArrayList == null) {
            observerArrayList = new ArrayList<>();
        }
        observerArrayList.remove(observer);
    }

    @Override
    public void notifyObservers() {
        if (observerArrayList == null) {
            observerArrayList = new ArrayList<>();
        }
        for (Observer o : observerArrayList) {
            o.update();
        }
    }

    @Override
    public String toString() {
        return name + "      " + " balance: $" + ownedFiat + "          game duration: " + gameTime;
    }

    //====================================================Private Methods=============================================//

    private void updateGame() {
        gameTime.addElapsedTime(gameSecondsPerFrame);
        currencyModels.forEach(cm -> cm.updateCurrencyModel(gameSecondsPerFrame, gameTime));
        this.notifyObservers();
    }

    //====================================================Getters and setters=========================================//

    public void setPaused(boolean paused) {
        isPaused = paused;
        notifyObservers();
    }

    public void setOwnedFiat(double ownedFiat) {
        this.ownedFiat = ownedFiat;
        notifyObservers();
    }

    public void setChosenCurrencyModel(CurrencyModel chosenCurrencyModel) {
        this.chosenCurrencyModel = chosenCurrencyModel;
        notifyObservers();
    }

    public void setDelay(int delay) {
        this.delay = delay;
        frameRefreshingTimer.setDelay(delay);
        notifyObservers();
    }

    public int getDelay() {
        return delay;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public double getOwnedFiat() {
        return ownedFiat;
    }

    public GameTime getGameTime() {
        return gameTime;
    }

    public ArrayList<CurrencyModel> getCurrencyModels() {
        return currencyModels;
    }

    public CurrencyModel getChosenCurrencyModel() {
        return chosenCurrencyModel;
    }

    public Timer getFrameRefreshingTimer() {
        return frameRefreshingTimer;
    }

    public double getValueOfWallet() {
        return currencyModels.stream().mapToDouble(CurrencyModel::getValueOfOwnedAmount).sum();
    }
}
