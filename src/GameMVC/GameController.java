package GameMVC;

import model.CurrencyModel;
import utilities.Constants;
import utilities.CryptoCurrency;
import utilities.GameTime;


import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.Element;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Locale;


public class GameController implements Runnable {
    private final GamePanelView gamePanelView;
    private final GameModel gameModel;
    private final DocumentListener amountToBuyDocumentListener = new MyDocumentListener();
    private Timer timer;

    public GameController(GamePanelView gamePanelView, GameModel gameModel) {
        this.gamePanelView = gamePanelView;
        this.gameModel = gameModel;
    }

    @Override
    public void run() {
        addListenersToButtons();
        gamePanelView.addDocumentListenerToAmountToBuyOrSellTextField(amountToBuyDocumentListener);
        amountToBuyDocumentListener.changedUpdate(new EmptyDocumentEvent());
        updateGamePanel();
        timer = new Timer(gameModel.getDelay(), e -> {
            gameModel.updateGame();
            updateGamePanel();
        });
        if(!gameModel.isPaused()){
            timer.start();
        }
        timer.setInitialDelay(10);

    }

    private void addListenersToButtons() {
        ArrayList<ActionListener> currencyButtonListeners = new ArrayList<>();
        for(int i = 0; i< gameModel.getCurrencyModels().size(); i++){
            currencyButtonListeners.add(new CurrencyButtonActionListener(gameModel.getCurrencyModels().get(i),
                    gamePanelView.getCryptoCurrencyButtons().get(i)));
        }
        gamePanelView.addActionListenersToCurrencyButtons(currencyButtonListeners);
        gamePanelView.getPauseButton().addActionListener(new PauseButtonPressed());
        gamePanelView.getFastForwardButton().addMouseListener(new FastForwardButtonPressed());
        gamePanelView.getNumberOfCandleSticksButton().addMouseListener(new NumberOfCandleSticksButtonListener());
        gamePanelView.getBuyButton().addActionListener(new BuyButtonPressed());
        gamePanelView.getSellButton().addActionListener(new SellButtonPressed());
    }

    public void updateGamePanel() {
        GameTime currentTime = gameModel.getGameTime();
        CryptoCurrency cryptoCurrency = gameModel.getChosenCurrencyModel().getCryptoCurrency();
        double ownedAmount = gameModel.getChosenCurrencyModel().getOwnedAmount();
        double currentPrice = cryptoCurrency.getCurrentPrice();
        double ownedFiat = gameModel.getOwnedFiat();

        gamePanelView.setGameTimeLabelText(currentTime.toString());
        gamePanelView.setOwnedCryptoText(String.valueOf(ownedAmount));
        gamePanelView.setValueOfOwnedCryptoText("$" + Constants.DOUBLE_FORMATTER.format(ownedAmount *
                currentPrice));
        gamePanelView.setFiatBalanceText("$" + Constants.DOUBLE_FORMATTER.format(ownedFiat));
        gamePanelView.setChosenCurrencyText("Waluta: " +
                cryptoCurrency.getName().toUpperCase(Locale.ROOT));
        gamePanelView.setChosenCurrencyValueText("Cena: " + "$" +
                        Constants.DOUBLE_FORMATTER.format(currentPrice).toUpperCase(Locale.ROOT),
                gameModel.getChosenCurrencyModel().getPacketToDraw(1).getCandleSticks().get(0).getColor());
        gamePanelView.setValueOfCryptosText("$" + Constants.DOUBLE_FORMATTER.format(gameModel.getValueOfWallet()));
        amountToBuyDocumentListener.changedUpdate(new EmptyDocumentEvent());
        gamePanelView.setTotalValueText("$" + Constants.DOUBLE_FORMATTER.format(gameModel.getValueOfWallet() + ownedFiat));
        gamePanelView.setSuggestedActionState(gameModel.getChosenCurrencyModel().getSugestedAction());
        gamePanelView.getPlotPanel().update(gameModel.getNumberOfCandleSticksToDraw(),
                gameModel.getChosenCurrencyModel().getPacketToDraw(gameModel.getNumberOfCandleSticksToDraw()));
        gamePanelView.setFastForwardButtonState(1000 / gameModel.getDelay());
        gamePanelView.setPauseButtonState(gameModel.isPaused());
        gamePanelView.setNumberOfCandleSticksButtonState(gameModel.getNumberOfCandleSticksToDraw());
    }

