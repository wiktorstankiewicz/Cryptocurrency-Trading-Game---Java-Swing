package mainFrameMVC;

import GameMVC.GameController;
import GameMVC.GameModel;
import GameMVC.GamePanelView;
import interfaces.pricePredictionStrategy.Cheating;
import interfaces.pricePredictionStrategy.LongTermPrediction;
import interfaces.pricePredictionStrategy.PricePredictor;
import interfaces.pricePredictionStrategy.ShortTermPrediction;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class MainFrameController implements Runnable {
    private final MainFrameView view;
    private final MainFrameModel model;

    public MainFrameController(MainFrameModel mainFrameModel, MainFrameView mainFrameView) {
        this.model = mainFrameModel;
        this.view = mainFrameView;
    }

    @Override
    public void run() {
        addListenersToView();
        try {
            model.deserialize();
        } catch (IOException | ClassNotFoundException e) {
            view.showErrorMesage("Nie udało się wczytać zapisów");
        }
        view.start();
    }

    private void initialiseSaveCreationParameters() {
        view.getGameNameInputJTextField().setText("Zapis " + model.getAmountOfSaves());
        view.setSpinnerParameters(MainFrameModel.MIN_STARTING_FUNDS,
                MainFrameModel.MAX_STARTING_FUNDS,
                MainFrameModel.DEFAULT_STARTING_FUNDS,
                MainFrameModel.STEP_SIZE);
    }

    private void addListenersToView() {
        view.addListenersToButtons(new AcceptButtonListener(), new ConfirmSelectionOfSaveButtonListener(),
                new DeleteSelectedSaveButtonListener(), new GoBackButtonPressed(),
                new ExitButtonClicked(), new SavesButtonClicked(), new CreateGameButtonClicked(),
                new PricePredictionSelectionRadioButtonSelected(new Cheating()),
                new PricePredictionSelectionRadioButtonSelected(new ShortTermPrediction()),
                new PricePredictionSelectionRadioButtonSelected(new LongTermPrediction()));
        view.addWindowListener(new MainFrameListener());
        view.addListenerToSavesTable(new SavesMenuListener(view.getSavesTable()));

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
            initialiseSaveCreationParameters();
            view.showCreateGamePanel();
        }
    }

    private class SavesButtonClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.showSavesPanel(model.getSavesLabels());
        }
    }

    private class ConfirmSelectionOfSaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = model.getSelectedIndex();
            if (selectedIndex > model.getAmountOfSaves() || selectedIndex < 0) {
                view.showErrorMesage("Wybierz zapis");
                return;
            }
            startGame(selectedIndex);
        }
    }

    private void startGame(int selectedIndex) {
        if (model.getAmountOfSaves() == 0) {
            return;
        }
        GameModel selectedSave = model.getSaves().get(selectedIndex);
        GamePanelView gamePanelView = new GamePanelView(selectedSave.getCurrencyNames(),
                selectedSave.getIcons(),
                selectedSave.isPaused(),
                selectedSave.getSimulationSpeed(),
                selectedSave.getNumberOfCandleSticksToDraw()
        );
        //todo
        GameController gameController = new GameController(gamePanelView, selectedSave);
        view.showGamePanel(gamePanelView);
        (new Thread(gameController)).run();
    }

    private class DeleteSelectedSaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = model.getSelectedIndex();
            if (selectedIndex < 0) {
                view.showErrorMesage("Wybierz zapis");
                return;
            }
            model.deleteSave(selectedIndex);
            view.updateSavesPanel(model.getSavesLabels());
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
            String saveName = view.getGameNameInputJTextField().getText();
            double startingFunds = ((Integer) view.getCreateGameStartingFunds().getValue()).doubleValue();
            PricePredictor selectedPricePredictor = model.getSelectedPricePredictor();

            model.addSave(saveName,
                    startingFunds,
                    selectedPricePredictor);

            view.getSavesButton().doClick();
        }
    }

    private class PricePredictionSelectionRadioButtonSelected implements ActionListener {
        private PricePredictor pricePredictor;

        public PricePredictionSelectionRadioButtonSelected(PricePredictor pricePredictor) {
            this.pricePredictor = pricePredictor;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            MainFrameController.this.model.setSelectedPricePredictor(pricePredictor);
        }
    }

    private class SavesMenuListener extends MouseAdapter {
        private JTable jTable;

        public SavesMenuListener(JTable jTable) {
            this.jTable = jTable;
        }

        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            int row = jTable.rowAtPoint(evt.getPoint());
            if (row >= 0) {
                model.setSelectedIndex(row);
            }
        }
    }
}

