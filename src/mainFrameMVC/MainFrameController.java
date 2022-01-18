package mainFrameMVC;

import GameMVC.GameController;
import GameMVC.GameModel;
import GameMVC.GamePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class MainFrameController implements Runnable {
    private MainFrame view;
    private MainFrameModel model;

    private GameController gameController;

    public MainFrameController(MainFrameModel mainFrameModel, MainFrame mainFrame) {
        this.model = mainFrameModel;
        this.view = mainFrame;
        //gameController = new GameController(new GamePanel(), chosenGameModel);
    }

    @Override
    public void run() {
        addListenersToView();
        try{
            model.deserialize();
        }catch (IOException | ClassNotFoundException e){
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
                new ExitButtonClicked(), new SavesButtonClicked(), new CreateGameButtonClicked());
        view.addWindowListener(new MainFrameListener());
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
            int selectedIndex = view.getGameModelSelectionJList().getSelectedIndex();
            if (selectedIndex == -1) {
                return;
            }
            //todo

            GameModel selectedSave = model.getSaves().get(selectedIndex);
            GamePanel gamePanel = new GamePanel(selectedSave.getCurrencyNames(), selectedSave.getIcons(),
                    selectedSave.isPaused(),
                    selectedSave.getSimulationSpeed(),
                    selectedSave.getNumberOfCandleSticksToDraw()
                    );
            gameController = new GameController(gamePanel, selectedSave);
            view.showGamePanel(gamePanel);
            (new Thread(gameController)).run();
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
            model.addSave(view.getGameNameInputJTextField().getText(),
                    ((Integer) view.getCreateGameStartingFunds().getValue()).doubleValue());
            view.getSavesButton().doClick();
        }
    }
}
