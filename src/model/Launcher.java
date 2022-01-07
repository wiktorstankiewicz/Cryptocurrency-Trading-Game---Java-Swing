package model;

import mainFrame.MainFrame;
import model.GameModel;

import java.io.*;

public class Launcher {

    public static void main(String[] args) {
        Launcher launcher = new Launcher();
        //GameModel gameModel = new GameModel();
        GameModel gameModel = launcher.deserialize();
        MainFrame mainFrame = new MainFrame(launcher, gameModel);
        (new Thread(mainFrame)).run();
    }

    public void serialize(GameModel gameModel) {
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("save.ser"));
            os.writeObject(gameModel);
            System.out.println("saved game");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GameModel deserialize(){
        try{
            ObjectInputStream os = new ObjectInputStream(new FileInputStream("save.ser"));
            return (GameModel) os.readObject();
        }catch(IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
