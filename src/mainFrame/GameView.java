package mainFrame;

import Constants.Constants;
import Interfaces.Observer;
import Utilities.GameTime;
import mainFrame.gameView.PlotPanel;
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

import static Utilities.Utilities.addJPanelsToGrid;

public class GameView extends JPanel implements Observer {
    private final JPanel centerPanel = new JPanel();
    private final JPanel topPanel = new JPanel();
    private final JPanel bottomPanel = new JPanel();
    private final JPanel rightPanel = new JPanel();
    private final JPanel leftPanel = new JPanel();

    private final GameModel gameModel;

    private final ArrayList<JButton> cryptoCurrencyButtons = new ArrayList<>();
    private final JButton fastForwardButton = new JButton();
    private final JButton pauseButton = new JButton();
    private final JTextField gameTimeTextField;
    private JTextField ownedCrypto;
    private JTextField valueOfOwnedCrypto;
    private JTextField amountToBuyOrSellTextField;
    private JTextField valueOfAmountToBuyOrSell;
    private final JTextField text1 = new JTextField("Stan konta: ");
    private final JTextField fiatBalance = new JTextField();
    private PlotPanel plotView;

    private static DocumentListener documentListener;

    //====================================================Public Methods==============================================//

    public GameView(GameModel gameModel) {
        this.gameModel = gameModel;
        gameTimeTextField = new JTextField();

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
                System.out.println(valueOfAmountToBuyOrSell.getText());
                System.out.println(amountToBuyOrSellTextField.getText());

            }
        };
        this.setFocusable(true);
        this.setLayout(new BorderLayout());
        for (CurrencyModel cm : gameModel.getCurrencyModels()) {
            JButton bufforButton;
            bufforButton = new JButton(cm.getCryptoCurrency().getName());
            bufforButton.setSize(new Dimension(300, 50));
            bufforButton.setFocusable(false);
            bufforButton.setBackground(Color.LIGHT_GRAY);
            bufforButton.addActionListener(e -> {
                gameModel.setChosenCurrencyModel(cm);
                for (JButton jButton : cryptoCurrencyButtons) {
                    jButton.setBackground(Color.LIGHT_GRAY);
                    jButton.setBorder(null);
                }
                bufforButton.setBackground(Color.GRAY);
                bufforButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
            });
            cryptoCurrencyButtons.add(bufforButton);
            bufforButton.setIcon(bufforButton.getIcon());
        }
        fastForwardButton.setText("x" + 1000 / gameModel.getDelay());
        fastForwardButton.addMouseListener(new FastForwardButtonPressed());
        fastForwardButton.setFocusable(false);
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
        initBorderLayoutPanels();
        gameModel.addObserver(this);
        gameModel.addObserver(this.plotView);
        gameModel.run();
        this.update();
    }

    @Override
    public void update() {
        GameTime currentTime = gameModel.getGameTime();
        gameTimeTextField.setText(String.valueOf(currentTime));
        documentListener.changedUpdate(new EmptyDocumentEvent());
        ownedCrypto.setText(Constants.DOUBLE_FORMATTER.format(gameModel.getChosenCurrencyModel().getOwnedAmount()));
        valueOfOwnedCrypto.setText("$" + Constants.DOUBLE_FORMATTER.format(gameModel.getChosenCurrencyModel().getOwnedAmount() *
                gameModel.getChosenCurrencyModel().getCryptoCurrency().getCurrentPrice()));
        fiatBalance.setText("$" + Constants.DOUBLE_FORMATTER.format(gameModel.getOwnedFiat()));
    }

    //====================================================Private Methods==============================================//

    private void initBorderLayoutPanels() {
        initCenterPanel();
        initTopPanel();
        initBottomPanel();
        initRightPanel();
        initLeftPanel();
    }

    private void initLeftPanel() {
        JPanel[][] grid = addJPanelsToGrid(5, 2, leftPanel, 10, 10);

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
        JPanel[][] grid = addJPanelsToGrid(10, 2, rightPanel, 5, 5);

        rightPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        rightPanel.setBackground(Color.BLACK);
        fiatBalance.setHorizontalAlignment(JTextField.CENTER);
        fiatBalance.setText("$" + Constants.DOUBLE_FORMATTER.format(gameModel.getOwnedFiat()));
        text1.setHorizontalAlignment(JTextField.CENTER);

        grid[0][0].add(text1);
        grid[0][1].add(fiatBalance);
        this.add(rightPanel, BorderLayout.EAST);
    }

    private void initBottomPanel() {
        JPanel[][] grid = addJPanelsToGrid(2, 6, bottomPanel, 10, 10);
        JTextField ownedAmountText = new JTextField("Posiadana ilość:");
        JTextField ownedAmountValueText = new JTextField("Wartość:");
        JButton buyButton = new JButton("ZAKUP");
        JButton sellButton = new JButton("SPRZEDAŻ");

        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.setBorder(BorderFactory.createLineBorder(bottomPanel.getBackground(), 10));

        ownedCrypto = new JTextField(String.valueOf(gameModel.getChosenCurrencyModel().getOwnedAmount()));
        ownedCrypto.setHorizontalAlignment(JTextField.CENTER);

        valueOfOwnedCrypto = new JTextField(String.valueOf(gameModel.getChosenCurrencyModel().getValueOfOwnedAmount()));
        valueOfOwnedCrypto.setHorizontalAlignment(JTextField.CENTER);

        amountToBuyOrSellTextField = new JTextField("1");
        amountToBuyOrSellTextField.getDocument().addDocumentListener(documentListener);

        valueOfAmountToBuyOrSell = new JTextField();
        valueOfAmountToBuyOrSell.setEditable(false);


        ownedAmountText.setEditable(false);
        ownedAmountText.setFocusable(false);
        ownedAmountText.setHorizontalAlignment(JTextField.CENTER);
        ownedAmountValueText.setEditable(false);
        ownedAmountValueText.setFocusable(false);
        ownedAmountValueText.setHorizontalAlignment(JTextField.CENTER);
        buyButton.addActionListener(new BuyButtonPressed());
        sellButton.addActionListener(new SellButtonPressed());

        grid[0][1].add(ownedCrypto);
        grid[1][1].add(valueOfOwnedCrypto);
        grid[0][0].add(ownedAmountText);
        grid[1][0].add(ownedAmountValueText);
        grid[0][4].add(amountToBuyOrSellTextField);
        grid[0][5].add(valueOfAmountToBuyOrSell);
        grid[1][4].add(sellButton);
        grid[1][5].add(buyButton);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void initTopPanel() {
        JPanel[][] topPanelGrid = addJPanelsToGrid(1, 5, topPanel, 1, 1);

        topPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        topPanel.setBackground(Color.BLACK);

        gameTimeTextField.setText(gameModel.getGameTime().toString());
        gameTimeTextField.setEditable(false);

        topPanelGrid[0][0].add(pauseButton);
        topPanelGrid[0][1].add(fastForwardButton);
        topPanelGrid[0][4].add(gameTimeTextField);
        this.add(topPanel, BorderLayout.NORTH);
    }

    private void initCenterPanel() {
        plotView = new PlotPanel(gameModel);

        centerPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(plotView, BorderLayout.CENTER);

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
                JOptionPane.showMessageDialog(GameView.this,
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
                JOptionPane.showMessageDialog(GameView.this,
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
}
