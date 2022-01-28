package mainFrameMVC;

import GameMVC.GamePanelView;

import javax.swing.*;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Vector;

import static utilities.Utilities.addGridOfJPanels;

@SuppressWarnings("ALL")
public class MainFrameView extends JFrame {
    private static final Dimension SMALL_DIMENSION = new Dimension(600, 400);
    private static final Dimension BIG_DIMENSION = new Dimension(1200, 600);
    @SuppressWarnings("FieldCanBeLocal")
    private final String TITLE = "Giełda - Wiktor Stankiewicz";

    private final JPanel containerPanel = new JPanel();
    private final JPanel mainPanel = new JPanel();
    private final JPanel savesPanel = new JPanel();
    private final JPanel createGamePanel = new JPanel();

    private final JButton acceptButton = new JButton("Akceptuj");
    private final JButton confirmSelectionOfSaveButton = new JButton("Wybierz");
    private final JButton deleteSelectedSaveButton = new JButton("Usuń");
    private final JButton goBackButton = new JButton("Wróć");
    private final JButton savesButton = new JButton("Wczytaj zapis");
    private final JButton createGameButton = new JButton("Stwórz nową grę");
    private final JButton exitGameButton = new JButton("Wyjdź do Windows");

    private final JPanel pricePredictionSelectionButtons = new JPanel(new GridLayout(3,1,1,1));
    private final JRadioButton pricePredictorSelector1 = new JRadioButton("Cheater!");
    private final JRadioButton pricePredictorSelector2 = new JRadioButton("Krótkoterminowa Predykcja");
    private final JRadioButton pricePredictorSelector3 = new JRadioButton("Długoterminowa Predykcja");

    private final CardLayout cardLayout = new CardLayout();
    public final JTextField gameNameInputJTextField = new JTextField();

    private JSpinner createGameStartingFunds = new JSpinner();

    private JTable gameModelSelectionJList = new JTable();


    //====================================================Public Methods==============================================//

    public MainFrameView() {
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        //this.addWindowListener(new MainFrameListener());
        initMainFrame();
        initMainPanel();
        initSavesPanel();
        initCreateGamePanel();
        initContainerPanel();
        this.add(containerPanel);
    }

    public void start() {
        this.setSize(BIG_DIMENSION);
        cardLayout.show(containerPanel, "mainPanel");
        this.setVisible(true);
    }

    //====================================================Private Methods=============================================//

    private void initMainFrame() {
        this.setTitle(TITLE);
        this.setSize(WIDTH, HEIGHT);
    }

    private void initCreateGamePanel() {
        JPanel southPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        JPanel northPanel = new JPanel();

        SpinnerNumberModel spinnerNumberModel = new SpinnerNumberModel();
        JPanel[][] grid = addGridOfJPanels(5, 2, centerPanel, 1, 1);
        JLabel title = new JLabel("Tworzenie nowej gry");
        JLabel name = new JLabel("Nazwa: ");
        JLabel startingFunds = new JLabel("Srodki początkowe: ");
        JLabel pricePredictionAlgorithm = new JLabel("Wybór doradcy finansowego: ");
        ButtonGroup buttonGroup = new ButtonGroup();

        buttonGroup.add(pricePredictorSelector1);
        buttonGroup.add(pricePredictorSelector2);
        buttonGroup.add(pricePredictorSelector3);
        pricePredictorSelector1.doClick();

        pricePredictionSelectionButtons.add(pricePredictorSelector1);
        pricePredictionSelectionButtons.add(pricePredictorSelector2);
        pricePredictionSelectionButtons.add(pricePredictorSelector3);




        createGameStartingFunds.setModel(spinnerNumberModel);
        createGamePanel.setLayout(new BorderLayout());
        createGamePanel.add(southPanel, BorderLayout.SOUTH);
        createGamePanel.add(centerPanel, BorderLayout.CENTER);
        createGamePanel.add(northPanel, BorderLayout.NORTH);

        title.setFocusable(false);
        title.setOpaque(false);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        name.setFocusable(false);
        name.setHorizontalAlignment(SwingConstants.CENTER);

        startingFunds.setFocusable(false);
        startingFunds.setHorizontalAlignment(SwingConstants.CENTER);

        pricePredictionAlgorithm.setFocusable(false);
        pricePredictionAlgorithm.setHorizontalAlignment(SwingConstants.CENTER);

        //setSpinnerParameters(spinnerNumberModel);

        /*acceptButton.addActionListener(e -> );*/

        northPanel.add(title);
        southPanel.add(acceptButton);

        grid[0][0].add(name);
        grid[0][1].add(gameNameInputJTextField);

        grid[1][0].add(startingFunds);
        grid[1][1].add(createGameStartingFunds);

        grid[2][0].add(pricePredictionAlgorithm);
        grid[2][1].add(pricePredictionSelectionButtons);
    }

