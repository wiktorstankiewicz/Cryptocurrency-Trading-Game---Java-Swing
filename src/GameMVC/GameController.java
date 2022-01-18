package GameMVC;

import model.CurrencyModel;
import utilities.Constants;
import utilities.GameTime;
import view.gamePanel.PlotPanel;

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
    private GamePanel gamePanel;
    private GameModel gameModel;
    private DocumentListener documentListener = new MyDocumentListener();

    private Timer timer;

    public GameController(GamePanel gamePanel, GameModel gameModel) {
        this.gamePanel = gamePanel;
        this.gameModel = gameModel;
    }

    @Override
    public void run() {
        addListenersToButtons();
        gamePanel.addDocumentListenerToAmountToBuyOrSellTextField(documentListener);
        documentListener.changedUpdate(new EmptyDocumentEvent());
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
                    gamePanel.getCryptoCurrencyButtons().get(i)));
        }
        gamePanel.addActionListenersToCurrencyButtons(currencyButtonListeners);
        gamePanel.getPauseButton().addActionListener(new PauseButtonPressed());
        gamePanel.getFastForwardButton().addMouseListener(new FastForwardButtonPressed());
        gamePanel.getNumberOfCandleSticksButton().addMouseListener(new NumberOfCandleSticksButtonListener());
        gamePanel.getBuyButton().addActionListener(new BuyButtonPressed());
        gamePanel.getSellButton().addActionListener(new SellButtonPressed());
    }

    public void updateGamePanel() {
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
        documentListener.changedUpdate(new EmptyDocumentEvent());
        gamePanel.setTotalValueText("$" + Constants.DOUBLE_FORMATTER.format(gameModel.getValueOfWallet() + gameModel.getOwnedFiat()));

        gamePanel.getPlotPanel().updates(gameModel.getNumberOfCandleSticksToDraw(),
                gameModel.getChosenCurrencyModel().getPacketToDraw(gameModel.getNumberOfCandleSticksToDraw()));
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
            timer.setDelay(gameModel.getDelay());
            gamePanel.setFastForwardButtonState(1000 / delays[position]);
            gamePanel.getFastForwardButton().setSelected(false);
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
                gamePanel.setPauseButtonState(true);
                return;
            }
            timer.start();
            gameModel.setPaused(false);
            gamePanel.setPauseButtonState(false);
            updateGamePanel();
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
            } else {
                gamePanel.showErrorMessage("Nie posiadasz tyle pieniędzy!");
            }
            updateGamePanel();
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
            } else {

                gamePanel.showErrorMessage("Nie posiadasz tyle waluty " +
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
            gamePanel.setNumberOfCandleSticksButtonState(gameModel.getNumberOfCandleSticksToDraw());
            //todo update plotpanel
            gamePanel.getNumberOfCandleSticksButton().setSelected(false);
            gamePanel.getPlotPanel().updates(numbers[position],
                    gameModel.getChosenCurrencyModel().getPacketToDraw(numbers[position]));
        }

        public void updatePlotPanel(int numberOfCandleSticksToPaint) {

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
    }

    private class MyKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyChar() == ' ') {
                gamePanel.getPauseButton().doClick();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

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
            for (JButton jButton : gamePanel.getCryptoCurrencyButtons()) {
                jButton.setBackground(Color.LIGHT_GRAY);
            }
            currencyButton.setBackground(Color.GRAY);

            updateGamePanel();
        }
    }

}
