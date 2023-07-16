package Kupon1;

import java.time.LocalDateTime;

public class Kupon1 {
    private String number;
    private boolean win;
    private boolean czyWykorzystany;
    private LocalDateTime localDateTime;

    public Kupon1(String number, boolean win, boolean czyWykorzystany, LocalDateTime localDateTime) {
        this.number = number;
        this.win = win;
        this.czyWykorzystany = czyWykorzystany;
        this.localDateTime = localDateTime;
    }

    public String getNumber() {
        return number;
    }

    public boolean isWin() {
        return win;
    }

    public boolean isCzyWykorzystany() {
        return czyWykorzystany;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    @Override
    public String toString() {
        return number +
                "-" + win +
                "-" + czyWykorzystany + "    *"
                + localDateTime ;
    }
}