    public void setSpinnerParameters(int min, int max, int initialValue, int stepSize) {
        SpinnerNumberModel spinnerNumberModel = (SpinnerNumberModel) createGameStartingFunds.getModel();
        spinnerNumberModel.setMinimum(min);
        spinnerNumberModel.setMaximum(max);
        spinnerNumberModel.setValue(initialValue);
        spinnerNumberModel.setStepSize(stepSize);
    }

    private void initSavesPanel() {
        JPanel topPanel = new JPanel();
        JPanel southPanel = new JPanel(new GridLayout(1, 7, 1, 1));
        JPanel[][] southPanelGrid = addGridOfJPanels(3,7,southPanel,1,1);
        JPanel centerPanel = new JPanel();
        initGameModelSelectionJMenu();
        JScrollPane jScrollPane = new JScrollPane(gameModelSelectionJList);
        jScrollPane.setPreferredSize(new Dimension(700,500));


        //goBackButton.addActionListener(new GoBackButtonPressed());

        //confirmSelectionOfSaveButton.addActionListener(new ConfirmSelectionOfSaveButtonListener());
        //deleteSelectedSaveButton.addActionListener(new DeleteSelectedSaveButtonListener());

        savesPanel.setBackground(Color.green);
        savesPanel.setLayout(new BorderLayout());
        savesPanel.add(topPanel, BorderLayout.NORTH);
        savesPanel.add(centerPanel, BorderLayout.CENTER);
        savesPanel.add(southPanel, BorderLayout.SOUTH);

        southPanelGrid[1][1].add(confirmSelectionOfSaveButton);
        southPanelGrid[1][3].add(deleteSelectedSaveButton);
        southPanelGrid[1][5].add(goBackButton);
        topPanel.add(new JLabel("Wybór zapisu"));

        centerPanel.add(jScrollPane);
    }

    private void initGameModelSelectionJMenu() {
        DefaultTableModel dataModel = new DefaultTableModel();
        gameModelSelectionJList.setModel(dataModel);
        gameModelSelectionJList.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    }


    private void initContainerPanel() {
        containerPanel.setLayout(cardLayout);
        containerPanel.add(mainPanel, "mainPanel");
        containerPanel.add(savesPanel, "savesPanel");
        containerPanel.add(createGamePanel, "createGamePanel");
    }

    private void initMainPanel() {
        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        JLabel gameTitle = new JLabel("Symulator giełdy kryptowalut");


        savesButton.setFont(new Font("Arial", Font.BOLD, 30));
        //savesButton.addActionListener(new SavesButtonClicked());

        createGameButton.setFont(new Font("Arial", Font.BOLD, 30));
        //createGameButton.addActionListener(new CreateGameButtonClicked());

        exitGameButton.setFont(new Font("Arial", Font.BOLD, 30));
        //exitGameButton.addActionListener(new ExitButtonClicked());

        savesButton.setFocusable(false);
        createGameButton.setFocusable(false);
        exitGameButton.setFocusable(false);

        gameTitle.setFocusable(false);
        gameTitle.setOpaque(false);
        gameTitle.setFont(new Font("Comic sans", Font.PLAIN, 55));
        gameTitle.setHorizontalAlignment(JTextField.CENTER);

        buttonsPanel.add(savesButton);
        buttonsPanel.add(createGameButton);
        buttonsPanel.add(exitGameButton);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(gameTitle, BorderLayout.NORTH);
        mainPanel.add(buttonsPanel, BorderLayout.CENTER);
    }

    public void showMainPanel() {
        this.setSize(BIG_DIMENSION);
        cardLayout.show(containerPanel, "mainPanel");
    }

    public void showSavesPanel(Vector<Vector<String>> labels) {
        this.updateSavesPanel(labels);
        this.setSize(new Dimension(800,400));
        cardLayout.show(containerPanel, "savesPanel");
    }

