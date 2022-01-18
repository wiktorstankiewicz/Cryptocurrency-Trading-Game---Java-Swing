package view.gamePanel;

import interfaces.Observer;
import model.CurrencyModel;
import model.GameModel;
import utilities.Constants;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;

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
    private JLabel chosenCurrencyText =  new JLabel();
    private JLabel chosenCurrencyValue = new JLabel();
    private final JLabel valueOfCryptos = new JLabel();
    private final JLabel totalValue = new JLabel();

    private PlotPanel plotPanel;
    private JPanel[][] cryptoCurrencyButtonsPanelGrid;


    //====================================================Public Methods==============================================//

    public GamePanel() {
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
        initBorderLayoutPanels();

        this.update();
    }

    @Override
    public void update() {

    }

    public void setTotalValueText(String text) {
        totalValue.setText(text);
    }

    public void setValueOfCryptosText(String text) {
        valueOfCryptos.setText(text);
    }

    public void setChosenCurrencyValueText(String text, PriceDirection priceDirection) {
        Color color = switch (priceDirection){
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

    public void initCurrencyButtons(GameModel gameModel) {
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
        cryptoCurrencyButtonsPanelGrid = addGridOfJPanels(5, 2, leftPanel, 10, 10);

        leftPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        leftPanel.setBackground(Color.BLACK);
        for (int i = 0; i < cryptoCurrencyButtons.size(); i++) {
            cryptoCurrencyButtonsPanelGrid[i][0].setBackground(null);
            cryptoCurrencyButtonsPanelGrid[i][1].setBackground(null);

            cryptoCurrencyButtonsPanelGrid[i][0].setLayout(new BorderLayout());
            cryptoCurrencyButtonsPanelGrid[i][0].add(cryptoCurrencyButtons.get(i));
            cryptoCurrencyButtonsPanelGrid[i][1].add(new JLabel());
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

        amountToBuyOrSellTextField = new JTextField("1");

        valueOfAmountToBuyOrSell = new JTextField();
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

    private void initTopPanel() {
        JPanel[][] topPanelGrid = addGridOfJPanels(1, 5, topPanel, 1, 1);
        topPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        topPanel.setBackground(Color.BLACK);

        gameTimeLabel.setForeground(Color.WHITE);
        gameTimeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        fastForwardButton.setFocusable(false);
        //cryptoCurrencyButtons.get(0).doClick();

        pauseButton.setFocusable(false);

        numberOfCandleSticksButton.setFocusable(false);
        topPanelGrid[0][0].add(pauseButton);
        topPanelGrid[0][1].add(fastForwardButton);
        topPanelGrid[0][2].add(numberOfCandleSticksButton);
        topPanelGrid[0][4].add(gameTimeLabel);
        this.add(topPanel, BorderLayout.NORTH);
    }

    public void setFastForwardButtonText(String text) {
        fastForwardButton.setText(text);
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

    public void setOwnedCrypto(JLabel ownedCrypto) {
        this.ownedCrypto = ownedCrypto;
    }

    public JLabel getValueOfOwnedCrypto() {
        return valueOfOwnedCrypto;
    }

    public void setValueOfOwnedCrypto(JLabel valueOfOwnedCrypto) {
        this.valueOfOwnedCrypto = valueOfOwnedCrypto;
    }

    public JTextField getAmountToBuyOrSellTextField() {
        return amountToBuyOrSellTextField;
    }

    public void setAmountToBuyOrSellTextField(JTextField amountToBuyOrSellTextField) {
        this.amountToBuyOrSellTextField = amountToBuyOrSellTextField;
    }

    public JTextField getValueOfAmountToBuyOrSell() {
        return valueOfAmountToBuyOrSell;
    }

    public void setValueOfAmountToBuyOrSell(JTextField valueOfAmountToBuyOrSell) {
        this.valueOfAmountToBuyOrSell = valueOfAmountToBuyOrSell;
    }

    public JLabel getFiatBalance() {
        return fiatBalance;
    }

    public JLabel getChosenCurrencyText() {
        return chosenCurrencyText;
    }

    public void setChosenCurrencyText(JLabel chosenCurrencyText) {
        this.chosenCurrencyText = chosenCurrencyText;
    }

    public JLabel getChosenCurrencyValue() {
        return chosenCurrencyValue;
    }

    public void setChosenCurrencyValue(JLabel chosenCurrencyValue) {
        this.chosenCurrencyValue = chosenCurrencyValue;
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
        JOptionPane.showMessageDialog(GamePanel.this,
                text,
                "",
                JOptionPane.WARNING_MESSAGE);
    }
}
