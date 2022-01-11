package utilities;

import java.io.Serial;
import java.io.Serializable;

public class GameTime implements Cloneable, Serializable {
    @Serial
    private static final long serialVersionUID = -4067309321639227652L;
    private int days;//0-inf
    private int hours;//0-23
    private int minutes;//0-59
    private int seconds;//0-59

    //====================================================Public Methods==============================================//

    public GameTime(int days, int hours, int minutes, int seconds) {
        this.days = days;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public void addElapsedTime(int seconds) {
        setSeconds(getSeconds() + seconds);
    }

    public GameTime(){
        this(0,0,0,0);
    }

    @Override
    public String toString() {
        return "Dzień: " + days + "      " + hours + "h : " + minutes + "min ";
    }

    public int valueOf(){
        return seconds + 60*minutes + 3600*hours + 86400*days;
    }

    @Override
    public GameTime clone(){
        return new GameTime(days, hours, minutes, seconds);
    }

    //====================================================Private Methods=============================================//

    private void setHours(int hours) {
        int sum = hours;
        int daysToAdd = 0;

        while (sum > 23) {
            sum = sum - 24;
            daysToAdd++;
        }
        setDays(getDays() + daysToAdd);
        this.hours = sum;
    }

    private void setDays(int days) {
        this.days = days;
    }

    private void setMinutes(int minutes) {
        int sum = minutes;
        int hoursToAdd = 0;

        while (sum > 59) {
            sum = sum - 60;
            hoursToAdd++;
        }
        setHours(getHours() + hoursToAdd);
        this.minutes = sum;
    }

    private void setSeconds(int seconds) {
        int sum = seconds;
        int minutesToAdd = 0;

        while (sum > 59) {
            sum = sum - 60;
            minutesToAdd++;
        }
        setMinutes(getMinutes() + minutesToAdd);
        this.seconds = sum;
    }

    //====================================================Getters and setters=========================================//

    public int getDays() {
        return days;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }
}
