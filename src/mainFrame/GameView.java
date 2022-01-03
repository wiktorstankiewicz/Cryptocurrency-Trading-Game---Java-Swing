package mainFrame;

import Interfaces.Observer;
import mainFrame.gameView.PlotView;
import model.CurrencyModel;
import model.GameModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class GameView extends JPanel implements Observer {
    private JPanel centerPanel = new JPanel();
    private JPanel topPanel = new JPanel();
    private JPanel bottomPanel = new JPanel();
    private JPanel rightPanel = new JPanel();
    private JPanel leftPanel = new JPanel();

    private GameModel gameModel;

    private ArrayList<JButton> cryptoCurrencyButtons = new ArrayList<>();
    private JButton fastForwardButton = new JButton();
    private JButton pauseButton = new JButton();
    private JTextArea gameTimeTextField;
    private PlotView plotView;

    public GameView(GameModel gameModel) {
        this.gameModel = gameModel;
        gameTimeTextField = new JTextArea();
        this.setLayout(new BorderLayout());
        this.setVisible(true);
        JButton bufforButton;
        for (CurrencyModel cm : gameModel.getCurrencyModels()) {
            bufforButton = new JButton(cm.getCryptoCurrency().getName());
            bufforButton.setSize(new Dimension(300, 50));
            bufforButton.setFocusable(false);
            cryptoCurrencyButtons.add(bufforButton);
            bufforButton.setIcon(bufforButton.getIcon());
        }
        fastForwardButton.setText("x" + 1000 / gameModel.getDelay());
        fastForwardButton.addMouseListener(new FastForwardButtonPressed());
        fastForwardButton.setFocusable(false);

        pauseButton.setText("Pause");
        pauseButton.setForeground(Color.BLACK);
        pauseButton.setFocusable(false);
        pauseButton.addActionListener(new PauseButtonPressed());


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
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setLayout(new GridLayout(8, 2, 10, 10));
        JPanel[][] grid = new JPanel[8][2];
        /*for (JButton button : buttons) {
            button.setFocusable(false);
            button.addActionListener(
                    e -> {
                        for(JButton button1: buttons){
                            button1.setBackground(new Color(-1118482));
                            button1.setEnabled(true);
                        }
                        button.setBackground(new Color(123));
                        button.setEnabled(false);
                    }
                    );
            leftPanel.add(button);
        }*/
        //dodawanie przyciskow do 0 kolumny
        for (int i = 0; i < cryptoCurrencyButtons.size(); i++) {
            grid[i][0] = new JPanel();
            grid[i][1] = new JPanel();

            grid[i][0].setBackground(null);
            grid[i][1].setBackground(null);

            leftPanel.add(grid[i][0]);
            leftPanel.add(grid[i][1]);

            grid[i][0].setLayout(new BorderLayout());
            grid[i][0].add(cryptoCurrencyButtons.get(i));
            grid[i][1].add(new JLabel(gameModel.getCurrencyModels().get(i).getCryptoCurrency().getImageIcon()));
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
        JTextField text2 = new JTextField("Bilans dnia");
        JTextField dailyAbsoluteChange = new JTextField("$-100");
        JTextField dailyRelativeChange = new JTextField("-25%");

        grid[0].add(text1);
        grid[1].add(fiatBalance);
        grid[2].add(text2);
        grid[3].add(dailyAbsoluteChange);
        grid[6].add(dailyRelativeChange);
        this.add(rightPanel, BorderLayout.EAST);
    }

    private void initBottomPanel() {
        bottomPanel.setBackground(Color.red);
        bottomPanel.setBorder(BorderFactory.createLineBorder(bottomPanel.getBackground(), 10));
        JPanel[][] grid;
        bottomPanel.setLayout(new GridLayout(2, 6, 10, 10));
        grid = addJPanelsToGrid(2, 6, bottomPanel);
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
        JButton buyButton = new JButton("ZAKUP");
        JButton sellButton = new JButton("SPRZEDAŻ");

        grid[0][0].add(ownedAmountText);
        grid[1][0].add(ownedAmountValueText);
        grid[0][4].add(buyButton);
        grid[1][4].add(sellButton);
        grid[1][5].add(new JLabel(gameModel.getCurrencyModels().get(0).getCryptoCurrency().getImageIcon()));
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void initTopPanel() {
        topPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        topPanel.setBackground(Color.red);
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
        centerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
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
    }

    public JPanel getCenterPanel() {
        return centerPanel;
    }

    public void setCenterPanel(JPanel centerPanel) {
        this.centerPanel = centerPanel;
    }

    public JPanel getTopPanel() {
        return topPanel;
    }

    public void setTopPanel(JPanel topPanel) {
        this.topPanel = topPanel;
    }

    public JPanel getBottomPanel() {
        return bottomPanel;
    }

    public void setBottomPanel(JPanel bottomPanel) {
        this.bottomPanel = bottomPanel;
    }

    public JPanel getRightPanel() {
        return rightPanel;
    }

    public void setRightPanel(JPanel rightPanel) {
        this.rightPanel = rightPanel;
    }

    public JPanel getLeftPanel() {
        return leftPanel;
    }

    public void setLeftPanel(JPanel leftPanel) {
        this.leftPanel = leftPanel;
    }

    public GameModel getGameModel() {
        return gameModel;
    }

    public void setGameModel(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    public ArrayList<JButton> getCryptoCurrencyButtons() {
        return cryptoCurrencyButtons;
    }

    public void setCryptoCurrencyButtons(ArrayList<JButton> cryptoCurrencyButtons) {
        this.cryptoCurrencyButtons = cryptoCurrencyButtons;
    }

    public JTextArea getGameTimeTextField() {
        return gameTimeTextField;
    }

    public void setGameTimeTextField(JTextArea gameTimeTextField) {
        this.gameTimeTextField = gameTimeTextField;
    }

    public PlotView getPlotView() {
        return plotView;
    }

    public void setPlotView(PlotView plotView) {
        this.plotView = plotView;
    }

    private class FastForwardButtonPressed implements MouseListener {
        private int position = 0;
        private int[] delays = {1000, 500, 200, 100, 50, 20, 10};

        /*@Override
        public void actionPerformed(ActionEvent e) {
            if(e.)
            position = (position + 1) % delays.length;
            gameModel.setDelay(delays[position]);
            fastForwardButton.setText("x " + 1000/delays[position]);
        }*/

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
            if (SwingUtilities.isRightMouseButton(e)){
                direction = -1;
            }else{
                direction = 1;
            }
            if(position == 0 && direction == -1){
                position = delays.length-1;
            }else{
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
            if(gameModel.getFrameRefreshingTimer().isRunning()){
                gameModel.getFrameRefreshingTimer().stop();
                pauseButton.setText("PAUSED");
                pauseButton.setForeground(Color.RED);
                return;
            }
            gameModel.getFrameRefreshingTimer().restart();
            pauseButton.setText("Pause");
            pauseButton.setForeground(Color.BLACK);
        }
    }
}

