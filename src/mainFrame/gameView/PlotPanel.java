package mainFrame.gameView;

import Interfaces.Observer;
import Utilities.CandleStick;
import model.CurrencyModel;
import model.GameModel;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;
import java.text.DecimalFormat;
import java.util.ArrayList;

import static java.lang.Math.abs;

public class PlotPanel extends JPanel implements Observer {
    @Serial
    private static final long serialVersionUID = -6105297773901533785L;
    private static final int numberOfCandleSticksToPaint = 20;

    private final GameModel gameModel;
    private final JPanel timeLinePanel = new JPanel();
    private final GraphPanel graphPanel = new GraphPanel();
    private AxisPanel priceAxisPanel;
    private final JTextArea currentPrice;

    //====================================================Public Methods==============================================//

    public PlotPanel(GameModel gameModel) {
        this.gameModel = gameModel;
        currentPrice = new JTextArea();
        priceAxisPanel = new AxisPanel();
        priceAxisPanel.add(currentPrice);
        initPlotPanel();
    }

    @Override
    public void update() {
        graphPanel.repaint();
        priceAxisPanel.repaint();
    }

    //====================================================Private Methods=============================================//

    private void initPlotPanel() {
        initPriceAxisPanel();
        initTimeLinePanel();
        initGraphPanel();
        this.setLayout(new BorderLayout());
        this.add(graphPanel, BorderLayout.CENTER);
        this.add(priceAxisPanel, BorderLayout.WEST);
    }

    private void initPriceAxisPanel() {
        priceAxisPanel = new AxisPanel();
    }

    private void initGraphPanel() {
        graphPanel.setLayout(null);
        graphPanel.setBackground(Color.BLACK);
    }

    private void initTimeLinePanel() {
        timeLinePanel.setBackground(Color.MAGENTA);
    }

    private void paintCandleStick(int leftX, CandleStick candleStick, Graphics2D g2D, int width) {
        int y = (int) (candleStick.getOpenPricePercentHeight() * (double) graphPanel.getHeight());
        int height = (int) (((candleStick.getClosePricePercentHeight() - candleStick.getOpenPricePercentHeight()) * graphPanel.getHeight()));
        int middleOfCandle = leftX + width / 2;

        //drawing body of candle
        g2D.setColor(candleStick.getColor());
        if (height > 0) {
            g2D.fill(new Rectangle(leftX, y, width, abs(height)));
        } else {
            g2D.fill(new Rectangle(leftX, y - abs(height), width, abs(height)));
        }

        //drawing the candlewick
        g2D.drawLine(middleOfCandle, (int) (candleStick.getMinPricePercentHeight() * graphPanel.getHeight()), middleOfCandle,
                (int) (candleStick.getMaxPricePercentHeight() * graphPanel.getHeight()));
    }

    private void paintAllCandleSticks(Graphics2D g2D) {
        CurrencyModel.PacketToDraw packetToDraw = gameModel.getChosenCurrencyModel().getPacketToDraw(numberOfCandleSticksToPaint);
        ArrayList<CandleStick> arrayListToDraw = packetToDraw.getCandleSticks();
        CandleStick lastCandleStick = arrayListToDraw.get(arrayListToDraw.size() - 1);
        int candleStickWidth = graphPanel.getWidth() / arrayListToDraw.size();
        int middleOfLastCandleStick = arrayListToDraw.size() * candleStickWidth - candleStickWidth / 2;

        for (int i = 0; i < arrayListToDraw.size(); i++) {
            paintCandleStick(i * candleStickWidth, arrayListToDraw.get(i), g2D, candleStickWidth);
        }
        paintCurrentPriceLine(g2D, lastCandleStick, middleOfLastCandleStick);
        priceAxisPanel.update(lastCandleStick);
    }

    private void paintCurrentPriceLine(Graphics2D g2D, CandleStick lastCandleStick, int middleOfLastCandleStick) {
        g2D.setColor(lastCandleStick.getColor());
        g2D.setStroke(new BasicStroke(2));
        g2D.drawLine(0, (int) (lastCandleStick.getClosePricePercentHeight() * (double) graphPanel.getHeight()),
                middleOfLastCandleStick,
                (int) (lastCandleStick.getClosePricePercentHeight() * (double) graphPanel.getHeight()));
    }

    private class GraphPanel extends JPanel {
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

    private class AxisPanel extends JPanel {
        private final int amountOfLabels = 10;
        private final JTextArea[] labelArrayList = new JTextArea[amountOfLabels];
        private final JPanel[] grid = new JPanel[amountOfLabels];

        public AxisPanel() {
            this.setLayout(null);
            this.setPreferredSize(new Dimension(50, 300));
            this.setBackground(Color.BLACK);

            currentPrice.setForeground(Color.WHITE);
            currentPrice.setOpaque(false);
        }

        public void update(CandleStick lastCandleStick) {

            currentPrice.setBounds(0, graphPanel.getHeight() - (int) (lastCandleStick.getClosePricePercentHeight() * (double) graphPanel.getHeight()),
                    300,
                    50);
            DecimalFormat dec = new DecimalFormat("#0.00");
            currentPrice.setText("$ " + dec.format(lastCandleStick.getClosePrice()));

        }

        private void initLabelArrayList() {
            for (int i = labelArrayList.length - 1; i >= 0; i--) {
                labelArrayList[i] = new JTextArea();
                labelArrayList[i].setAlignmentY(JTextField.CENTER_ALIGNMENT);
            }
        }

        private void updateLabels() {
            CurrencyModel.PacketToDraw packetToDraw = gameModel.getChosenCurrencyModel().getPacketToDraw(numberOfCandleSticksToPaint);
            String text;
            for (int i = labelArrayList.length - 1; i >= 0; i--) {
                text = "Xd";
                labelArrayList[i].setText(text);
            }
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g2D = (Graphics2D) g;
            //updateLabels();
        }
    }
}