    private class FastForwardButtonPressed implements MouseListener {
        private int position = 0;
        private final int[] delays = {1000, 500, 200, 100, 50, 20, 10};

        @Override
        public void mouseClicked(MouseEvent e) {
            gamePanelView.getFastForwardButton().setSelected(true);
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
            timer.setDelay(gameModel.getDelay());
            updateGamePanel();
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
                gamePanelView.setPauseButtonState(true);
                return;
            }
            timer.start();
            gameModel.setPaused(false);
            updateGamePanel();
        }
    }

    private class BuyButtonPressed implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            double amountToBuy = Double.parseDouble(gamePanelView.getAmountToBuyOrSellTextField().getText());
            double value = amountToBuy * gameModel.getChosenCurrencyModel().getCryptoCurrency().getCurrentPrice();
            double accountBalance = gameModel.getOwnedFiat();

            if (value <= accountBalance) {
                gameModel.setOwnedFiat(accountBalance - value);
                gameModel.getChosenCurrencyModel().setOwnedAmount(gameModel.getChosenCurrencyModel().getOwnedAmount() + amountToBuy);
            } else {
                gamePanelView.showErrorMessage("Nie posiadasz tyle pieniędzy!");
            }
            updateGamePanel();
        }
    }

    private class SellButtonPressed implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            double amountToSell = Double.parseDouble(gamePanelView.getAmountToBuyOrSellTextField().getText());
            double value = amountToSell * gameModel.getChosenCurrencyModel().getCryptoCurrency().getCurrentPrice();

            double accountBalance = gameModel.getOwnedFiat();
            if (amountToSell <= gameModel.getChosenCurrencyModel().getOwnedAmount()) {
                gameModel.setOwnedFiat(accountBalance + value);
                gameModel.getChosenCurrencyModel().setOwnedAmount(gameModel.getChosenCurrencyModel().getOwnedAmount() - amountToSell);
            } else {

                gamePanelView.showErrorMessage("Nie posiadasz tyle waluty " +
                        gameModel.getChosenCurrencyModel().getCryptoCurrency().getName() + "!");
            }
            updateGamePanel();
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
            gameModel.setNumberOfCandleSticksToDraw(numbers[position]);
            updateGamePanel();
        }


        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    private class MyDocumentListener implements DocumentListener {
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
                text = "$" + Constants.DOUBLE_FORMATTER.format(Double.parseDouble(gamePanelView.getAmountToBuyOrSellTextField().getText()) *
                        gameModel.getChosenCurrencyModel().getCryptoCurrency().getCurrentPrice());
                if (Double.parseDouble(gamePanelView.getAmountToBuyOrSellTextField().getText()) <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException numberFormatException) {
                text = "";
            }
            gamePanelView.getValueOfAmountToBuyOrSell().setText(text);
        }
    }

    private class CurrencyButtonActionListener implements ActionListener {
        private final CurrencyModel currencyModel;
        private final JButton currencyButton;

        public CurrencyButtonActionListener(CurrencyModel currencyModel, JButton currencyButton) {
            this.currencyModel = currencyModel;
            this.currencyButton = currencyButton;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            gameModel.setChosenCurrencyModel(currencyModel);
            for (JButton jButton : gamePanelView.getCryptoCurrencyButtons()) {
                jButton.setBackground(Color.LIGHT_GRAY);
            }
            currencyButton.setBackground(Color.GRAY);
            updateGamePanel();
        }
    }

}
