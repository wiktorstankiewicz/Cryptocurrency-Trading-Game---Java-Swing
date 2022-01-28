package mainFrameMVC;

import GameMVC.GameModel;
import interfaces.pricePredictionStrategy.PricePredictor;
import utilities.Constants;

import java.io.*;
import java.util.ArrayList;
import java.util.Vector;

public class MainFrameModel {
    static final int MIN_STARTING_FUNDS = 5000;
    static final int MAX_STARTING_FUNDS = 1000000;
    static final int DEFAULT_STARTING_FUNDS = 250000;
    static final int STEP_SIZE = 5000;

    private final String SAVE_FILE_NAME = "save.ser";
    private ArrayList<GameModel> saves = new ArrayList<>();
    private GameModel selectedGameModel;
    private PricePredictor selectedPricePredictor;
    private int selectedIndex;

    public void addSave(String saveName, double startingFunds, PricePredictor selectedPricePredictor) {
        saves.add(new GameModel(saveName,startingFunds, selectedPricePredictor));
    }

    public void deleteSave(int index){
        saves.remove(index);
    }

    public void serialize() {
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(SAVE_FILE_NAME));
            os.writeObject(saves);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deserialize() throws ClassNotFoundException, IOException {
            ObjectInputStream os = new ObjectInputStream(new FileInputStream(SAVE_FILE_NAME));
            //noinspection unchecked
            saves = (ArrayList<GameModel>) os.readObject();

    }

    public int getAmountOfSaves() {
        return saves.size()+1;
    }

    public ArrayList<GameModel> getSaves() {
        return saves;
    }

    public void setSaves(ArrayList<GameModel> saves) {
        this.saves = saves;
    }

    public GameModel getSelectedGameModel() {
        return selectedGameModel;
    }

    public void setSelectedGameModel(GameModel selectedGameModel) {
        this.selectedGameModel = selectedGameModel;
    }

    public Vector<Vector<String>> getSavesLabels() {
        Vector<Vector<String>> labels = new Vector<>();
        Vector<String> buffer = new Vector<>(5);
        for(int i = 0; i<saves.size(); i++){
            buffer.add(String.valueOf(i));
            buffer.add(saves.get(i).getName());
            buffer.add(Constants.DOUBLE_FORMATTER.format(saves.get(i).getOwnedFiat()));
            buffer.add(saves.get(i).getPricePredictorName());
            buffer.add(String.valueOf(saves.get(i).getGameTime()));
            labels.add(buffer);
            buffer = new Vector<>();
        }
        return labels;
    }

    public void setSelectedPricePredictor(PricePredictor selectedPricePredictor) {
        this.selectedPricePredictor = selectedPricePredictor;
    }

    public PricePredictor getSelectedPricePredictor() {
        return selectedPricePredictor;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }
}
