package mainFrame.gameView;

import Constants.ExCryptocurrencies;
import Interfaces.Observer;
import Utilities.CandleStick;
import model.CurrencyModel;
import model.GameModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static java.lang.Math.abs;

public class PlotView extends JPanel implements Observer, Runnable {
    private static final int CANDLE_STICK_WIDTH = 50;
    private GameModel gameModel;
    private JPanel timeLinePanel = new JPanel();
    private PlotPanel plotPanel = new PlotPanel();

    private int PLOT_VIEW_HEIGHT = 100;
    private int PLOT_VIEW_WIDTH = 500;

    private int PLOT_PANEL_HEIGHT;
    private int PLOT_PANEL_WIDTH;

    private int TIME_LINE_PANEL_HEIGHT;
    private int TIME_LINE_PANEL_WIDTH;

    public PlotView(GameModel gameModel) {
        this.gameModel = gameModel;
        initPlotView();
    }

    //todo dodaj strategie wyswietlania wykresu
    @Override
    public void run() {
        for (int i = 0; i > -1; i++) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            plotPanel.repaint();
        }
    }

    private void paintCandleStick(int leftX, CandleStick candleStick, Graphics2D g2D, int width) {
        //todo
       // double scale = 0.4;
        int y = (int)(candleStick.getOpenPricePercentHeight() * (double) plotPanel.getHeight());
        int height = (int) (((candleStick.getClosePricePercentHeight() - candleStick.getOpenPricePercentHeight()) * plotPanel.getHeight()));
        int middleOfCandle = leftX + width / 2;
        //drawing body of candle
        g2D.setColor(candleStick.getColor());
        if (height > 0) {
            g2D.fill(new Rectangle(leftX, y, width, abs(height)));
        } else {
            g2D.fill(new Rectangle(leftX, y - abs(height), width, abs(height)));
        }

        //drawing the candlewick
        g2D.drawLine(middleOfCandle, (int) (candleStick.getMinPricePercentHeight() * plotPanel.getHeight()), middleOfCandle,
                (int) (candleStick.getMaxPricePercentHeight() * plotPanel.getHeight()));
    }

    private void paintAllCandleSticks(Graphics2D g2D) {
        ArrayList<CandleStick> candleStickArrayList = gameModel.getChoosenCurrencyModel().getCandleStickArrayList();
        int distanceFromLeftBorder = 1;
        int candleStickWidth;
        if(candleStickArrayList.size() == 1){
            candleStickWidth = 50;
        }
        else{
            candleStickWidth = plotPanel.getWidth() / candleStickArrayList.size();
        }
        for (int i = 0; i < candleStickArrayList.size(); i++) {
            paintCandleStick(distanceFromLeftBorder + i * candleStickWidth,
                    candleStickArrayList.get(i), g2D, candleStickWidth);
        }

    }

    public void initPlotView() {
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(PLOT_VIEW_WIDTH, PLOT_VIEW_HEIGHT));
        this.add(timeLinePanel, BorderLayout.SOUTH);
        this.add(plotPanel, BorderLayout.CENTER);
        initTimeLinePanel();
        initPlotPanel();
    }

    private void initPlotPanel() {
        plotPanel.setLayout(null);
        plotPanel.setBackground(Color.BLACK);
    }

    private void initTimeLinePanel() {
        plotPanel.setBackground(Color.green);
    }

    @Override
    public void update() {
        plotPanel.repaint();
    }

    private class PlotPanel extends JPanel {

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g2D = (Graphics2D) g;
            g2D.scale(1, -1);
            g2D.translate(0, -getHeight());

            paintAllCandleSticks(g2D);

            g2D.scale(1, -1);
            g2D.translate(0, getHeight());
        }
    }
}