    public void showCreateGamePanel() {
        this.setSize(SMALL_DIMENSION);
        cardLayout.show(containerPanel, "createGamePanel");
    }

    public int showClosingDialog() {
        return JOptionPane.showOptionDialog(
                this,
                "Czy na pewno chcesz zakończyć grę?",
                "Czy zakończyć?", JOptionPane.DEFAULT_OPTION,
                JOptionPane.WARNING_MESSAGE, null,
                new Object[]{"Zapisz i zakończ", "Zakończ bez zapisywania", "Anuluj"},
                null);
    }

    public void addListenersToButtons(ActionListener acceptButtonListener,
                                      ActionListener confirmSelectionOfSaveButtonListener,
                                      ActionListener deleteSelectedSaveButtonListener,
                                      ActionListener goBackButtonListener,
                                      ActionListener exitGameButtonListener,
                                      ActionListener savesButtonListener,
                                      ActionListener createGameButtonListener,
                                      ActionListener algorithm1Listener,
                                      ActionListener algorithm2Listener,
                                      ActionListener algorithm3Listener) {
        acceptButton.addActionListener(acceptButtonListener);
        confirmSelectionOfSaveButton.addActionListener(confirmSelectionOfSaveButtonListener);
        deleteSelectedSaveButton.addActionListener(deleteSelectedSaveButtonListener);
        goBackButton.addActionListener(goBackButtonListener);
        exitGameButton.addActionListener(exitGameButtonListener);
        savesButton.addActionListener(savesButtonListener);
        createGameButton.addActionListener(createGameButtonListener);
        pricePredictorSelector1.addActionListener(algorithm1Listener);
        pricePredictorSelector2.addActionListener(algorithm2Listener);
        pricePredictorSelector3.addActionListener(algorithm3Listener);
        pricePredictorSelector1.doClick();
    }

    public JTextField getGameNameInputJTextField() {
        return gameNameInputJTextField;
    }

    //====================================================Inner Classes===============================================//


    public String getTITLE() {
        return TITLE;
    }

    public JPanel getContainerPanel() {
        return containerPanel;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JPanel getSavesPanel() {
        return savesPanel;
    }

    public JPanel getCreateGamePanel() {
        return createGamePanel;
    }

    public JButton getAcceptButton() {
        return acceptButton;
    }

    public JButton getConfirmSelectionOfSaveButton() {
        return confirmSelectionOfSaveButton;
    }

    public JButton getDeleteSelectedSaveButton() {
        return deleteSelectedSaveButton;
    }

    public JButton getGoBackButton() {
        return goBackButton;
    }

    public JButton getSavesButton() {
        return savesButton;
    }

    public JButton getCreateGameButton() {
        return createGameButton;
    }

    public JButton getExitGameButton() {
        return exitGameButton;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public JSpinner getCreateGameStartingFunds() {
        return createGameStartingFunds;
    }

    public void setCreateGameStartingFunds(JSpinner createGameStartingFunds) {
        this.createGameStartingFunds = createGameStartingFunds;
    }

    public void updateSavesPanel(Vector<Vector<String>> savesLabels) {
        DefaultTableModel model = (DefaultTableModel)(gameModelSelectionJList.getModel());
        /*for(Vector<String> row: savesLabels){
            model.addRow(row);
            model.addRow(new Object[]{"1","2","3"});
        }*/
        Vector<String> columns = new Vector<>();
        columns.add("Numer");
        columns.add("Nazwa");
        columns.add("Balans");
        columns.add("Doradca");
        columns.add("Czas");



        model.setDataVector(savesLabels,columns);
        gameModelSelectionJList.getColumnModel().getColumn(0).setPreferredWidth(50);
        gameModelSelectionJList.setPreferredSize(new Dimension(800,300));
        System.out.println("saves panel updated");
    }

    public void showGamePanel(GamePanelView gamePanelView) {
        containerPanel.add(gamePanelView, "gameView");
        this.setSize(BIG_DIMENSION);
        cardLayout.show(containerPanel, "gameView");
    }

    public void showErrorMesage(String text) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(MainFrameView.this
                        ,
                        text,"BLAD",
                        JOptionPane.ERROR_MESSAGE,null);
            }
        }).start();

    }

    public JTable getSavesTable() {
        return gameModelSelectionJList;
    }

    public void addListenerToSavesTable(MouseAdapter savesMenuListener) {
        gameModelSelectionJList.addMouseListener(savesMenuListener);
    }
}
