package GameMVC;

import interfaces.Observer;
import interfaces.pricePredictionStrategy.PricePredictor;
import model.CurrencyModel;
import utilities.Constants;
import utilities.CryptoCurrency;
import utilities.GameTime;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class GameModel implements Serializable {
    @Serial
    private static final long serialVersionUID = -7756479980491019706L;
    private final String name;
    private final int gameSecondsPerFrame = 300; //how many game seconds passes in one frame
    private int delay = 1000; //how many ms it takes to refresh the frame
    private double ownedFiat;
    private final ArrayList<CurrencyModel> currencyModels = new ArrayList<>();
    private CurrencyModel chosenCurrencyModel; // represents currency chosen by user
    private final GameTime gameTime = new GameTime();
    private int simulationSpeed = 1;
    private int numberOfCandleSticksToDraw = 20;
    private boolean isPaused = false;
    private int amountToBuyInput;
    private Double amountToBuyOrSellInput;

    //====================================================Public Methods==============================================//

    public GameModel(String name, double startingFunds, PricePredictor selectedPricePredictor) {
        for (CryptoCurrency cc : Constants.AVAILABLE_CRYPTO_CURRENCIES) {
            currencyModels.add(new CurrencyModel(cc, selectedPricePredictor));
        }
        chosenCurrencyModel = currencyModels.get(0);//default chosen currency is the first one
        this.name = name;
        this.ownedFiat = startingFunds;
    }


    @Override
    public String toString() {
        return name + "      " + " balance: $" + ownedFiat + "          game duration: " + gameTime;
    }

    //====================================================Private Methods=============================================//

    public void updateGame() {
        gameTime.addElapsedTime(gameSecondsPerFrame);
        currencyModels.forEach(cm -> cm.updateCurrencyModel(gameSecondsPerFrame, gameTime));

    }

    //====================================================Getters and setters=========================================//

    public void setPaused(boolean paused) {
        isPaused = paused;

    }

    public void setOwnedFiat(double ownedFiat) {
        this.ownedFiat = ownedFiat;

    }

    public void setChosenCurrencyModel(CurrencyModel chosenCurrencyModel) {
        this.chosenCurrencyModel = chosenCurrencyModel;

    }

    public void setDelay(int delay) {
        this.delay = delay;
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


    public double getValueOfWallet() {
        return currencyModels.stream().mapToDouble(CurrencyModel::getValueOfOwnedAmount).sum();
    }

    public ArrayList<String> getCurrencyNames() {
        ArrayList<String> labels = new ArrayList<>();
        for (CurrencyModel cm : currencyModels) {
            labels.add(cm.getCryptoCurrency().getName());
        }
        return labels;
    }

    public ArrayList<ImageIcon> getIcons() {
        ArrayList<ImageIcon> icons = new ArrayList<>();
        for (CurrencyModel cm : currencyModels) {
            icons.add(cm.getCryptoCurrency().getImageIcon());
        }
        return icons;
    }

    public int getSimulationSpeed() {
        return simulationSpeed;
    }

    public void setSimulationSpeed(int simulationSpeed) {
        this.simulationSpeed = simulationSpeed;
    }

    public int getNumberOfCandleSticksToDraw() {
        return numberOfCandleSticksToDraw;
    }

    public String getName() {
        return name;
    }

    public int getGameSecondsPerFrame() {
        return gameSecondsPerFrame;
    }

    public void setNumberOfCandleSticksToDraw(int numberOfCandleSticksToDraw) {
        this.numberOfCandleSticksToDraw = numberOfCandleSticksToDraw;
    }

    public int getAmountToBuyInput() {
        return amountToBuyInput;
    }

    public void setAmountToBuyInput(int amountToBuyInput) {
        this.amountToBuyInput = amountToBuyInput;
    }

    public double getValueOfAmountToBuyOrSellInput() {
        if(amountToBuyOrSellInput == null){
            return 0;
        }
        return chosenCurrencyModel.getCryptoCurrency().getCurrentPrice()*amountToBuyOrSellInput;
    }

    public void setAmountToBuyOrSellInput(Double amountToBuyOrSellInput) {
        this.amountToBuyOrSellInput = amountToBuyOrSellInput;
    }

    public Double getAmountToBuyOrSellInput() {
        return amountToBuyOrSellInput;
    }
}
