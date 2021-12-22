import mainFrame.MainFrame;
import model.Game;
import model.Model;

public class Launcher {
    public static void main(String[] args) {
        Model model = new Model();
        Game game1 = new Game("Save 1", 0);
        System.out.println(game1);
        model.addSave(game1);
        MainFrame mainFrame = new MainFrame(model);
        mainFrame.run();
        System.out.println("Done");
    }
}
