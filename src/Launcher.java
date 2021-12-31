import Utilities.GameTime;
import mainFrame.MainFrame;
import model.GameModel;

import javax.swing.*;

public class Launcher {

    public static void main(String[] args) {

        MainFrame mainFrame = new MainFrame();
        (new Thread(mainFrame)).start();
        /*try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }*/
//        Game game1 = new Game("Save 1", 0);
//        System.out.println(game1);
//        //model.addSave(game1);
//        MainFrame mainFrame = new MainFrame(model);
//        mainFrame.run();
//        System.out.println("Done");
    }
}
