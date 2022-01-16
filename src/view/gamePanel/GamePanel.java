package view.gamePanel;

import utilities.Constants;
import interfaces.Observer;
import utilities.GameTime;
import model.CurrencyModel;
import model.GameModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.Element;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Locale;

import static utilities.Utilities.addGridOfJPanels;

public class GamePanel extends JPanel implements Observer {
    private final JPanel centerPanel = new JPanel();
    private final JPanel topPanel = new JPanel();
    private final JPanel bottomPanel = new JPanel();
    private final JPanel rightPanel = new JPanel();
    private final JPanel leftPanel = new JPanel();
    private final ArrayList<JButton> cryptoCurrencyButtons = new ArrayList<>();
    private final JButton fastForwardButton = new JButton();
    private final JButton pauseButton = new JButton();
    private final JButton numberOfCandleSticksButton = new JButton();
    private final JLabel gameTimeLabel = new JLabel();
    private JLabel ownedCrypto;
    private JLabel valueOfOwnedCrypto;
    private JTextField amountToBuyOrSellTextField;
    private JTextField valueOfAmountToBuyOrSell;
    private final JLabel fiatBalance = new JLabel();
    private static DocumentListener documentListener;
    private JLabel chosenCurrencyText =  new JLabel();
    private JLabel chosenCurrencyValue = new JLabel();
    private final JLabel valueOfCryptos = new JLabel();
    private final JLabel totalValue = new JLabel();

    private PlotPanel plotPanel;
    private final GameModel gameModel;

    //====================================================Public Methods==============================================//

