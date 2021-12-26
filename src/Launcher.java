import Utilities.GameTime;
import mainFrame.MainFrame;
import model.GameModel;

public class Launcher {

    public static void main(String[] args) {
        GameModel gameModel = new GameModel();
        MainFrame mainFrame = new MainFrame(gameModel);
        mainFrame.run();
//        Game game1 = new Game("Save 1", 0);
//        System.out.println(game1);
//        //model.addSave(game1);
//        MainFrame mainFrame = new MainFrame(model);
//        mainFrame.run();
//        System.out.println("Done");
        GameTime gameTime = new GameTime(0,0,0,0);
    }
}
