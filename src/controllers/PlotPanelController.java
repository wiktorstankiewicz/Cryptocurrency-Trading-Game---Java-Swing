package controllers;

import model.GameModel;
import view.gamePanel.PlotPanel;

public class PlotPanelController {
    private PlotPanel view;
    private GameModel model;

    public PlotPanelController(PlotPanel view, GameModel model) {
        this.view = view;
        this.model = model;
    }

    public void updatePlotPanel(int number) {
        view.setNumberOfCandleSticksToPaint(number);
        view.update();
    }
}