    public GamePanel(GameModel gameModel) {
        this.gameModel = gameModel;
        this.setLayout(new BorderLayout());
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == ' ') {
                    pauseButton.doClick();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        this.setFocusable(true);
        documentListener = new DocumentListener() {
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
                    text = "$" + Constants.DOUBLE_FORMATTER.format(Double.parseDouble(amountToBuyOrSellTextField.getText()) *
                            gameModel.getChosenCurrencyModel().getCryptoCurrency().getCurrentPrice());
                    if (Double.parseDouble(amountToBuyOrSellTextField.getText()) <= 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException numberFormatException) {
                    text = "";
                }
                valueOfAmountToBuyOrSell.setText(text);
            }
        };
        initCurrencyButtons(gameModel);
        initBorderLayoutPanels();
        gameModel.addObserver(this);
        gameModel.addObserver(this.plotPanel);
        gameModel.run();
        this.update();
    }

    @Override
    public void update() {
        GameTime currentTime = gameModel.getGameTime();
        setGameTimeLabelText(currentTime.toString());
        documentListener.changedUpdate(new EmptyDocumentEvent());
        setOwnedCryptoText(String.valueOf(gameModel.getChosenCurrencyModel().getOwnedAmount()));
        setValueOfOwnedCryptoText("$" + Constants.DOUBLE_FORMATTER.format(gameModel.getChosenCurrencyModel().getOwnedAmount() *
                gameModel.getChosenCurrencyModel().getCryptoCurrency().getCurrentPrice()));
        setFiatBalanceText("$" + Constants.DOUBLE_FORMATTER.format(gameModel.getOwnedFiat()));
        setChosenCurrencyText("Waluta: " +
                gameModel.getChosenCurrencyModel().getCryptoCurrency().getName().toUpperCase(Locale.ROOT));
        setChosenCurrencyValueText("Cena: " + "$" +
                Constants.DOUBLE_FORMATTER.format(gameModel.getChosenCurrencyModel().getCryptoCurrency().getCurrentPrice()).toUpperCase(Locale.ROOT),
                gameModel.getChosenCurrencyModel().getPacketToDraw(1).getCandleSticks().get(0).getColor());
        setValueOfCryptosText("$" + Constants.DOUBLE_FORMATTER.format(gameModel.getValueOfWallet()));
        setTotalValueText("$" + Constants.DOUBLE_FORMATTER.format(gameModel.getValueOfWallet() + gameModel.getOwnedFiat()));
    }

    private void setTotalValueText(String text) {
        totalValue.setText(text);
    }

    private void setValueOfCryptosText(String text) {
        valueOfCryptos.setText(text);
    }

    private void setChosenCurrencyValueText(String text, PriceDirection priceDirection) {
        Color color = switch (priceDirection){
            case UP -> Color.GREEN;
            case DOWN -> Color.RED;
        };
        chosenCurrencyValue.setText(text);
        chosenCurrencyValue.setForeground(color);
    }

    private void setChosenCurrencyText(String text) {
        chosenCurrencyText.setText(text);

    }

    private void setFiatBalanceText(String text) {
        fiatBalance.setText(text);
    }

    private void setValueOfOwnedCryptoText(String text) {
        valueOfOwnedCrypto.setText(text);
    }

    private void setOwnedCryptoText(String ownedAmount) {
        ownedCrypto.setText(ownedAmount);
    }

    private void setGameTimeLabelText(String text) {
        gameTimeLabel.setText(text);
    }


    //====================================================Private Methods==============================================//

    private void initCurrencyButtons(GameModel gameModel) {
        for (CurrencyModel cm : gameModel.getCurrencyModels()) {
            JButton buffer;
            buffer = new JButton(cm.getCryptoCurrency().getName());
            buffer.setSize(new Dimension(300, 50));
            buffer.setFocusable(false);
            buffer.setBackground(Color.LIGHT_GRAY);
            buffer.addActionListener(e -> {
                gameModel.setChosenCurrencyModel(cm);
                for (JButton jButton : cryptoCurrencyButtons) {
                    jButton.setBackground(Color.LIGHT_GRAY);
                    jButton.setBorder(null);
                }
                buffer.setBackground(Color.GRAY);
                buffer.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
            });
            cryptoCurrencyButtons.add(buffer);
            buffer.setIcon(buffer.getIcon());
        }
    }

    private void initBorderLayoutPanels() {
        initCenterPanel();
        initTopPanel();
        initBottomPanel();
        initRightPanel();
        initLeftPanel();
    }

    private void initLeftPanel() {
        JPanel[][] grid = addGridOfJPanels(5, 2, leftPanel, 10, 10);

        leftPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        leftPanel.setBackground(Color.BLACK);
        for (int i = 0; i < cryptoCurrencyButtons.size(); i++) {
            grid[i][0].setBackground(null);
            grid[i][1].setBackground(null);

            grid[i][0].setLayout(new BorderLayout());
            grid[i][0].add(cryptoCurrencyButtons.get(i));
            grid[i][1].add(new JLabel(gameModel.getCurrencyModels().get(i).getCryptoCurrency().getImageIcon()));
        }
        this.add(leftPanel, BorderLayout.WEST);
    }

    private void initRightPanel() {
        JPanel[][] grid = addGridOfJPanels(5, 2, rightPanel, 5, 5);
        JLabel text1 = new JLabel("Posiadane $: ");
        JLabel text2 = new JLabel("Wartość walut: ");
        JLabel text3 = new JLabel("Łącznie: ");
        totalValue.setForeground(Color.WHITE);
        totalValue.setHorizontalAlignment(SwingConstants.CENTER);
        valueOfCryptos.setForeground(Color.WHITE);
        valueOfCryptos.setHorizontalAlignment(SwingConstants.CENTER);
        text1.setHorizontalAlignment(SwingConstants.CENTER);
        text1.setForeground(Color.WHITE);
        rightPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        rightPanel.setBackground(Color.BLACK);
        fiatBalance.setHorizontalAlignment(JTextField.CENTER);
        setFiatBalanceText("$" + Constants.DOUBLE_FORMATTER.format(gameModel.getOwnedFiat()));
        fiatBalance.setForeground(Color.WHITE);

        text2.setHorizontalAlignment(SwingConstants.CENTER);
        text2.setForeground(Color.WHITE);

        text3.setForeground(Color.WHITE);
        text3.setHorizontalAlignment(SwingConstants.CENTER);

        grid[0][0].add(text1);
        grid[0][1].add(fiatBalance);

        grid[1][0].add(text2);
        grid[1][1].add(valueOfCryptos);

        grid[2][0].add(text3);
        grid[2][1].add(totalValue);
        this.add(rightPanel, BorderLayout.EAST);
    }

    private void initBottomPanel() {
        JPanel[][] grid = addGridOfJPanels(3, 6, bottomPanel, 10, 10);
        JLabel ownedAmountText = new JLabel("Posiadana ilość:");
        JLabel ownedAmountValueText = new JLabel("Wartość:");
        JLabel description1 = new JLabel("Ilość do kupienia/sprzedania");
        JLabel description2 = new JLabel("Wartość");
        JButton buyButton = new JButton("ZAKUP");
        JButton sellButton = new JButton("SPRZEDAŻ");
        chosenCurrencyText = new JLabel("Waluta: " +
                gameModel.getChosenCurrencyModel().getCryptoCurrency().getName().toUpperCase(Locale.ROOT));
        chosenCurrencyText.setForeground(Color.WHITE);
        chosenCurrencyText.setHorizontalAlignment(SwingConstants.CENTER);
        chosenCurrencyValue = new JLabel("Cena: " +
                Constants.DOUBLE_FORMATTER.format(gameModel.getChosenCurrencyModel().getCryptoCurrency().getCurrentPrice()).toUpperCase(Locale.ROOT));
        chosenCurrencyValue.setHorizontalAlignment(SwingConstants.CENTER);
        description1.setForeground(Color.WHITE);
        description1.setHorizontalAlignment(SwingConstants.CENTER);
        description2.setForeground(Color.WHITE);
        description2.setHorizontalAlignment(SwingConstants.CENTER);

        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.setBorder(BorderFactory.createLineBorder(bottomPanel.getBackground(), 10));

        ownedCrypto = new JLabel(String.valueOf(gameModel.getChosenCurrencyModel().getOwnedAmount()));
        ownedCrypto.setHorizontalAlignment(JTextField.CENTER);
        ownedCrypto.setForeground(Color.WHITE);

        valueOfOwnedCrypto = new JLabel(String.valueOf(gameModel.getChosenCurrencyModel().getValueOfOwnedAmount()));
        valueOfOwnedCrypto.setHorizontalAlignment(JTextField.CENTER);
        valueOfOwnedCrypto.setForeground(Color.WHITE);

        amountToBuyOrSellTextField = new JTextField("1");
        amountToBuyOrSellTextField.getDocument().addDocumentListener(documentListener);

        valueOfAmountToBuyOrSell = new JTextField();
        valueOfAmountToBuyOrSell.setEditable(false);


        ownedAmountText.setFocusable(false);
        ownedAmountText.setHorizontalAlignment(JTextField.CENTER);
        ownedAmountText.setForeground(Color.WHITE);
        ownedAmountValueText.setFocusable(false);
        ownedAmountValueText.setHorizontalAlignment(JTextField.CENTER);
        ownedAmountValueText.setForeground(Color.WHITE);
        buyButton.addActionListener(new BuyButtonPressed());
        buyButton.setFocusable(false);
        sellButton.addActionListener(new SellButtonPressed());
        sellButton.setFocusable(false);

        grid[0][0].add(chosenCurrencyText);
        grid[1][0].add(chosenCurrencyValue);
        grid[1][1].add(ownedCrypto);
        grid[1][2].add(valueOfOwnedCrypto);
        grid[0][1].add(ownedAmountText);
        grid[0][2].add(ownedAmountValueText);

        grid[1][4].add(amountToBuyOrSellTextField);
        grid[1][5].add(valueOfAmountToBuyOrSell);
        grid[2][4].add(sellButton);
        grid[2][5].add(buyButton);

        grid[0][4].add(description1);
        grid[0][5].add(description2);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void initTopPanel() {
        JPanel[][] topPanelGrid = addGridOfJPanels(1, 5, topPanel, 1, 1);


        topPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        topPanel.setBackground(Color.BLACK);

        gameTimeLabel.setText(gameModel.getGameTime().toString());
        gameTimeLabel.setForeground(Color.WHITE);
        gameTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        fastForwardButton.addMouseListener(new FastForwardButtonPressed());
        fastForwardButton.setFocusable(false);
        fastForwardButton.setText("x" + 1000 / gameModel.getDelay());
        cryptoCurrencyButtons.get(0).doClick();
        if (gameModel.isPaused()) {
            pauseButton.setForeground(Color.RED);
            pauseButton.setText("PAUSED");
        } else {
            pauseButton.setForeground(Color.BLACK);
            pauseButton.setText("Pause");
        }
        pauseButton.setFocusable(false);
        pauseButton.addActionListener(new PauseButtonPressed());

        numberOfCandleSticksButton.setText("Ilość świeczek: " + plotPanel.getNumberOfCandleSticksToPaint());
        numberOfCandleSticksButton.addMouseListener(new NumberOfCandleSticksButtonListener());
        numberOfCandleSticksButton.setFocusable(false);
        topPanelGrid[0][0].add(pauseButton);
        topPanelGrid[0][1].add(fastForwardButton);
        topPanelGrid[0][2].add(numberOfCandleSticksButton);
        topPanelGrid[0][4].add(gameTimeLabel);
        this.add(topPanel, BorderLayout.NORTH);
    }

    private void initCenterPanel() {
        plotPanel = new PlotPanel(gameModel);
        centerPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(plotPanel, BorderLayout.CENTER);

        this.add(centerPanel, BorderLayout.CENTER);
    }

    //====================================================Inner classes===============================================//

    private class FastForwardButtonPressed implements MouseListener {
        private int position = 0;
        private final int[] delays = {1000, 500, 200, 100, 50, 20, 10};

        @Override
        public void mouseClicked(MouseEvent e) {
            fastForwardButton.setSelected(true);

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
            fastForwardButton.setText("x " + 1000 / delays[position]);
            fastForwardButton.setSelected(false);
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
            if (gameModel.getFrameRefreshingTimer().isRunning()) {
                gameModel.getFrameRefreshingTimer().stop();
                gameModel.setPaused(true);
                pauseButton.setText("PAUSED");
                pauseButton.setForeground(Color.RED);
                return;
            }
            gameModel.getFrameRefreshingTimer().restart();
            gameModel.setPaused(false);
            pauseButton.setText("Pause");
            pauseButton.setForeground(Color.BLACK);
        }
    }

    private class BuyButtonPressed implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            double amountToBuy = Double.parseDouble(amountToBuyOrSellTextField.getText());
            double value = amountToBuy * gameModel.getChosenCurrencyModel().getCryptoCurrency().getCurrentPrice();
            double accountBalance = gameModel.getOwnedFiat();

            if (value <= accountBalance) {
                gameModel.setOwnedFiat(accountBalance - value);
                gameModel.getChosenCurrencyModel().setOwnedAmount(gameModel.getChosenCurrencyModel().getOwnedAmount() + amountToBuy);
                gameModel.notifyObservers();
            } else {
                JOptionPane.showMessageDialog(GamePanel.this,
                        "Nie posiadasz tyle pieniędzy!",
                        "",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private class SellButtonPressed implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            double amountToSell = Double.parseDouble(amountToBuyOrSellTextField.getText());
            double value = amountToSell * gameModel.getChosenCurrencyModel().getCryptoCurrency().getCurrentPrice();

            double accountBalance = gameModel.getOwnedFiat();
            if (amountToSell <= gameModel.getChosenCurrencyModel().getOwnedAmount()) {
                gameModel.setOwnedFiat(accountBalance + value);
                gameModel.getChosenCurrencyModel().setOwnedAmount(gameModel.getChosenCurrencyModel().getOwnedAmount() - amountToSell);
                gameModel.notifyObservers();
            } else {
                JOptionPane.showMessageDialog(GamePanel.this,
                        "Nie posiadasz tyle waluty " +
                                gameModel.getChosenCurrencyModel().getCryptoCurrency().getName() + "!",
                        "",
                        JOptionPane.WARNING_MESSAGE);
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

    private class NumberOfCandleSticksButtonListener implements MouseListener {
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
            numberOfCandleSticksButton.setText("Ilość świeczek: " + numbers[position]);
            plotPanel.setNumberOfCandleSticksToPaint(numbers[position]);
            plotPanel.update();
            numberOfCandleSticksButton.setSelected(false);
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
