package mainFrame.gameView;

import Constants.ExCryptocurrencies;
import Interfaces.Observer;
import Utilities.CandleStick;
import Utilities.CryptoCurrency;
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
        gameModel.setChoosenCurrencyModel(new CurrencyModel(0,
                ExCryptocurrencies.availableCryptoCurrencies.get(0)));
        gameModel.getChoosenCurrencyModel().setCandleStickArrayList(ExCryptocurrencies.exampleCandleStickArray);
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
        int y = candleStick.getOpenPriceHeight();
        int height = candleStick.getClosePriceHeight() - candleStick.getOpenPriceHeight();
        int middleOfCandle = leftX + width / 2;
        //drawing body of candle
        g2D.setColor(candleStick.getColor());
        if (height > 0) {
            g2D.fill(new Rectangle(leftX, y, width, abs(height)));
        } else {
            g2D.fill(new Rectangle(leftX, y - abs(height), width, abs(height)));
        }

        //drawing the candlewick
        g2D.drawLine(middleOfCandle, candleStick.getMinPriceHeight(), middleOfCandle, candleStick.getMaxPriceHeight());
    }

    private void paintAllCandleSticks(Graphics2D g2D){
        ArrayList<CandleStick> candleStickArrayList = gameModel.getChoosenCurrencyModel().getCandleStickArrayList();
        int distanceFromLeftBorder = 1;
        int candleStickWidth = plotPanel.getWidth()/ candleStickArrayList.size() - 1;
        for(int i = 0 ; i<candleStickArrayList.size(); i++){
            paintCandleStick(distanceFromLeftBorder + i*candleStickWidth,
                    candleStickArrayList.get(i),g2D,candleStickWidth);
        }

    }

    public void initPlotView() {
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(PLOT_VIEW_WIDTH,PLOT_VIEW_HEIGHT));
        this.add(timeLinePanel, BorderLayout.SOUTH);
        this.add(plotPanel, BorderLayout.CENTER);
        initTimeLinePanel();
        initPlotPanel();
    }

    private void initPlotPanel() {
        plotPanel.setLayout(null);
        plotPanel.setBackground(Color.BLACK);
        plotPanel.setSize(new Dimension(PLOT_VIEW_WIDTH, PLOT_VIEW_HEIGHT - TIME_LINE_PANEL_HEIGHT));
    }

    private void initTimeLinePanel() {
        plotPanel.setBackground(Color.green);
        //plotPanel.setSize(newd)
    }

    @Override
    public void update() {
        plotPanel.repaint();
    }

    private class PlotPanel extends JPanel {
        public int time = 1;
        public CryptoCurrency BTC = ExCryptocurrencies.availableCryptoCurrencies.get(0);
        public CandleStick cs = new CandleStick(BTC);

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g2D = (Graphics2D) g;
            g2D.scale(1, -1);
            g2D.translate(0, -getHeight());
            gameModel.getChoosenCurrencyModel().getCryptoCurrency().getPriceCalculation().calculatePrice(1,gameModel.getChoosenCurrencyModel().getCryptoCurrency());
            CandleStick last = gameModel.getChoosenCurrencyModel().getCandleStickArrayList().get(gameModel.getChoosenCurrencyModel().getCandleStickArrayList().size()-1);
            last.update();
            paintAllCandleSticks(g2D);
            g2D.scale(1, -1);
            g2D.translate(0, getHeight());
            /*for (int i = 0; i < ExCryptocurrencies.exampleCandleStickArray.size(); i++) {
                paintCandleStick(i * CANDLE_STICK_WIDTH + 1,
                        ExCryptocurrencies.exampleCandleStickArray.get(i),
                        g2D);
            }*/
//            paintCandleStick(50,new CandleStick(100,200,50,250,
//                     Color.GREEN),g2D);
        }
    }
}
