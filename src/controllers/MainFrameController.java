package controllers;

import model.GameModel;
import model.MainModel;
import view.MainFrame;
import view.gamePanel.GamePanel;
import view.gamePanel.PlotPanel;

public class MainFrameController {
    private GameModel chosenGameModel;
    private MainFrame view;
    private MainModel model;

    private GameController gameController;

    public MainFrameController(MainModel mainModel, MainFrame mainFrame) {
        this.model = mainModel;
        this.view = mainFrame;
        gameController = new GameController(new GamePanel(), chosenGameModel);
    }
}
