package GameMVC;

import interfaces.pricePredictionStrategy.SugestedAction;
import utilities.PriceDirection;
import view.gamePanel.PlotPanel;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import static utilities.Utilities.addGridOfJPanels;

public class GamePanelView extends JPanel {
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
    private final JLabel ownedCrypto = new JLabel();
    private final JLabel valueOfOwnedCrypto = new JLabel();
    private final JTextField amountToBuyOrSellTextField = new JTextField();
    private final JTextField valueOfAmountToBuyOrSell = new JTextField();
    private final JLabel fiatBalance = new JLabel();
    private final JLabel chosenCurrencyText = new JLabel("(WALUTA): ");
    private final JLabel chosenCurrencyValue = new JLabel("(WARTOSC)");
    private final JLabel valueOfCryptos = new JLabel();
    private final JLabel totalValue = new JLabel();
    private final JLabel suggestedAction = new JLabel();

    private PlotPanel plotPanel = new PlotPanel();
    private JPanel[][] cryptoCurrencyButtonsPanelGrid;
    private final JButton buyButton = new JButton("ZAKUP");
    private final JButton sellButton = new JButton("SPRZEDAŻ");


    //====================================================Public Methods==============================================//

    public GamePanelView(ArrayList<String> currencyButtonLabels, ArrayList<ImageIcon> icons, boolean isPaused,
                         int simulationSpeed, int numberOfCandleSticks) {
        this.setLayout(new BorderLayout());
        this.setFocusable(true);
        initBorderLayoutPanels(currencyButtonLabels, icons, isPaused,
                simulationSpeed, numberOfCandleSticks);
    }


    public void update(String time, String ownedCrypto, String valueOfOwnedCrypto, String valueOfAmountToBuyOrSell, String fiatBalance,
                       String chosenCurrency, String chosenCurrencyValue, String valueOfCryptos, String totalValue) {
        gameTimeLabel.setText(time);
        this.ownedCrypto.setText(ownedCrypto);
        this.valueOfOwnedCrypto.setText(valueOfOwnedCrypto);
        this.valueOfAmountToBuyOrSell.setText(valueOfAmountToBuyOrSell);
        this.fiatBalance.setText(fiatBalance);
        this.chosenCurrencyText.setText(chosenCurrency);
        this.chosenCurrencyValue.setText(chosenCurrencyValue);
        this.valueOfCryptos.setText(valueOfCryptos);
        this.totalValue.setText(totalValue);
    }

    public void setTotalValueText(String text) {
        totalValue.setText(text);
    }

    public void setValueOfCryptosText(String text) {
        valueOfCryptos.setText(text);
    }

    public void setChosenCurrencyValueText(String text, PriceDirection priceDirection) {
        Color color = switch (priceDirection) {
            case UP -> Color.GREEN;
            case DOWN -> Color.RED;
        };
        chosenCurrencyValue.setText(text);
        chosenCurrencyValue.setForeground(color);
    }

    public void setChosenCurrencyText(String text) {
        chosenCurrencyText.setText(text);

    }

    public void setFiatBalanceText(String text) {
        fiatBalance.setText(text);
    }

    public void setValueOfOwnedCryptoText(String text) {
        valueOfOwnedCrypto.setText(text);
    }

    public void setOwnedCryptoText(String ownedAmount) {
        ownedCrypto.setText(ownedAmount);
    }

    public void setGameTimeLabelText(String text) {
        gameTimeLabel.setText(text);
    }


    //====================================================Private Methods==============================================//

    public void initCurrencyButtons(ArrayList<String> labels) {
        for (String label : labels) {
            JButton buffer = new JButton(label);
            buffer.setFocusable(false);
            buffer.setBackground(Color.LIGHT_GRAY);
            cryptoCurrencyButtons.add(buffer);
            //buffer.setIcon(buffer.getIcon());
        }
    }

    public void addActionListenersToCurrencyButtons(ArrayList<ActionListener> actionListeners) {
        for (int i = 0; i < cryptoCurrencyButtons.size(); i++) {
            cryptoCurrencyButtons.get(i).addActionListener(actionListeners.get(i));
        }
    }


    private void initBorderLayoutPanels(ArrayList<String> currencyButtonLabels, ArrayList<ImageIcon> icons, boolean isPaused,
                                        int simulationSpeed, int numberOfCandleSticks) {
        initCenterPanel();
        initTopPanel(isPaused, simulationSpeed, numberOfCandleSticks);
        initBottomPanel();
        initRightPanel();
        initLeftPanel(currencyButtonLabels, icons);
    }

