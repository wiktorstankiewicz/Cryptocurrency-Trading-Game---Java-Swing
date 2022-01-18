package controllers;

import model.GameModel;
import model.MainModel;
import view.MainFrame;
import view.gamePanel.GamePanel;
import view.gamePanel.PlotPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrameController implements Runnable {
    private GameModel chosenGameModel;
    private MainFrame view;
    private MainModel model;

    private GameController gameController;

    public MainFrameController(MainModel mainModel, MainFrame mainFrame) {
        this.model = mainModel;
        this.view = mainFrame;
        //gameController = new GameController(new GamePanel(), chosenGameModel);
    }

    @Override
    public void run() {
        addListenersToView();
        view.start();
    }

    private void addListenersToView() {
        view.addListenersToButtons(new AcceptButtonListener(), new ConfirmSelectionOfSaveButtonListener(),
                new DeleteSelectedSaveButtonListener(), new GoBackButtonPressed(),
                new ExitButtonClicked(), new SavesButtonClicked(), new CreateGameButtonClicked());
    }

    private class ExitButtonClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(1);
        }
    }

    private class CreateGameButtonClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.showCreateGamePanel();
        }
    }

    private class SavesButtonClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.refreshSavesPanel();
            view.showSavesPanel();
        }
    }

    private class ConfirmSelectionOfSaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            /*GameModel selectedSave = gameModelSelectionJList.getSelectedValue();
            if (selectedSave == null) {
                return;
            }
            //todo
            //gamePanel = new GamePanel(selectedSave);
            containerPanel.add(gamePanel, "gameView");
            cardLayout.show(containerPanel, "gameView");*/
        }
    }

    private class DeleteSelectedSaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //todo delete button listener
            /*GameModel selectedSave = gameModelSelectionJList.getSelectedValue();
            int selectedIndex = gameModelSelectionJList.getSelectedIndex();
            DefaultListModel<GameModel> listModel = (DefaultListModel<GameModel>) gameModelSelectionJList.getModel();

            if (selectedIndex < 0) {
                return;
            }
            listModel.remove(selectedIndex);
            listModel.removeSave(selectedSave);*/
        }
    }

    private class GoBackButtonPressed implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.showMainPanel();
        }
    }

    private class MainFrameListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            int option = view.showClosingDialog();
            switch (option) {
                case (0) -> {
                    model.serialize();
                    System.exit(0);
                }
                case (1) -> System.exit(0);
                default -> {
                    return;
                }
            }
            System.exit(1);
        }

        @Override
        public void windowClosed(WindowEvent e) {
            windowClosing(e);
        }
    }

    private class AcceptButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}
