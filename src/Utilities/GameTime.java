package Utilities;

public class GameTime {
    private int days;//0-inf
    private int hours;//0-23
    private int minutes;//0-59
    private int seconds;//0-59

    public void addElapsedTime(int seconds) {
        setSeconds(getSeconds() + seconds);
    }

    public GameTime(int days, int hours, int minutes, int seconds) {
        this.days = days;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    @Override
    public String toString() {
        return days + " : " + hours + " : " + minutes + " : " + seconds + "\n";
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        int sum = hours;
        int daysToAdd = 0;
        while (sum > 23) {
            sum = sum - 24;
            daysToAdd++;
        }
        setDays(getDays() + daysToAdd);
        this.hours = sum;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        int sum = minutes;
        int hoursToAdd = 0;
        while (sum > 59) {
            sum = sum - 60;
            hoursToAdd++;
        }
        setHours(getHours() + hoursToAdd);
        this.minutes = sum;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        int sum = seconds;
        int minutesToAdd = 0;
        while (sum > 59) {
            sum = sum - 60;
            minutesToAdd++;
        }
        setMinutes(getMinutes() + minutesToAdd);
        this.seconds = sum;
    }
}
