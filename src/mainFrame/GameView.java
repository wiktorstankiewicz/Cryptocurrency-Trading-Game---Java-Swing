package mainFrame;

import Interfaces.Observer;
import model.GameModel;
import plot.PlotView;

import javax.swing.*;
import java.awt.*;

public class GameView extends JPanel implements Observer {
    private JPanel centerPanel = new JPanel();
    private JPanel topPanel = new JPanel();
    private JPanel bottomPanel = new JPanel();
    private JPanel rightPanel = new JPanel();
    private JPanel leftPanel = new JPanel();

    private GameModel gameModel;

    public GameView(GameModel gameModel) {
        this.gameModel = gameModel;
        this.setLayout(new BorderLayout());
        this.setVisible(true);
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
        leftPanel.setBounds(0, 0, 200, 0);
        this.add(leftPanel, BorderLayout.WEST);
    }

    private void initRightPanel() {
        rightPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        rightPanel.setBackground(Color.blue);
        this.add(rightPanel, BorderLayout.EAST);
    }

    private void initBottomPanel() {
        bottomPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        bottomPanel.setBackground(Color.red);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void initTopPanel() {
        topPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        topPanel.setBackground(Color.red);
        topPanel.setSize(0,100);
        this.add(topPanel, BorderLayout.NORTH);
    }

    private void initCenterPanel() {
        PlotView plotView = new PlotView(gameModel);
        centerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        centerPanel.setBackground(Color.GREEN);
        this.add(centerPanel, BorderLayout.CENTER);
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(plotView,BorderLayout.CENTER);
        centerPanel.setPreferredSize(new Dimension(300,300));
        new Thread(plotView).start();
        //plotView.run();
    }

    @Override
    public void update() {

    }
}
