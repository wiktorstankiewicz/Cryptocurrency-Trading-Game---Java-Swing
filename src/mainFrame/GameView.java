package mainFrame;

import Interfaces.Observer;
import mainFrame.gameView.PlotView;
import model.CurrencyModel;
import model.GameModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.Element;
import java.awt.*;
import java.awt.event.*;
import java.io.Serial;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class GameView extends JPanel implements Observer, Serializable {
    @Serial
    private static final long serialVersionUID = 7201869555830826526L;
    private  JPanel centerPanel = new JPanel();
    private  JPanel topPanel = new JPanel();
    private  JPanel bottomPanel = new JPanel();
    private  JPanel rightPanel = new JPanel();
    private  JPanel leftPanel = new JPanel();

    private GameModel gameModel;

    private  ArrayList<JButton> cryptoCurrencyButtons = new ArrayList<>();
    private  JButton fastForwardButton = new JButton();
    private  JButton pauseButton = new JButton();
    private  JButton buyButton = new JButton();
    private  JButton sellButton = new JButton();
    private  JTextField gameTimeTextField;
    private  JTextField ownedCrypto;
    private  JTextField valueOfOwnedCrypto;
    private  JTextField amountToBuyOrSellTextField;
    private  JTextField valueOfAmountToBuyOrSell;
    private  JTextField text1 = new JTextField("Stan konta: ");
    private  JTextField fiatBalance = new JTextField();
    private  JTextField text2 = new JTextField("Bilans dnia");
    private  JTextField dailyAbsoluteChange = new JTextField("$-100");
    private  JTextField dailyRelativeChange = new JTextField("-25%");
    private  PlotView plotView;

    private static transient DocumentListener documentListener;
    private static transient DecimalFormat decimalFormat;


    public GameView(GameModel gameModel) {
        this.gameModel = gameModel;
        gameTimeTextField = new JTextField();
        decimalFormat = new DecimalFormat("#.##");
        this.setFocusable(true);
        this.setLayout(new BorderLayout());
        this.setVisible(true);
        for (CurrencyModel cm : gameModel.getCurrencyModels()) {
            JButton bufforButton;
            bufforButton = new JButton(cm.getCryptoCurrency().getName());
            bufforButton.setSize(new Dimension(300, 50));
            bufforButton.setFocusable(false);
            bufforButton.setBackground(Color.LIGHT_GRAY);
            bufforButton.addActionListener(e -> {
                gameModel.setChoosenCurrencyModel(cm);
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

        if(gameModel.isPaused()){
            pauseButton.setForeground(Color.RED);
            pauseButton.setText("PAUSED");
        }else{
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
    }

    private void initBorderLayoutPanels() {
        initCenterPanel();
        initTopPanel();
        initBottomPanel();
        initRightPanel();
        initLeftPanel();

    }

    private void initLeftPanel() {
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        leftPanel.setBackground(Color.BLACK);
        leftPanel.setLayout(new GridLayout(5, 2, 10, 10));
        JPanel[][] grid = addJPanelsToGrid(5, 2, leftPanel);
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
        rightPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        rightPanel.setBackground(Color.BLACK);
        rightPanel.setLayout(new GridLayout(10, 1, 5, 5));
        JPanel[][] grid = addJPanelsToGrid(10, 2, rightPanel);


        grid[0][0].add(text1);
        text1.setHorizontalAlignment(JTextField.CENTER);
        fiatBalance.setHorizontalAlignment(JTextField.CENTER);
        grid[0][1].add(fiatBalance);
        /*grid[2].add(text2);
        grid[3].add(dailyAbsoluteChange);
        grid[6].add(dailyRelativeChange);*/
        this.add(rightPanel, BorderLayout.EAST);
    }

    private void initBottomPanel() {
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.setBorder(BorderFactory.createLineBorder(bottomPanel.getBackground(), 10));
        JPanel[][] grid;
        bottomPanel.setLayout(new GridLayout(2, 6, 10, 10));
        grid = addJPanelsToGrid(2, 6, bottomPanel);
        ownedCrypto = new JTextField(String.valueOf(gameModel.getChoosenCurrencyModel().getOwnedAmount()));
        //ownedCrypto =  new JTextField();
        ownedCrypto.setHorizontalAlignment(JTextField.CENTER);

        valueOfOwnedCrypto = new JTextField(String.valueOf(gameModel.getChoosenCurrencyModel().getValueOfOwnedAmount()));
        valueOfOwnedCrypto.setHorizontalAlignment(JTextField.CENTER);
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
                    text = decimalFormat.format(Double.parseDouble(amountToBuyOrSellTextField.getText()) *
                            gameModel.getChoosenCurrencyModel().getCryptoCurrency().getCurrentPrice());
                    if (Double.parseDouble(amountToBuyOrSellTextField.getText()) <= 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException numberFormatException) {
                    text = "";
                }
                valueOfAmountToBuyOrSell.setText(text);
                System.out.println(amountToBuyOrSellTextField.getText());

            }
        };
        amountToBuyOrSellTextField = new JTextField("1");
        amountToBuyOrSellTextField.getDocument().addDocumentListener(documentListener);


        valueOfAmountToBuyOrSell = new JTextField();
        grid[0][1].add(ownedCrypto);
        grid[1][1].add(valueOfOwnedCrypto);
        /*for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                grid[row][col] = new JPanel();
                //grid[i].add(new JTextArea(String.valueOf(i)));
                grid[row][col].setLayout(new BorderLayout());
                grid[row][col].setOpaque(false);
                bottomPanel.add(grid[row][col]);
            }
        }*/

        JTextField ownedAmountText = new JTextField("Posiadana ilość:");
        ownedAmountText.setEditable(false);
        ownedAmountText.setFocusable(false);
        ownedAmountText.setHorizontalAlignment(JTextField.CENTER);
        JTextField ownedAmountValueText = new JTextField("Wartość:");

        ownedAmountValueText.setEditable(false);
        ownedAmountValueText.setFocusable(false);
        ownedAmountValueText.setHorizontalAlignment(JTextField.CENTER);
        buyButton = new JButton("ZAKUP");
        buyButton.addActionListener(new BuyButtonPressed());
        sellButton = new JButton("SPRZEDAŻ");
        sellButton.addActionListener(new SellButtonPressed());

        grid[0][0].add(ownedAmountText);
        grid[1][0].add(ownedAmountValueText);

        grid[0][4].add(amountToBuyOrSellTextField);
        grid[0][5].add(valueOfAmountToBuyOrSell);
        grid[1][4].add(sellButton);
        grid[1][5].add(buyButton);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void initTopPanel() {
        topPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        topPanel.setBackground(Color.BLACK);
        topPanel.setSize(0, 100);
        topPanel.setLayout(new GridLayout(1, 5, 1, 1));
        JPanel[][] topPanelGrid = addJPanelsToGrid(1, 5, topPanel);
        gameTimeTextField.setText(gameModel.getGameTime().toString());
        gameTimeTextField.setEditable(false);

        topPanelGrid[0][0].add(pauseButton);
        topPanelGrid[0][1].add(fastForwardButton);
        topPanelGrid[0][4].add(gameTimeTextField);
        this.add(topPanel, BorderLayout.NORTH);
    }

    private JPanel[][] addJPanelsToGrid(int rows, int columns, JPanel panel) {
        if (!(panel.getLayout() instanceof GridLayout)) {
            throw new IllegalArgumentException("Panel should have grid layout");
        }
        JPanel[][] grid = new JPanel[rows][columns];
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                grid[row][col] = new JPanel();
                //grid[i].add(new JTextArea(String.valueOf(i)));
                grid[row][col].setLayout(new BorderLayout());
                grid[row][col].setOpaque(false);
                panel.add(grid[row][col]);
            }
        }
        return grid;
    }

    private void initCenterPanel() {
        plotView = new PlotView(gameModel);
        centerPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        centerPanel.setBackground(Color.GREEN);
        this.add(centerPanel, BorderLayout.CENTER);
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(plotView, BorderLayout.CENTER);
        centerPanel.setPreferredSize(new Dimension(300, 300));
        //new Thread(plotView).start();
        //plotView.run();
    }

    @Override
    public void update() {
        gameTimeTextField.setText(gameModel.getGameTime().toString());
        documentListener.changedUpdate(new DocumentEvent() {
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
        });
        ownedCrypto.setText(decimalFormat.format(gameModel.getChoosenCurrencyModel().getOwnedAmount()));
        valueOfOwnedCrypto.setText(decimalFormat.format(gameModel.getChoosenCurrencyModel().getOwnedAmount() *
                gameModel.getChoosenCurrencyModel().getCryptoCurrency().getCurrentPrice()));
        fiatBalance.setText("$" + decimalFormat.format(gameModel.getOwnedFiat()));
    }

    public PlotView getPlotView() {
        return plotView;
    }

    public void setPlotView(PlotView plotView) {
        this.plotView = plotView;
    }

    private class FastForwardButtonPressed implements MouseListener, Serializable {
        @Serial
        private static final long serialVersionUID = 5389791265366866357L;
        private int position = 0;
        private int[] delays = {1000, 500, 200, 100, 50, 20, 10};

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

    private class PauseButtonPressed implements ActionListener, Serializable {
        @Serial
        private static final long serialVersionUID = -3225639420325292316L;

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

    /*private class amountToBuyOrSellListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            valueOfAmountToBuyOrSell.setText(String.valueOf(Double.parseDouble(amountToBuyOrSellTextField.getText()) * gameModel.getChoosenCurrencyModel().getCryptoCurrency().getCurrentPrice()));
            System.out.println("value changed!");
        }
    }*/

    public class BuyButtonPressed implements ActionListener, Serializable {

        @Serial
        private static final long serialVersionUID = 1199706087838553543L;

        @Override
        public void actionPerformed(ActionEvent e) {
            double amountToBuy = Double.parseDouble(amountToBuyOrSellTextField.getText());
            double value = amountToBuy * gameModel.getChoosenCurrencyModel().getCryptoCurrency().getCurrentPrice();

            double accountBalance = gameModel.getOwnedFiat();
            if (value <= accountBalance) {
                gameModel.setOwnedFiat(accountBalance - value);
                gameModel.getChoosenCurrencyModel().setOwnedAmount(gameModel.getChoosenCurrencyModel().getOwnedAmount() + amountToBuy);
                gameModel.notifyObservers();
            } else {
                JFrame jFrame = new JFrame();
                JDialog jDialog = new JDialog(jFrame);
                JTextField dialogText = new JTextField("Nie posiadasz tyle pieniędzy!");
                dialogText.setFont(new Font("Arial", Font.BOLD, 30));
                dialogText.setEditable(false);
                dialogText.setFocusable(false);
                Toolkit.getDefaultToolkit().beep();
                jDialog.add(dialogText);
                jDialog.setVisible(true);
                jFrame.setLocationRelativeTo(null);
                jDialog.pack();
            }
            System.out.println("Buy button pressed!");
        }
    }

    public class SellButtonPressed implements ActionListener, Serializable {

        @Serial
        private static final long serialVersionUID = -7600673056395471820L;

        @Override
        public void actionPerformed(ActionEvent e) {
            double amountToSell = Double.parseDouble(amountToBuyOrSellTextField.getText());
            double value = amountToSell * gameModel.getChoosenCurrencyModel().getCryptoCurrency().getCurrentPrice();

            double accountBalance = gameModel.getOwnedFiat();
            if (amountToSell <= gameModel.getChoosenCurrencyModel().getOwnedAmount()) {
                gameModel.setOwnedFiat(accountBalance + value);
                gameModel.getChoosenCurrencyModel().setOwnedAmount(gameModel.getChoosenCurrencyModel().getOwnedAmount() - amountToSell);
                gameModel.notifyObservers();
            } else {
                JDialog jDialog = new JDialog(new JFrame());
                JTextField dialogText = new JTextField("Nie posiadasz tyle waluty " + gameModel.getChoosenCurrencyModel().getCryptoCurrency().getName() + "!");
                dialogText.setFont(new Font("Arial", Font.BOLD, 30));
                dialogText.setEditable(false);
                dialogText.setFocusable(false);
                Toolkit.getDefaultToolkit().beep();
                jDialog.add(dialogText);
                jDialog.setVisible(true);
                jDialog.pack();
            }
            System.out.println("Sell button pressed!");
        }
    }
}

