import Utilities.GameTime;
import mainFrame.MainFrame;
import model.GameModel;

public class Launcher {

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        (new Thread(mainFrame)).start();
//        Game game1 = new Game("Save 1", 0);
//        System.out.println(game1);
//        //model.addSave(game1);
//        MainFrame mainFrame = new MainFrame(model);
//        mainFrame.run();
//        System.out.println("Done");
    }
}