    private void initLeftPanel(ArrayList<String> currencyButtonLabels, ArrayList<ImageIcon> icons) {
        cryptoCurrencyButtonsPanelGrid = addGridOfJPanels(5, 2, leftPanel, 10, 10);
        initCurrencyButtons(currencyButtonLabels);
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        leftPanel.setBackground(Color.BLACK);
        for (int i = 0; i < cryptoCurrencyButtons.size(); i++) {
            cryptoCurrencyButtonsPanelGrid[i][0].setBackground(null);
            cryptoCurrencyButtonsPanelGrid[i][1].setBackground(null);

            cryptoCurrencyButtonsPanelGrid[i][0].setLayout(new BorderLayout());
            cryptoCurrencyButtonsPanelGrid[i][0].add(cryptoCurrencyButtons.get(i));
            cryptoCurrencyButtonsPanelGrid[i][1].add(new JLabel(icons.get(i)));
        }
        this.add(leftPanel, BorderLayout.WEST);
    }


    private void initRightPanel() {
        JPanel[][] grid = addGridOfJPanels(5, 2, rightPanel, 5, 5);
        JLabel text1 = new JLabel("Posiadane $: ");
        JLabel text2 = new JLabel("Wartość walut: ");
        JLabel text3 = new JLabel("Łącznie: ");
        JLabel text4 = new JLabel("Sugerowana akcja: ");
        suggestedAction.setForeground(Color.WHITE);
        suggestedAction.setHorizontalAlignment(SwingConstants.CENTER);
        text4.setHorizontalAlignment(SwingConstants.CENTER);
        text4.setForeground(Color.WHITE);
        totalValue.setForeground(Color.WHITE);
        totalValue.setHorizontalAlignment(SwingConstants.CENTER);
        valueOfCryptos.setForeground(Color.WHITE);
        valueOfCryptos.setHorizontalAlignment(SwingConstants.CENTER);
        text1.setHorizontalAlignment(SwingConstants.CENTER);
        text1.setForeground(Color.WHITE);
        rightPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        rightPanel.setBackground(Color.BLACK);
        fiatBalance.setHorizontalAlignment(JTextField.CENTER);
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

        grid[4][0].add(text4);
        grid[4][1].add(suggestedAction);
        this.add(rightPanel, BorderLayout.EAST);
    }

