import Constants.ExCryptocurrencies;
import mainFrame.GameTime;
import mainFrame.MainFrame;
import model.Game;
import mainFrame.GameModel;

public class Launcher {

    public static void main(String[] args) {
        GameModel model = new GameModel();
        model.run();
//        Game game1 = new Game("Save 1", 0);
//        System.out.println(game1);
//        //model.addSave(game1);
//        MainFrame mainFrame = new MainFrame(model);
//        mainFrame.run();
//        System.out.println("Done");
        GameTime gameTime = new GameTime(0,0,0,0);
        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            gameTime.addElapsedTime(1);
            System.out.println(gameTime);
        }
    }
}
