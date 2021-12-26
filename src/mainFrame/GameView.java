package mainFrame;

import Interfaces.Observer;
import mainFrame.gameView.PlotView;
import model.CurrencyModel;
import model.GameModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameView extends JPanel implements Observer {
    private JPanel centerPanel = new JPanel();
    private JPanel topPanel = new JPanel();
    private JPanel bottomPanel = new JPanel();
    private JPanel rightPanel = new JPanel();
    private JPanel leftPanel = new JPanel();

    private GameModel gameModel;

    private ArrayList<JButton> buttons = new ArrayList<>();

    public GameView(GameModel gameModel) {
        this.gameModel = gameModel;
        this.setLayout(new BorderLayout());
        this.setVisible(true);
        JButton bufforButton;
        for (CurrencyModel cm : gameModel.getCurrencyModels()) {
            bufforButton = new JButton(cm.getCryptoCurrency().getName());
            bufforButton.setSize(new Dimension(300, 50));
            buttons.add(bufforButton);
        }
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
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        leftPanel.setBackground(Color.blue);
        leftPanel.setLayout(new GridLayout(8, 1, 1, 1));
        for (JButton button : buttons) {
            button.setFocusable(false);
            button.addActionListener(e -> button.setEnabled(false));
            leftPanel.add(button);
        }
        this.add(leftPanel, BorderLayout.WEST);
    }

    private void initRightPanel() {
        rightPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        rightPanel.setBackground(Color.blue);
        rightPanel.setLayout(new GridLayout(10, 1, 1, 1));
        JPanel[] grid = new JPanel[10];
        for (int i = 0; i < grid.length; i++) {
            grid[i] = new JPanel();
            grid[i].setOpaque(false);
            rightPanel.add(grid[i]);
        }

        JTextField text1 = new JTextField("Stan konta");
        JTextField fiatBalance = new JTextField("$0");
        JTextField text2 =  new JTextField("Bilans dnia");
        JTextField dailyAbsoluteChange =  new JTextField("$-100");
        JTextField dailyRelativeChange =  new JTextField("-25%");

        grid[0].add(text1);
        grid[1].add(fiatBalance);
        grid[2].add(text2);
        grid[3].add(dailyAbsoluteChange);
        grid[4].add(dailyRelativeChange);
        this.add(rightPanel, BorderLayout.EAST);
    }

    private void initBottomPanel() {
        bottomPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        bottomPanel.setBackground(Color.red);
        JPanel[] grid = new JPanel[8];
        bottomPanel.setLayout(new GridLayout(2, 4, 1, 1));
        for (int i = 0; i < grid.length; i++) {
            grid[i] = new JPanel();
            grid[i].add(new JTextArea(String.valueOf(i)));
            bottomPanel.add(grid[i]);
        }
        JTextField ownedAmountText = new JTextField("Posiadana ilość: 93843,34");
        ownedAmountText.setEditable(false);
        ownedAmountText.setFocusable(false);
        ownedAmountText.setHorizontalAlignment(JTextField.CENTER);
        JTextField ownedAmountValueText = new JTextField("Wartość: $245023123,12");

        ownedAmountValueText.setEditable(false);
        ownedAmountValueText.setFocusable(false);
        ownedAmountValueText.setHorizontalAlignment(JTextField.CENTER);
        JButton buyButton = new JButton("ZAKUP");
        JButton sellButton = new JButton("SPRZEDAŻ");

        /*bottomPanel.add(ownedAmountText);
        bottomPanel.add(ownedAmountValueText);
        bottomPanel.add(buyButton);
        bottomPanel.add(sellButton);*/
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void initTopPanel() {
        topPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        topPanel.setBackground(Color.red);
        topPanel.setSize(0, 100);
        this.add(topPanel, BorderLayout.NORTH);
    }

    private void initCenterPanel() {
        PlotView plotView = new PlotView(gameModel);
        centerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        centerPanel.setBackground(Color.GREEN);
        this.add(centerPanel, BorderLayout.CENTER);
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(plotView, BorderLayout.CENTER);
        centerPanel.setPreferredSize(new Dimension(300, 300));
        new Thread(plotView).start();
        //plotView.run();
    }

    @Override
    public void update() {

    }
}