    private void initBottomPanel() {
        JPanel[][] grid = addGridOfJPanels(3, 6, bottomPanel, 10, 10);
        JLabel ownedAmountText = new JLabel("Posiadana ilość:");
        JLabel ownedAmountValueText = new JLabel("Wartość:");
        JLabel description1 = new JLabel("Ilość do kupienia/sprzedania");
        JLabel description2 = new JLabel("Wartość");

        chosenCurrencyText.setForeground(Color.WHITE);
        chosenCurrencyText.setHorizontalAlignment(SwingConstants.CENTER);
        chosenCurrencyValue.setHorizontalAlignment(SwingConstants.CENTER);
        description1.setForeground(Color.WHITE);
        description1.setHorizontalAlignment(SwingConstants.CENTER);
        description2.setForeground(Color.WHITE);
        description2.setHorizontalAlignment(SwingConstants.CENTER);

        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.setBorder(BorderFactory.createLineBorder(bottomPanel.getBackground(), 10));

        ownedCrypto.setHorizontalAlignment(JTextField.CENTER);
        ownedCrypto.setForeground(Color.WHITE);

        valueOfOwnedCrypto.setHorizontalAlignment(JTextField.CENTER);
        valueOfOwnedCrypto.setForeground(Color.WHITE);

        amountToBuyOrSellTextField.setText("1");
        valueOfAmountToBuyOrSell.setEditable(false);

        ownedAmountText.setFocusable(false);
        ownedAmountText.setHorizontalAlignment(JTextField.CENTER);
        ownedAmountText.setForeground(Color.WHITE);
        ownedAmountValueText.setFocusable(false);
        ownedAmountValueText.setHorizontalAlignment(JTextField.CENTER);
        ownedAmountValueText.setForeground(Color.WHITE);
        buyButton.setFocusable(false);
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

    public void addDocumentListenerToAmountToBuyOrSellTextField(DocumentListener documentListener) {
        amountToBuyOrSellTextField.getDocument().addDocumentListener(documentListener);
    }

    private void initTopPanel(boolean isPaused, int simulationSpeed, int amountOfCandleSticks) {
        JPanel[][] topPanelGrid = addGridOfJPanels(1, 5, topPanel, 1, 1);
        topPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        topPanel.setBackground(Color.BLACK);

        gameTimeLabel.setForeground(Color.WHITE);
        gameTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        setFastForwardButtonState(simulationSpeed);
        fastForwardButton.setFocusable(false);

        pauseButton.setFocusable(false);
        setPauseButtonState(isPaused);


        numberOfCandleSticksButton.setFocusable(false);
        setNumberOfCandleSticksButtonState(amountOfCandleSticks);
        topPanelGrid[0][0].add(pauseButton);
        topPanelGrid[0][1].add(fastForwardButton);
        topPanelGrid[0][2].add(numberOfCandleSticksButton);
        topPanelGrid[0][4].add(gameTimeLabel);
        this.add(topPanel, BorderLayout.NORTH);
    }

    public void setFastForwardButtonState(int simulationSpeed) {
        fastForwardButton.setText("x " + simulationSpeed);
    }

    public void setNumberOfCandleSticksButtonState(int amountOfCandleSticks) {
        numberOfCandleSticksButton.setText("Ilość świeczek: " + amountOfCandleSticks);
    }

    public void setPauseButtonState(boolean isPaused) {
        if (!isPaused) {
            pauseButton.setText("Pause");
            pauseButton.setForeground(Color.BLACK);
            return;
        }
        pauseButton.setText("PAUSED");
        pauseButton.setForeground(Color.RED);
    }


    private void addMouseListenerToNumberOfCandleSticksButton(MouseListener mouseListener) {
        numberOfCandleSticksButton.addMouseListener(mouseListener);
    }

    private void initCenterPanel() {
        plotPanel = new PlotPanel();
        centerPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(plotPanel, BorderLayout.CENTER);

        this.add(centerPanel, BorderLayout.CENTER);
    }

    public void addPlotPanel(PlotPanel plotPanel) {
        this.plotPanel = plotPanel;
    }

    //====================================================Inner classes===============================================//


    public JPanel getCenterPanel() {
        return centerPanel;
    }

    public JPanel getTopPanel() {
        return topPanel;
    }

    public JPanel getBottomPanel() {
        return bottomPanel;
    }

    public JPanel getRightPanel() {
        return rightPanel;
    }

    public JPanel getLeftPanel() {
        return leftPanel;
    }

    public ArrayList<JButton> getCryptoCurrencyButtons() {
        return cryptoCurrencyButtons;
    }

    public JButton getFastForwardButton() {
        return fastForwardButton;
    }

    public JButton getPauseButton() {
        return pauseButton;
    }

    public JButton getNumberOfCandleSticksButton() {
        return numberOfCandleSticksButton;
    }

    public JLabel getGameTimeLabel() {
        return gameTimeLabel;
    }

    public JLabel getOwnedCrypto() {
        return ownedCrypto;
    }


    public JLabel getValueOfOwnedCrypto() {
        return valueOfOwnedCrypto;
    }


    public JTextField getAmountToBuyOrSellTextField() {
        return amountToBuyOrSellTextField;
    }

    public JTextField getValueOfAmountToBuyOrSell() {
        return valueOfAmountToBuyOrSell;
    }


    public JLabel getFiatBalance() {
        return fiatBalance;
    }

    public JLabel getChosenCurrencyText() {
        return chosenCurrencyText;
    }


    public JLabel getChosenCurrencyValue() {
        return chosenCurrencyValue;
    }


    public JLabel getValueOfCryptos() {
        return valueOfCryptos;
    }

    public JLabel getTotalValue() {
        return totalValue;
    }

    public PlotPanel getPlotPanel() {
        return plotPanel;
    }

    public void setPlotPanel(PlotPanel plotPanel) {
        this.plotPanel = plotPanel;
    }

    public JPanel[][] getCryptoCurrencyButtonsPanelGrid() {
        return cryptoCurrencyButtonsPanelGrid;
    }

    public void setCryptoCurrencyButtonsPanelGrid(JPanel[][] cryptoCurrencyButtonsPanelGrid) {
        this.cryptoCurrencyButtonsPanelGrid = cryptoCurrencyButtonsPanelGrid;
    }


    public void showErrorMessage(String text) {
        JOptionPane.showMessageDialog(GamePanelView.this,
                text,
                "",
                JOptionPane.WARNING_MESSAGE);
    }

    public JButton getBuyButton() {
        return buyButton;
    }

    public JButton getSellButton() {
        return sellButton;
    }

    public void setSuggestedActionState(SugestedAction state) {
        Color color;
        String text;

        switch (state) {
            case BUY -> {
                color = Color.GREEN;
                text = "Kupuj!";
            }
            case SELL -> {
                color = Color.RED;
                text = "Sprzedaj!";
            }
            case NOT_DETERMINED -> {
                color = Color.WHITE;
                text = "???";
            }
            default -> {
                throw new IllegalStateException();
            }
        }
        suggestedAction.setText(text);
        suggestedAction.setForeground(color);
    }

    public void setValueOfAmountToBuyOrSellTextFieldState(String text) {
        valueOfAmountToBuyOrSell.setText("$" + text);
    }
}
