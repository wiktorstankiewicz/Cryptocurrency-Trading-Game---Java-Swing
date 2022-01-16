package controllers;

import model.GameModel;
import utilities.Constants;
import utilities.GameTime;
import view.gamePanel.GamePanel;
import view.gamePanel.PlotPanel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.Element;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Locale;


public class GameController {
    private GamePanel gamePanel;
    private GameModel gameModel;

    private PlotPanelController plotPanelController;
    private Timer timer;

    public GameController(GamePanel gamePanel, GameModel gameModel) {
        this.gamePanel = gamePanel;
        this.gameModel = gameModel;
        PlotPanel plotPanel = new PlotPanel();
        gamePanel.addPlotPanel(plotPanel);

        plotPanelController = new PlotPanelController(plotPanel, gameModel);
        this.run();
    }

    public void run(){
        timer = new Timer(gameModel.getDelay(), e -> {
           gameModel.updateGame();
        });
        if(gameModel.isPaused()){
            timer.stop();
        }else{
            timer.start();
        }
    }

    public void updateGamePanel(){
        GameTime currentTime = gameModel.getGameTime();
        gamePanel.setGameTimeLabelText(currentTime.toString());
        //todo
        //gamePanel.invokeDocumentListenerEvent();
        gamePanel.setOwnedCryptoText(String.valueOf(gameModel.getChosenCurrencyModel().getOwnedAmount()));
        gamePanel.setValueOfOwnedCryptoText("$" + Constants.DOUBLE_FORMATTER.format(gameModel.getChosenCurrencyModel().getOwnedAmount() *
                gameModel.getChosenCurrencyModel().getCryptoCurrency().getCurrentPrice()));
        gamePanel.setFiatBalanceText("$" + Constants.DOUBLE_FORMATTER.format(gameModel.getOwnedFiat()));
        gamePanel.setChosenCurrencyText("Waluta: " +
                gameModel.getChosenCurrencyModel().getCryptoCurrency().getName().toUpperCase(Locale.ROOT));
        gamePanel.setChosenCurrencyValueText("Cena: " + "$" +
                        Constants.DOUBLE_FORMATTER.format(gameModel.getChosenCurrencyModel().getCryptoCurrency().getCurrentPrice()).toUpperCase(Locale.ROOT),
                gameModel.getChosenCurrencyModel().getPacketToDraw(1).getCandleSticks().get(0).getColor());
        gamePanel.setValueOfCryptosText("$" + Constants.DOUBLE_FORMATTER.format(gameModel.getValueOfWallet()));
        gamePanel.setTotalValueText("$" + Constants.DOUBLE_FORMATTER.format(gameModel.getValueOfWallet() + gameModel.getOwnedFiat()));
    }

    private class FastForwardButtonPressed implements MouseListener {
        private int position = 0;
        private final int[] delays = {1000, 500, 200, 100, 50, 20, 10};

        @Override
        public void mouseClicked(MouseEvent e) {
            gamePanel.getFastForwardButton().setSelected(true);

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            int direction;
            if (SwingUtilities.isRightMouseButton(e)) {
                direction = -1;
            } else {
                direction = 1;
            }
            if (position == 0 && direction == -1) {
                position = delays.length - 1;
            } else {
                position = (position + direction) % delays.length;
            }
            gameModel.setDelay(delays[position]);
            gamePanel.setFastForwardButtonText("x " + 1000 / delays[position]);
            gamePanel.getFastForwardButton().setSelected(false);
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    private class PauseButtonPressed implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (timer.isRunning()) {
                timer.stop();
                gameModel.setPaused(true);
                gamePanel.getPauseButton().setText("PAUSED");
                gamePanel.getPauseButton().setForeground(Color.RED);
                return;
            }
            timer.restart();
            gameModel.setPaused(false);
            gamePanel.getPauseButton().setText("Pause");
            gamePanel.getPauseButton().setForeground(Color.BLACK);
        }
    }

    private class BuyButtonPressed implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            double amountToBuy = Double.parseDouble(gamePanel.getAmountToBuyOrSellTextField().getText());
            double value = amountToBuy * gameModel.getChosenCurrencyModel().getCryptoCurrency().getCurrentPrice();
            double accountBalance = gameModel.getOwnedFiat();

            if (value <= accountBalance) {
                gameModel.setOwnedFiat(accountBalance - value);
                gameModel.getChosenCurrencyModel().setOwnedAmount(gameModel.getChosenCurrencyModel().getOwnedAmount() + amountToBuy);
                gameModel.notifyObservers();
            } else {
                gamePanel.showErrorMessage("Nie posiadasz tyle pieniędzy!");
            }
        }
    }

    private class SellButtonPressed implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            double amountToSell = Double.parseDouble(gamePanel.getAmountToBuyOrSellTextField().getText());
            double value = amountToSell * gameModel.getChosenCurrencyModel().getCryptoCurrency().getCurrentPrice();

            double accountBalance = gameModel.getOwnedFiat();
            if (amountToSell <= gameModel.getChosenCurrencyModel().getOwnedAmount()) {
                gameModel.setOwnedFiat(accountBalance + value);
                gameModel.getChosenCurrencyModel().setOwnedAmount(gameModel.getChosenCurrencyModel().getOwnedAmount() - amountToSell);
                gameModel.notifyObservers();
            } else {

                gamePanel.showErrorMessage("Nie posiadasz tyle waluty " +
                        gameModel.getChosenCurrencyModel().getCryptoCurrency().getName() + "!");
            }
        }
    }

    private static class EmptyDocumentEvent implements DocumentEvent {
        @Override
        public int getOffset() {
            return 0;
        }

        @Override
        public int getLength() {
            return 0;
        }

        @Override
        public Document getDocument() {
            return null;
        }

        @Override
        public EventType getType() {
            return null;
        }

        @Override
        public ElementChange getChange(Element elem) {
            return null;
        }
    }

    public class NumberOfCandleSticksButtonListener implements MouseListener {
        private int position = 0;
        private final int[] numbers = {20, 50, 100, 150, 200};

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            int direction;
            if (SwingUtilities.isRightMouseButton(e)) {
                direction = -1;
            } else {
                direction = 1;
            }
            if (position == 0 && direction == -1) {
                position = numbers.length - 1;
            } else {
                position = (position + direction) % numbers.length;
            }
            gamePanel.getNumberOfCandleSticksButton().setText("Ilość świeczek: " + numbers[position]);
            plotPanelController.updatePlotPanel(numbers[position]);
            gamePanel.getNumberOfCandleSticksButton().setSelected(false);
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    private class myDocumentListener implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {
            changedUpdate(e);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            changedUpdate(e);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            String text;
            try {
                text = "$" + Constants.DOUBLE_FORMATTER.format(Double.parseDouble(gamePanel.getAmountToBuyOrSellTextField().getText()) *
                        gameModel.getChosenCurrencyModel().getCryptoCurrency().getCurrentPrice());
                if (Double.parseDouble(gamePanel.getAmountToBuyOrSellTextField().getText()) <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException numberFormatException) {
                text = "";
            }
            gamePanel.getValueOfAmountToBuyOrSell().setText(text);
        }
    };

}
