package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Game {
    private int index = 0;
    private String saveName;
    private String creationDate;

    public Game(String saveName, int index) {
        calculateCreationDate();
        this.saveName = saveName;
        this.index = index;
    }

    private void calculateCreationDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        creationDate = formatter.format(date);
    }

    @Override
    public String toString() {
        return "Name: " + saveName + " creation date: " + creationDate + " index: " + index;
    }
}
